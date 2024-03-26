package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.EmployeeRequest;
import dto.EmployeeResponse;
import dto.LeaveRequest;
import util.ConnectionDatasource;

public class EmpLeaveServices {

	public static Response exportToExcel(String searchValue) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						"SELECT e.emp_id, e.emp_name, e.nrc, e.phone, " + "e.email, e.dob, e.rank, e.dep, e.address, "
								+ "e.checkdelete, el.leave_id, el.leave_type, "
								+ "el.from_date, el.to_date, el.days, el.deleted " + "FROM employee e "
								+ "FULL OUTER JOIN empleave el ON e.emp_id = el.emp_id "
								+ (searchValue != null && !searchValue.isEmpty()
										? "WHERE e.checkdelete = 1 AND (e.emp_id LIKE ? OR e.emp_name LIKE ? OR "
												+ "e.nrc LIKE ? OR e.phone LIKE ? OR "
												+ "e.email LIKE ? OR e.rank LIKE ? OR "
												+ "e.dep LIKE ? OR e.address LIKE ?)"
										: ""))) {

			if (searchValue != null && !searchValue.isEmpty()) {
				for (int i = 1; i <= 8; i++) {
					statement.setString(i, "%" + searchValue + "%");
				}
			}

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

			File excelFile = File.createTempFile("employee_data", ".xlsx");
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

	public static List<EmployeeResponse> findWithPager(int startIndex, int limit, String searchValue) {

		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = buildSearchStatement(connection, startIndex, limit, searchValue)) {

			ResultSet result = statement.executeQuery();
			List<EmployeeResponse> list = new ArrayList<EmployeeResponse>();
			while (result.next()) {
				EmployeeResponse response = new EmployeeResponse();
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
				list.add(response);
			}

			int totalCount = EmpLeaveServices.getTotalEmpCount(searchValue);
			int totalPages = EmpLeaveServices.calculateTotalPages(totalCount, limit);

			// System.out.println("TotalCount : " + totalCount);
			// System.out.println("TotalPages : " + totalPages);

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static PreparedStatement buildSearchStatement(Connection connection, int startIndex, int limit,
			String searchValue) throws SQLException {
		String baseQuery = "SELECT * FROM employee";
		String searchCondition = "";

		if (searchValue != null && !searchValue.isEmpty()) {
			searchCondition = " WHERE checkdelete = 1 AND (emp_id LIKE ? OR emp_name LIKE ? OR nrc LIKE ? OR phone LIKE ? OR email LIKE ? OR rank LIKE ? OR dep LIKE ? OR address LIKE ?)";
		}

		String finalQuery = baseQuery + searchCondition + " ORDER BY emp_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		PreparedStatement statement = connection.prepareStatement(finalQuery);
		int paramIndex = 1;

		if (searchValue != null && !searchValue.isEmpty()) {
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
		}

		statement.setInt(paramIndex++, startIndex);
		statement.setInt(paramIndex++, limit);

		return statement;
	}

	public static int getTotalEmpCount(String searchValue) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = buildCountStatement(connection, searchValue);) {
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private static PreparedStatement buildCountStatement(Connection connection, String searchValue)
			throws SQLException {
		String baseQuery = "SELECT COUNT(*) FROM employee";
		String searchCondition = "";

		if (searchValue != null && !searchValue.isEmpty()) {
			searchCondition = " WHERE checkdelete = 1 AND (emp_id LIKE ? OR emp_name LIKE ? OR nrc LIKE ? OR phone LIKE ? OR email LIKE ? OR rank LIKE ? OR dep LIKE ? OR address LIKE ?)";
		}

		String finalQuery = baseQuery + searchCondition;

		PreparedStatement statement = connection.prepareStatement(finalQuery);
		int paramIndex = 1;

		if (searchValue != null && !searchValue.isEmpty()) {
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
			statement.setString(paramIndex++, "%" + searchValue + "%");
		}
		return statement;
	}

	public static int calculateTotalPages(int totalEmp, int empPerpage) {
		return (int) Math.ceil((double) totalEmp / empPerpage);
	}
}
