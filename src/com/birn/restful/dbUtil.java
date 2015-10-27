package com.birn.restful;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class dbUtil {
	
	public static Connection connectBirnDb() throws Exception{
		Connection con = null;
		try {
			String userName = appUtil.getProperty("username");
			String pwd = appUtil.getProperty("password");

			userName = appUtil.decrypt(userName);
			pwd = appUtil.decrypt(pwd);
			
			con = dbUtil.connect("uci-bic-gpop.birn.uci.edu", "5432", "hidprd", userName, pwd);
			return con;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	
	public static Connection connect(String hidDBHost, String port, String hidDBName, String userName, String pwd) throws ClassNotFoundException, IOException {

		Connection connection = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");			
			connection = DriverManager.getConnection(
					"jdbc:postgresql://" + hidDBHost + ":" + port + "/" + hidDBName, userName, pwd);
			
			System.out.println("Connected! " );
			
		} catch (SQLException e) {
			
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();			
			
		}
		
		if (connection != null) {
			
			System.out.println("You made it, take control your database now!");
			
		} else {
			
			System.out.println("Failed to make connection!");
			
		}
		
		return connection;
		
	}

	
}
