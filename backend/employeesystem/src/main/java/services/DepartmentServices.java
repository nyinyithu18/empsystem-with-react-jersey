package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.DepartmentResponse;
import util.ConnectionDatasource;

public class DepartmentServices {

	public static List<DepartmentResponse> depList() {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from department")) {
			ResultSet result = statement.executeQuery();
			List<DepartmentResponse> list = new ArrayList<DepartmentResponse>();
			while (result.next()) {
				DepartmentResponse response = new DepartmentResponse();
				response.setDep_id(result.getInt("dep_id"));
				response.setDep_name(result.getString("dep_name"));
				list.add(response);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
