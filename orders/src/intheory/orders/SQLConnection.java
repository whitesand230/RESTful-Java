package intheory.orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	
	
	//Make Connections with MySQL DB
		public static Connection getConnection(){
			try{
				String connectionURL = "jdbc:mysql://localhost:3306/test";
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(connectionURL, "root", "secret");
				return con;
			} catch(ClassNotFoundException cnfex){
				System.out.println(cnfex.getMessage());
				return null;
			} catch(SQLException sqlex){
				System.out.println(sqlex.getMessage());
				return null;
			}
		}
}
