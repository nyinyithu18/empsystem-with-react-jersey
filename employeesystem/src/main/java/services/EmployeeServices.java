package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import dto.EmployeeRequest;
import dto.EmployeeResponse;
import dto.update.UpdateEmpData;
import util.ConnectionDatasource;

public class EmployeeServices {

	private static String filePath = "E:\\new-workspace\\employeesystem\\src\\main\\webapp\\uploadImage\\";

	// Employee Data Save With Image
	public static EmployeeResponse addEmployee(EmployeeRequest empRequest) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?,?,?)")) {

			String filePathName = "profileImage_" + empRequest.getEmp_id() + ".jpg";
			String mainFilePath = filePath + filePathName;
			Files.copy(empRequest.getFilePath(), Paths.get(mainFilePath), StandardCopyOption.REPLACE_EXISTING);

			statement.setInt(1, empRequest.getEmp_id());
			statement.setString(2, empRequest.getEmp_name());
			statement.setString(3, empRequest.getNrc());
			statement.setString(4, empRequest.getPhone());
			statement.setString(5, empRequest.getEmail());
			statement.setString(6, empRequest.getDob());
			statement.setString(7, empRequest.getRank());
			statement.setString(8, empRequest.getDep());
			statement.setString(9, empRequest.getAddress());
			statement.setInt(10, empRequest.getCheckdelete());
			statement.setString(11, filePathName);

			int result = statement.executeUpdate();

			if (result > 0) {
				EmployeeResponse response = new EmployeeResponse();
				response.setEmp_id(empRequest.getEmp_id());
				response.setEmp_name(empRequest.getEmp_name());
				response.setNrc(empRequest.getNrc());
				response.setPhone(empRequest.getPhone());
				response.setEmail(empRequest.getEmail());
				response.setDob(empRequest.getDob());
				response.setRank(empRequest.getRank());
				response.setDep(empRequest.getDep());
				response.setAddress(empRequest.getAddress());
				response.setCheckdelete(empRequest.getCheckdelete());
				// response.setFilePath(filePathName);
				return response;
			}

		} catch (NoSuchFileException e) {
			System.out.println("Image store error" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Employee Data GET All
	public static List<EmployeeResponse> empList() {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from employee")) {
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
				// response.setFilePath(result.getString("filePath"));
				list.add(response);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Update Employee Data With Image
	public static EmployeeResponse editEmpDataImage(int emp_id, EmployeeRequest empRequest) {

		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(""
						+ "update employee set emp_name=? , nrc=? , phone=? , email=? , dob=? , rank=? , dep=? , address=? , checkdelete=? , filePath=?"
						+ "where emp_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

			String filePathName = "profileImage_" + empRequest.getEmp_id() + ".jpg";
			String mainFilePath = filePath + filePathName;
			Files.copy(empRequest.getFilePath(), Paths.get(mainFilePath), StandardCopyOption.REPLACE_EXISTING);

			statement.setString(1, empRequest.getEmp_name());
			statement.setString(2, empRequest.getNrc());
			statement.setString(3, empRequest.getPhone());
			statement.setString(4, empRequest.getEmail());
			statement.setString(5, empRequest.getDob());
			statement.setString(6, empRequest.getRank());
			statement.setString(7, empRequest.getDep());
			statement.setString(8, empRequest.getAddress());
			statement.setInt(9, empRequest.getCheckdelete());
			statement.setString(10, filePathName);
			statement.setInt(11, empRequest.getEmp_id());

			int result = statement.executeUpdate();

			if (result > 0) {
				EmployeeResponse response = new EmployeeResponse();
				response.setEmp_id(emp_id);
				response.setEmp_name(empRequest.getEmp_name());
				response.setNrc(empRequest.getNrc());
				response.setPhone(empRequest.getPhone());
				response.setEmail(empRequest.getEmail());
				response.setDob(empRequest.getDob());
				response.setRank(empRequest.getRank());
				response.setDep(empRequest.getDep());
				response.setAddress(empRequest.getAddress());
				response.setCheckdelete(empRequest.getCheckdelete());
				// response.setFilePath(filePathName);
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// Update Employee Data Without Image
	public static EmployeeResponse editEmpDataWithoutImage(int emp_id, UpdateEmpData updateEmpdata) {

		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(""
						+ "update employee set emp_name=? , nrc=? , phone=? , email=? , dob=? , rank=? , dep=? , address=? , checkdelete=?"
						+ "where emp_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

			statement.setString(1, updateEmpdata.getEmp_name());
			statement.setString(2, updateEmpdata.getNrc());
			statement.setString(3, updateEmpdata.getPhone());
			statement.setString(4, updateEmpdata.getEmail());
			statement.setString(5, updateEmpdata.getDob());
			statement.setString(6, updateEmpdata.getRank());
			statement.setString(7, updateEmpdata.getDep());
			statement.setString(8, updateEmpdata.getAddress());
			statement.setInt(9, updateEmpdata.getCheckdelete());
			statement.setInt(10, emp_id);

			int result = statement.executeUpdate();

			if (result > 0) {
				EmployeeResponse response = new EmployeeResponse();
				response.setEmp_id(emp_id);
				response.setEmp_name(updateEmpdata.getEmp_name());
				response.setNrc(updateEmpdata.getNrc());
				response.setPhone(updateEmpdata.getPhone());
				response.setEmail(updateEmpdata.getEmail());
				response.setDob(updateEmpdata.getDob());
				response.setRank(updateEmpdata.getRank());
				response.setDep(updateEmpdata.getDep());
				response.setAddress(updateEmpdata.getAddress());
				response.setCheckdelete(updateEmpdata.getCheckdelete());
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// Find By Employee Id
	public static EmployeeResponse getByEmpID(int emp_id) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from employee where emp_id=?")) {
			statement.setInt(1, emp_id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				return new EmployeeResponse(result.getInt("emp_id"), result.getString("emp_name"),
						result.getString("nrc"), result.getString("phone"), result.getString("email"),
						result.getString("dob"), result.getString("rank"), result.getString("dep"),
						result.getString("address"), result.getInt("checkdelete"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get Image
	public static Response getProfileImage(int emp_id) throws FileNotFoundException {

		File imageFile = new File(filePath + "profileImage_" + emp_id + ".jpg");
		if (!imageFile.exists() || !imageFile.isFile()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		FileInputStream fileInputStream = new FileInputStream(imageFile);
		long contentLength = imageFile.length();

		return Response.ok(fileInputStream).header("Content-Length", String.valueOf(contentLength)).build();
	}
}
