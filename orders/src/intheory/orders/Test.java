package intheory.orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.XML;

public class Test {

	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			if (conn == null){
				//return Response.status(404).entity("Connection Error").build();
			}
			System.out.println("Make Connection");
			String sql = "SELECT * from orders";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			JSONArray ja = new JSONArray();
			while(rs.next()){
				JSONObject jo = new JSONObject();
				String orderid = rs.getString("order_id");
				String fname = rs.getString("firstName");
				String lname = rs.getString("lastName");
				Timestamp startDate = rs.getTimestamp("startDate");
				Timestamp endDate = rs.getTimestamp("finishDate");
				String phone = rs.getString("phone");
				int ownerid = rs.getInt("owner_id");
				String order_desc = rs.getString("order_desc");
				jo.put("OrderID", orderid);
				jo.put("FirstName", fname);
				jo.put("LastName", lname);
				jo.put("StartDate", startDate);
				jo.put("EndDate", endDate);
				jo.put("Phone", phone);
				jo.put("Owner", ownerid);
				jo.put("OrderDesc", order_desc);
				ja.put(jo);
			}
			JSONObject main = new JSONObject();
			main.put("Order",ja);
			System.out.println(main);
			//System.out.println(headers.getRequestHeader("Accept"));
			/*if(headers.getRequestHeader("Accept").get(0).equals(MediaType.APPLICATION_JSON)){
				System.out.println("Request Header is set for JSON");
				//return Response.status(200).entity(main.toString()).build();
			}
			else{
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Orders>" + XML.toString(main) +"</Orders>";
				//return Response.status(200).entity(xml).build();
			}*/
		}catch(Exception e){
			System.out.println(e.getMessage());
			//return Response.status(501).entity("Error").build();
		}
	}
	
	public static Connection getConnection(){
		try{
			String connectionURL = "jdbc:mysql://localhost:3306/test";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(connectionURL, "root", "secret");
			return con;
		} catch(ClassNotFoundException cnfex){
			System.out.println(cnfex.getClass() + " : " + cnfex.getMessage());
			return null;
		} catch(SQLException sqlex){
			System.out.println(sqlex.getClass() + " : " +sqlex.getMessage());
			return null;
		}
	}

}
