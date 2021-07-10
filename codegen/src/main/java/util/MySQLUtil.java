package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLUtil {

	private static String url = "jdbc:mysql:///p2p?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true";
	private static String user = "root";
	private static String password = "root";
	private static Connection conn = null;

	public static void exec(String ddl) throws SQLException{
		if(conn == null)
			conn = DriverManager.getConnection(url, user, password);

		Statement stmt = conn.createStatement();
		stmt.execute(ddl);

		stmt.close();
	}
}
