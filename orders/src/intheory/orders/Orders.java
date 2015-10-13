package intheory.orders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.sql.Connection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;


@Path("/Orders")
public class Orders {

	//GET all Orders
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrders(@Context HttpHeaders headers ){
		//return "Here are your orders";
		try {
			Connection conn = SQLConnection.getConnection();
			if (conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			System.out.println("Make Connection");
			String sql = "SELECT * from orders";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			JSONArray ja = new JSONArray();
			JSONObject main = new JSONObject();
			JSONObject jo;
			while(rs.next()){
				jo = new JSONObject();
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
				//main.put(orderid, jo);
			}
			//JSONObject main2 = new JSONObject();
			main.put("Orders",ja);
			System.out.println(main);
			System.out.println(headers.getRequestHeader("Accept"));
			if(headers.getRequestHeader("Accept").get(0).equals(MediaType.APPLICATION_JSON)){
				System.out.println("Request Header is set for JSON");
				return Response.status(200).entity(main.toString()).build();
			}
			else{
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + XML.toString(main);
				return Response.status(200).entity(xml).build();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(501).entity("Error").build();
		}
	}
	
	//GET Order by order_id{c}
	@GET
	@Path("{c}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@PathParam("c") Integer orderNumber){
		try{
			Connection conn = SQLConnection.getConnection();
			if (conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			String sql = "SELECT * FROM orders WHERE order_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderNumber);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){
				return Response.status(500).entity("Error").build();
			}
			else
			{
				JSONObject jo = new JSONObject();
				int orderid = rs.getInt("order_id");
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
				JSONObject main = new JSONObject();
				main.put("Order", jo);
				return Response.status(200).entity(main.toString()).build();
			} 
		}catch (Exception e){
			System.out.println(e.getMessage());
			return Response.status(500).entity("Error").build();
		}
	}
	
	//POST INSERT Order to order table
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setOrders(InputStream incomingData){
		String string = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(incomingData));
		String line;
		
		try{
			while((line = br.readLine()) != null){
				string += line + "\n";
			}
			JSONObject jo = new JSONObject(string);
			/*Iterator<?> keys = jo.keys();
			HashMap<Object,Object> map = new HashMap<Object,Object>();
			while(keys.hasNext())
			{
				String key = keys.next().toString();
				map.put(key, jo.get(key));
			}
			System.out.println(jo);
			System.out.println("The map size is " + map.size());*/
			br.close();
			String fname = jo.getString("FirstName");
			String lname = jo.getString("LastName");
			//String startDate = jo.getString("StartDate");
			//String endDate = jo.getString("EndDate");
			String phone = jo.getString("Phone");
			int ownerid = jo.getInt("Owner");
			String order_desc = jo.getString("OrderDesc");
			
			String sql = "INSERT INTO orders (`firstName`, `lastName`, `startDate`, `finishDate`, `phone`, `owner_id`, `order_desc`) VALUES (?,?,?,?,?,?,?)";
			
			Connection conn = SQLConnection.getConnection();
			if (conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,fname);
			ps.setString(2, lname);
			//ps.setDate(3, getCurrentDate());
			ps.setTimestamp(3, getCurrentDate());
			ps.setTimestamp(4, getCurrentDate());
			ps.setString(5, phone);
			ps.setInt(6, ownerid);
			ps.setString(7, order_desc);
			
			System.out.println(ps);
			
			ps.executeUpdate();
			
			/*if(rs != 0){
				return Response.status(400).entity("User Creation Failed").build();
			}*/
			
			return Response.status(201).entity("CREATED").build();
		}catch (Exception e){
			System.out.println(e);
			return Response.status(400).entity("Error").build();
		}		
	}
	
	//PUT UPDATE Order in order table
	@PUT
	@Path("{c}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrders(@PathParam("c") Integer orderNumber, InputStream incomingData){
		String string = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(incomingData));
		String line;
		
		try{
			while((line = br.readLine()) != null){
				string += line + "\n";
			}
			JSONObject jo = new JSONObject(string);
			Iterator<?> keys = jo.keys();
			Map<String,Object> map = new HashMap<String,Object>();
			while(keys.hasNext())
			{
				String key = keys.next().toString();
				map.put(key, jo.get(key));
			}
			System.out.println(jo);
			System.out.println("The map size is " + map.size());
			
			StringBuilder sb = new StringBuilder();
			boolean i = true;
			
			for(Map.Entry<String, Object> entry : map.entrySet()){
				if(!i)
					sb.append(",'" + entry.getKey() +"'='" +entry.getValue()+"'");
				else
					sb.append("'"+entry.getKey() +"'='" +entry.getValue()+"'");
			}
			
			System.out.print("Does this look right?" + sb.toString());
			
			//SQL Example UPDATE `test`.`orders` SET `lastName` = 'Test2' WHERE `orders`.`order_id` = 2 
			String sql = "UPDATE orders SET ? WHERE order_id=?";
			Connection conn = SQLConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(2, orderNumber);
			
		}catch (Exception e){
			System.out.println(e.getMessage());
			return Response.status(500).entity("Error").build();
		}
		return null;
	}


	private static Timestamp getCurrentDate() {
		//java.sql.Timestamp
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
	

}