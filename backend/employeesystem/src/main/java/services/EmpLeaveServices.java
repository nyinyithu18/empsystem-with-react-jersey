package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.EmployeeRequest;
import dto.LeaveRequest;
import util.ConnectionDatasource;

public class EmpLeaveServices {

	public static Response exportToExcel() {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"select employee.emp_id, employee.emp_name, employee.nrc, employee.phone, employee.email, employee.dob, employee.rank, employee.dep, employee.address, employee.checkdelete, empleave.leave_id, empleave.leave_type, empleave.from_date, empleave.to_date, empleave.days, empleave.deleted from employee full outer join empleave on employee.emp_id = empleave.emp_id;")) {
			ResultSet result = statement.executeQuery();
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Emp Data");
			int rowNum = 0;
			int currentEmpId = -1;
			Row headerRow = sheet.createRow(rowNum++);
			headerRow.createCell(0).setCellValue("Emp Id");
			headerRow.createCell(1).setCellValue("Emp Name");
			headerRow.createCell(2).setCellValue("Nrc");
			headerRow.createCell(3).setCellValue("Phone");
			headerRow.createCell(4).setCellValue("Email");
			headerRow.createCell(5).setCellValue("Date Of Birth");
			headerRow.createCell(6).setCellValue("Rank");
			headerRow.createCell(7).setCellValue("Department");
			headerRow.createCell(8).setCellValue("Address");
			headerRow.createCell(9).setCellValue("Checkdelete");
			headerRow.createCell(10).setCellValue("Leave Id");
			headerRow.createCell(11).setCellValue("Leave Type");
			headerRow.createCell(12).setCellValue("From Date");
			headerRow.createCell(13).setCellValue("To Date");
			headerRow.createCell(14).setCellValue("Days");
			headerRow.createCell(15).setCellValue("Deleted");

			while (result.next()) {
				Row empRow = sheet.createRow(rowNum++);
				empRow.createCell(0).setCellValue(result.getInt("emp_id"));
				empRow.createCell(1).setCellValue(result.getString("emp_name"));
				empRow.createCell(2).setCellValue(result.getString("nrc"));
				empRow.createCell(3).setCellValue(result.getString("phone"));
				empRow.createCell(4).setCellValue(result.getString("email"));
				empRow.createCell(5).setCellValue(result.getString("dob"));
				empRow.createCell(6).setCellValue(result.getString("rank"));
				empRow.createCell(7).setCellValue(result.getString("dep"));
				empRow.createCell(8).setCellValue(result.getString("address"));
				empRow.createCell(9).setCellValue(result.getInt("checkdelete"));
				empRow.createCell(10).setCellValue(result.getInt("leave_id"));
				empRow.createCell(11).setCellValue(result.getString("leave_type"));
				empRow.createCell(12).setCellValue(result.getString("from_date"));
				empRow.createCell(13).setCellValue(result.getString("to_date"));
				empRow.createCell(14).setCellValue(result.getInt("days"));
				empRow.createCell(15).setCellValue(result.getInt("deleted"));
			}

			File excelFile = File.createTempFile("employee-data", ".xlsx");
			try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
				workbook.write(outputStream);
			}

			Response.ResponseBuilder response = Response.ok(excelFile);
			response.header("Content-Disposition", "attachment; filename=employee_data.xlsx");
			return response.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void importExcel(InputStream inputfile) {

		try (Workbook workbook = new XSSFWorkbook(inputfile);
				Connection connection = ConnectionDatasource.getConnection()) {

			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}

				int emp_id = (int) row.getCell(0).getNumericCellValue();
				String emp_name = row.getCell(1).getStringCellValue();
				String nrc = row.getCell(2).getStringCellValue();
				int phone = (int) row.getCell(3).getNumericCellValue();
				String email = row.getCell(4).getStringCellValue();
				Date dob = new Date(row.getCell(5).getDateCellValue().getTime());
				String rank = row.getCell(6).getStringCellValue();
				String dep = row.getCell(7).getStringCellValue();
				String address = row.getCell(8).getStringCellValue();
				int checkdelete = (int) row.getCell(9).getNumericCellValue();

				EmployeeRequest request = new EmployeeRequest(emp_id, emp_name, nrc, String.valueOf(phone), email,
						String.valueOf(dob), rank, dep, address, checkdelete, null);
				EmployeeServices.addEmployee(request);

				String leave_type = row.getCell(11).getStringCellValue();
				Date from_date = new Date(row.getCell(12).getDateCellValue().getTime());
				Date to_date = new Date(row.getCell(13).getDateCellValue().getTime());
				int days = (int) row.getCell(14).getNumericCellValue();
				int deleted = (int) row.getCell(15).getNumericCellValue();

				LeaveRequest leaveRequest = new LeaveRequest(emp_id, leave_type, String.valueOf(from_date),
						String.valueOf(to_date), days, deleted);
				LeaveServices.addLeave(leaveRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
