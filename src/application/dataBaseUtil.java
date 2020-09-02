package application;

import java.sql.*;

public class dataBaseUtil {
	static Connection conn=null;
	static Connection connect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/dbbooks?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			return conn;
			
		}catch(Exception e) {
			System.out.println(e.getMessage().toString());
			return null;
		}
	}
}
