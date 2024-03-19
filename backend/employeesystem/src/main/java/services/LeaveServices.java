package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.LeaveRequest;
import dto.LeaveResponse;
import dto.update.EditLeaveDatas;
import util.ConnectionDatasource;

public class LeaveServices {

	public static LeaveResponse addLeave(LeaveRequest leaveRequest) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("insert into empleave values(?,?,?,?,?,?)")) {

			statement.setInt(1, leaveRequest.getEmp_id());
			statement.setString(2, leaveRequest.getLeave_type());
			statement.setString(3, leaveRequest.getFrom_date());
			statement.setString(4, leaveRequest.getTo_date());
			statement.setInt(5, leaveRequest.getDays());
			statement.setInt(6, 1);
			int result = statement.executeUpdate();
			if (result > 0) {
				LeaveResponse response = new LeaveResponse();
				response.setEmp_id(leaveRequest.getEmp_id());
				response.setLeave_type(leaveRequest.getLeave_type());
				response.setFrom_date(leaveRequest.getFrom_date());
				response.setTo_date(leaveRequest.getTo_date());
				response.setDays(leaveRequest.getDays());
				response.setDeleted(leaveRequest.getDeleted());
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<LeaveResponse> leaveList() {

		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from empleave")) {
			ResultSet result = statement.executeQuery();
			List<LeaveResponse> list = new ArrayList<LeaveResponse>();
			while (result.next()) {
				LeaveResponse response = new LeaveResponse();
				response.setLeave_id(result.getInt("leave_id"));
				response.setEmp_id(result.getInt("emp_id"));
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

	public static LeaveResponse findByLeaveId(int leave_id) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from empleave where leave_id=?")) {
			statement.setInt(1, leave_id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				return new LeaveResponse(result.getInt("leave_id"), result.getInt("emp_id"),
						result.getString("leave_type"), result.getString("from_date"), result.getString("to_date"),
						result.getInt("days"), result.getInt("deleted"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteLeaveId(int leave_id) {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("delete from empleave where leave_id=?")) {
			statement.setInt(1, leave_id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LeaveResponse editLeaveData(int leave_id, EditLeaveDatas editLeaveDatas) {

		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(""
						+ "update empleave set emp_id=? , leave_type=? , from_date=? , to_date=? , days=? , deleted=?"
						+ "where leave_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
			statement.setInt(1, editLeaveDatas.getEmp_id());
			statement.setString(2, editLeaveDatas.getLeave_type());
			statement.setString(3, editLeaveDatas.getFrom_date());
			statement.setString(4, editLeaveDatas.getTo_date());
			statement.setInt(5, editLeaveDatas.getDays());
			statement.setInt(6, editLeaveDatas.getDeleted());
			statement.setInt(7, leave_id);

			int result = statement.executeUpdate();
			if (result > 0) {
				LeaveResponse response = new LeaveResponse();
				response.setEmp_id(editLeaveDatas.getEmp_id());
				response.setLeave_type(editLeaveDatas.getLeave_type());
				response.setFrom_date(editLeaveDatas.getFrom_date());
				response.setTo_date(editLeaveDatas.getTo_date());
				response.setDays(editLeaveDatas.getDays());
				response.setDeleted(editLeaveDatas.getDeleted());
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
