package intheory.orders;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("Users")
public class Users {
	
	public static final String OWNERID = "owner_id";
	public static final String FNAME = "firstName";
	public static final String LNAME = "lastName";
	public static final String PHONE = "phone";


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(){
		try{
			Connection conn = SQLConnection.getConnection();
			if(conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			String sql = "SELECT * from owner";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			JSONObject main = new JSONObject();
			JSONArray ja = new JSONArray();
			JSONObject jo;
			while(rs.next()){
				jo = new JSONObject();
				String ownerid = rs.getString(OWNERID);
				String fname = rs.getString(FNAME);
				String lname = rs.getString(LNAME);
				String phone = rs.getString(PHONE);
				jo.put(OWNERID, ownerid);
				jo.put(FNAME, fname);
				jo.put(LNAME, lname);
				jo.put(PHONE, phone);
				ja.put(jo);
			}
			main.put("Users", ja);
			System.out.println(main);
			return Response.status(200).entity(main.toString()).build();
		}catch (Exception e){
			return Response.status(501).entity("Unable to get Users").build();
		}
	}
	
	@GET
	@Path("{c}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("c") Integer orderNumber){
		try{
			Connection conn = SQLConnection.getConnection();
			if(conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			String sql = "SELECT * FROM owner WHERE owner_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderNumber);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){
				return Response.status(500).entity("Error").build();
			}
			else{
				JSONObject jo = new JSONObject();
				String ownerid = rs.getNString(OWNERID);
				String fname = rs.getString(FNAME);
				String lname = rs.getString(LNAME);
				String phone = rs.getString(PHONE);
				jo.put(OWNERID, ownerid);
				jo.put(FNAME, fname);
				jo.put(LNAME, lname);
				jo.put(PHONE, phone);
				JSONArray ja = new JSONArray(jo);
				JSONObject main = new JSONObject();
				main.put("User", ja);
				return Response.status(200).entity(main.toString()).build();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(500).entity("Error").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(InputStream is){
		String string = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		
		try{
			while((line = br.readLine()) != null){
				string += line + "\n";
			}
			JSONObject jo = new JSONObject(string);
			br.close();
			String fname = jo.getString(FNAME);
			String lname = jo.getString(LNAME);
			String phone = jo.getString(PHONE);
			
			String sql = "INSERT INTO owner ('firstName', 'lastName', 'phone') VALUES (?,?,?)";
			
			Connection conn = SQLConnection.getConnection();
			if(conn == null){
				return Response.status(404).entity("Connection Error").build();
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, phone);
			
			ps.executeUpdate();
			ps.close();
			conn.close();
			
			return Response.status(201).entity("CREATED").build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(400).entity("Error").build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(InputStream is){
		return Response.status(500).entity("Not Configured").build();
	}
}
