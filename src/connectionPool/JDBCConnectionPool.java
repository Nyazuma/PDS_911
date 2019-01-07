package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.junit.experimental.theories.ParameterSignature;

public class JDBCConnectionPool {

	private ArrayList<Connection> listConnections;

	public JDBCConnectionPool() {
		for (int i = 0; i< Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()); i++) {
			init(); 
		}
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

