package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnFactory {
	private static ConnFactory cf;
	private ConnFactory() {
		super();
	}
	public static synchronized ConnFactory getInstance() {
		 if (cf==null) {
			 cf= new ConnFactory();
		 }
		 return cf;
	 }
	 
	 public Connection getConnection() {
		 Connection conn = null;
		 Properties prop = new Properties();
		 try {
			prop.load(new FileReader("database.properties"));
			 conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"), prop.getProperty("password"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	 }
	 
	}


