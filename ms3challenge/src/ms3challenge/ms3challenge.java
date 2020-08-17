package ms3challenge;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.time.*;
import java.time.format.*;



public class ms3challenge {

	//simple print statement to make error checking easy
	public static void print(String x) {
	System.out.print(x);
	}
	
	//this method is here for testing purposes and not actually used
	public void selectAll(){
        String sql = "SELECT * FROM gooddata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("A") +  "\t" + 
                					rs.getString("B") +  "\t" + 
                					rs.getString("C") +  "\t" + 
                					rs.getString("D") +  "\t" + 
                					rs.getString("E") +  "\t" + 
                					rs.getString("F") +  "\t" + 
                					rs.getString("G") +  "\t" + 
                					rs.getString("H") +  "\t" + 
                					rs.getString("H") +  "\t" + 
                					rs.getString("J"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//used to write to stats.log
	public void writetostats(long rec, long suc, long fai){
   		try {
   			FileWriter fw;
			fw = new FileWriter("src//stats.log");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			fw.append(dtf.format(now)+"\n");
			fw.append("# of records received: "+rec+"\n");
			fw.append("# of records successful: "+suc+"\n");
			fw.append("# of records failed: "+fai+"\n");
			fw.flush();
			fw.close();	
			print("\nstats written");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//used to write to baddata.cvs
	public void writetocvs(String badrecord){
   		try {
   			FileWriter fw;
			fw = new FileWriter("src//baddata.cvs");
			fw.append(badrecord+"\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//used to connect to SQLite database
	private Connection connect() {
        // SQLite connection string
		String url = "jdbc:sqlite:C:/sqlite/gooddata.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	//used to insert a record in SQLite
	 public void insert(String[] arr) {
	        String sql = "INSERT INTO gooddata (A,B,C,D,E,F,G,H,I,J) VALUES(?,?,?,?,?,?,?,?,?.?)";

	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, arr[0]);
	            pstmt.setString(2, arr[1]);
	            pstmt.setString(3, arr[2]);
	            pstmt.setString(4, arr[3]);
	            pstmt.setString(5, arr[4]);
	            pstmt.setString(6, arr[5]);
	            pstmt.setString(7, arr[6]);
	            pstmt.setString(8, arr[7]);
	            pstmt.setString(9, arr[8]);
	            pstmt.setString(10, arr[9]);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 
	//create new table in SQLite
	public static void createNewTable(String fileName) {
        
		// SQLite connection string
		String url = "jdbc:sqlite:C:/sqlite/" + fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS gooddata (\n"
                + "ID integer PRIMARY KEY,\n"
                + "A text NOT NULL,\n"
                + "B text NOT NULL,\n"
                + "C text NOT NULL,\n"
                + "D text NOT NULL,\n"
                + "E text NOT NULL,\n"
                + "F text NOT NULL,\n"
                + "G text NOT NULL,\n"
                + "H text NOT NULL,\n"
                + "I text NOT NULL,\n"
                + "J text NOT NULL,\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            print("table has been made");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	//to create a new database
	public static void createNewDatabase(String fileName) {
		try{
			Class.forName("org.sqlite.JDBC");
		}
		catch(ClassNotFoundException e){
			System.out.println("class not found");
			e.printStackTrace();
		}

        String url = "jdbc:sqlite:C:/sqlite/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
        	print("problem making db\n");
            System.out.println(e.getMessage());
        }
        createNewTable(fileName);
	}
	 /*
	  * 
	  * Main method starts
	  * 
	  */
	public static void main (String args[])  {
		ms3challenge app = new ms3challenge();
		long received = 0;
		long successful = 0; 
		long failed = 0;
				
		//CREATE a new database
		createNewDatabase("gooddata.db");
		
		
		//scanner setup
		try{
			File data = new File("src//ms3challenge//data.csv");
			System.out.println("absolute path: "+ data.getAbsolutePath());
			Scanner sc = new Scanner(data,"UTF-8");
			sc.useDelimiter("\n");
			
			//parse data
			boolean complete = true;
			//int fields = 0; 
			while(sc.hasNext()) {
			
				String next = sc.next();
				received++;
				//print(next);
				
				String[] row = next.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");				
				
				for(String z: row) {
					if(z.isEmpty()) {
						complete = false;
						
					}
				
				}
				//if all fields have data, insert record into SQLite
				if(complete ) {
					print("inserting record \n");
					successful++;
					
				      app.insert(row);
				}
				else {
					print("record going into bad data\n");
					failed++;
					
				}
				complete=true;
				
			}	
		
			sc.close();	
			print("stats being written\n");
			app.writetostats(received,successful,failed);
			
		}
		catch (FileNotFoundException e) {
				System.out.println("file not found");
				e.printStackTrace();
		}
		
		print("\nprogram done");
	}
}
