package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.RankResponse;
import util.ConnectionDatasource;

public class RankServices {

	public static List<RankResponse> rankList() {
		try (Connection connection = ConnectionDatasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("select * from rank")) {
			ResultSet result = statement.executeQuery();
			List<RankResponse> list = new ArrayList<RankResponse>();
			while (result.next()) {
				RankResponse response = new RankResponse();
				response.setRank_id(result.getInt("rank_id"));
				response.setRank_name(result.getString("rank_name"));
				list.add(response);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
