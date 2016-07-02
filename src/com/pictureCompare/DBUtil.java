package com.pictureCompare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	protected static Connection getConn() throws ClassNotFoundException, SQLException{

		Class.forName( "org.postgresql.Driver" );
		String url_conn = PropertiesUtil.GetValueByKey("url");
		String userName = PropertiesUtil.GetValueByKey("userName");
		String password = PropertiesUtil.GetValueByKey("password");
		Connection conn = DriverManager.getConnection(url_conn,userName,password);
		return conn;
	}
	
	protected static void close(ResultSet rs,Statement sta,Connection conn) throws SQLException{
		rs.close();
		sta.close();
		conn.close();
	}
}
