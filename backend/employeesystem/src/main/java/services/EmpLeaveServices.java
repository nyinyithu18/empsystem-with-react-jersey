package services;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.EmpLeaveResponse;
import dto.EmployeeRequest;
import dto.LeaveRequest;
import util.ConnectionDatasource;

public class EmpLeaveServices {

	public static List<EmpLeaveResponse> empLeaveList() {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"select employee.emp_id, employee.emp_name, employee.nrc, employee.phone, employee.email, employee.dob, employee.rank, employee.dep, employee.address, employee.checkdelete, empleave.leave_id, empleave.leave_type, empleave.from_date, empleave.to_date, empleave.days, empleave.deleted from employee full outer join empleave on employee.emp_id = empleave.emp_id;")) {
			ResultSet result = statement.executeQuery();
			List<EmpLeaveResponse> list = new ArrayList<EmpLeaveResponse>();
			while (result.next()) {
				EmpLeaveResponse response = new EmpLeaveResponse();
				response.setEmp_id(result.getInt("emp_id"));
				response.setEmp_name(result.getString("emp_name"));
				response.setNrc(result.getString("nrc"));
				response.setPhone(result.getString("phone"));
				response.setEmail(result.getString("email"));
				response.setDob(result.getString("dob"));
				response.setRank(result.getString("rank"));
				response.setDep(result.getString("dep"));
				response.setAddress(result.getString("address"));
				response.setCheckdelete(result.getInt("checkdelete"));
				response.setLeave_id(result.getInt("leave_id"));
				response.setLeave_type(result.getString("leave_type"));
				response.setFrom_date(result.getString("from_date"));
				response.setTo_date(result.getString("to_date"));
				response.setDays(result.getInt("days"));
				response.setDeleted(result.getInt("deleted"));
				list.add(response);
			}
			return list;
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
