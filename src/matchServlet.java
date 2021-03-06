

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class matchServlet
 */
@WebServlet("/matchServlet")
public class matchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC driver name and database URL
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final static String DB_URL = "jdbc:mysql://localhost:3306/hackmatch";    
   
   //  Database credentials
	final static String USER = "vl4kz";
	final static String PWD = "cs4640";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public matchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void processRequest (HttpServletRequest req, HttpServletResponse res)
    	      throws ServletException, IOException
    	   {
    	      PrintWriter out = res.getWriter();
    	      
    	       ResultSet rs = null;
    	       Statement stmt = null;
    	       Connection conn = null;
    	      
    	       try 
    	       {
    	          // Register JDBC driver
    	          Class.forName(JDBC_DRIVER);
    	          // System.out.println("MySQL JDBC Driver Registered");
    	 	          
    	          // Open a connection
    	          conn = DriverManager.getConnection(DB_URL, USER, PWD);
    	          // System.out.println("Connection established");
    	 	   
    	          // Execute SQL query
    	          stmt = conn.createStatement();
    	          String query = "SELECT * FROM USER_DATA "
    	          		+ "ORDER BY RAND() "
    	          		+ "LIMIT 1";
    	          System.out.println(query);
    	          rs = stmt.executeQuery(query);                    
    	         		
    	          // Extract data from result set
    	          while (rs.next())
    	          {
    	             // Retrieve by column name
    	            
    	        	  String name = rs.getString("username");
    	              String gender = rs.getString("gender");
    	              String favLang = rs.getString("pLanguages");
    	              byte[] profImage = rs.getBytes("profileImage");
    	              String encode = Base64.getEncoder().encodeToString(profImage);
    	              System.out.println(encode);
    	              
    	    	      out.print("<center><b><h2>" + name + "</b></h2>");
    	    	      out.print("<b>" + gender + "</b><br />");
    	    	      out.print("<b>" + favLang + "</b><br />");
    	    	      out.print("<img src = \"data:image/jpeg;base64," + encode + "\" class=\"profilePicLarge\" />");
    	    	      out.print("</center>");
    	    	      
    	              out.close();
    	          }	   
    	                  
    	          // Clean-up environment
    	          if (rs != null) {
    	             rs.close();         
    	          stmt.close();
    	          conn.close();
    	          // System.out.println("close database");
    	                 
    	          Driver driver = null;
    	          java.sql.DriverManager.deregisterDriver(driver);         
    	          // System.out.println("deregister driver");
    	          }
    	       } catch (SQLException se) {
    	          se.printStackTrace();       // handle errors for JDBC
    	       } catch (Exception e) {            
    	          e.printStackTrace();        // handle errors for Class.forName
    	       } finally {
    	           // finally block used to close resources
    	           try {
    	              if (stmt != null)
    	                 stmt.close();

    	              Driver driver = null;
    	              java.sql.DriverManager.deregisterDriver(driver);

    	           } catch (SQLException se2) {
    	      	 // nothing we can do
    	           }             
    	           try
    	           {
    	              if (conn != null)
    	                 conn.close();

    	              Driver driver = null;
    	              java.sql.DriverManager.deregisterDriver(driver);

    	           } catch (SQLException se) {
    	              se.printStackTrace();
    	           } // end finally try   
    	           
    	        } // end try
    		}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
