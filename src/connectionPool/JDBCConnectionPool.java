package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class JDBCConnectionPool {

	private ArrayList<Connection> listConnections;

	public JDBCConnectionPool() {
		
	}

	public void init() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			// Database name, username and password  
			listConnections.add(DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root"));
		}
		catch(Exception e){ 
			System.out.println(e);
		}  
	}

	public Connection getConnection() {
		Connection availableConnection = listConnections.get(0);
		listConnections.remove(availableConnection);
		return availableConnection;
	}

	public void closeConnection(Connection availableConnection) {
		listConnections.add(availableConnection);
	}


}

