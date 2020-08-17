package ms3challenge;

import java.io.*;
import java.util.*;
import java.sql.*;

public class ms3challenge {
	public static void print(String x) {
	System.out.print(x);
}
	//opening the SQLite
	 public static void connect() {
	        Connection conn = null;
	        try {
	            // db parameters
	            String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
	            // create a connection to the database
	            conn = DriverManager.getConnection(url);
	            
	            System.out.println("Connection to SQLite has been established.");
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	    }
	public static void main (String args[])  {
		//System.out.println("check");
	
		//CONNECT TO SQL
		connect();
		//("sqlite-jdbc-3.30.1.jar");
		//scanner setup
		try{
			File data = new File("src//ms3challenge//data.csv");
			System.out.println("absoluyte path: "+ data.getAbsolutePath());
			Scanner sc = new Scanner(data);
		
			sc.useDelimiter("\n");
			
			//parse data
			boolean done = false; 
			int fields = 0; 
			
			while(sc.hasNext()) {
				String next = sc.next();
				//print(next);
				
				String[] row = next.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");				
				for(String z: row) {
					fields++;
					System.out.print("\n!"+z);
					print(""+fields);
				}
				//print("!!!!!!!\n");
				if(fields == 10) {
					
				}
			}	
			 
			
			
			sc.close();	
		}
		catch (FileNotFoundException e) {
				System.out.println("file not found");
				e.printStackTrace();
		}
		
	}
}
