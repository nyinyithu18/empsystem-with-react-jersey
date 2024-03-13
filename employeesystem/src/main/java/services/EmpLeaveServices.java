package services;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.EmpLeaveResponse;
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

	public static List<EmpLeaveResponse> importExcel(InputStream inputfile) {
		List<EmpLeaveResponse> list = new ArrayList<>();

		return null;
	}
}
