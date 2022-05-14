package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Inquiry {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inquiryservice?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertInquiry(String iAccNo, String iCName, String iDate, String iReason)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into inquiry(`inqID`,`iAccNo`,`iCName`,`iDate`,`iReason`)" + " values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, iAccNo);
			 preparedStmt.setString(3, iCName);
			 preparedStmt.setString(4, iDate);
			 preparedStmt.setString(5, iReason);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newInquiry = readInquiry(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newInquiry + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the inquiry.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readInquiry()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Account No</th><th>Customer Name</th><th>Date</th><th>Reason</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from inquiry";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String inqID = Integer.toString(rs.getInt("inqID"));
				 String iAccNo = rs.getString("iAccNo");
				 String iCName = rs.getString("iCName");
				 String iDate = rs.getString("iDate");
				 String iReason = rs.getString("iReason");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidInquiryIDUpdate\' name=\'hidInquiryIDUpdate\' type=\'hidden\' value=\'" + inqID + "'>" 
							+ iAccNo + "</td>"; 
				output += "<td>" + iCName + "</td>";
				output += "<td>" + iDate + "</td>";
				output += "<td>" + iReason + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-inquiryid='" + inqID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the inquiry.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateInquiry(String inqID, String iAccNo, String iCName, String iDate, String iReason)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE inquiry SET iAccNo=?,iCName=?,iDate=?,iReason=?"  + "WHERE inqID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, iAccNo);
			 preparedStmt.setString(2, iCName);
			 preparedStmt.setString(3, iDate);
			 preparedStmt.setString(4, iReason);
			 preparedStmt.setInt(5, Integer.parseInt(inqID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newInquiry = readInquiry();    
			output = "{\"status\":\"success\", \"data\": \"" + newInquiry + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the inquiry.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteInquiry(String inqID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from inquiry where inqID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(inqID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newInquiry = readInquiry();    
			output = "{\"status\":\"success\", \"data\": \"" +  newInquiry + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the inquiry.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
