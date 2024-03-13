package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ConnectionDatasource {

	public static Connection getConnection() throws SQLException {
		try {
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setUser("sa");
			ds.setPassword("123");
			ds.setServerName("DESKTOP-64LVB6I");
			ds.setPortNumber(1433);
			ds.setDatabaseName("empsystem");
			ds.setTrustServerCertificate(true);

			return ds.getConnection();
		} catch (Exception e) {
			throw new SQLException("Error establishing connection", e);
		}
	}
}
