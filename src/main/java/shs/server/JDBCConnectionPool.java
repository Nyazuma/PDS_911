package shs.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.istack.internal.logging.Logger;

public class JDBCConnectionPool {

	private ArrayList<Connection> listConnections;
	private static Logger logger = Logger.getLogger(DataConfig.class); 

	public JDBCConnectionPool() {
		listConnections = new ArrayList<Connection>();
		long debut = System.currentTimeMillis();;
		for (int i = 0; i< Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()); i++) {
			if(!init())
				break;
		}
		logger.info("Pool loading in : " + (System.currentTimeMillis()-debut) + "ms");
	}

	public boolean init() {
		Connection connection = null;
		try{  
			// Database name (be carefull of the upper/lower case), username, password
			connection = DriverManager.getConnection(DataConfig.getPROPERTY_DRIVER() + "://" + DataConfig.getPROPERTY_URL() + "/" + DataConfig.getPROPERTY_DB(),
					DataConfig.getPROPERTY_NOM_UTILISATEUR(), DataConfig.getPROPERTY_MOT_DE_PASSE());
			listConnections.add(connection);
		}
		catch(Exception e){ 
			logger.info("A problem occurs with a connection " +e.getStackTrace());
			if ( connection != null )
				try {
					connection.close(); // if a problem occurs, the connection is removed
					listConnections.remove(connection);
				} catch ( SQLException ignore ) {}
			return false;

		}
		return true;
	}

	public Connection getConnection() {
		//TODO launch a thread to add more connection ? See the synchronization problem
		Connection availableConnection = listConnections.get(0);
		listConnections.remove(availableConnection);
		return availableConnection;
	}

	public void closeConnection(Connection availableConnection) {
		if(!listConnections.contains(availableConnection))
			listConnections.add(availableConnection);
	}

	public int statusConnection() {
		return listConnections.size();
	}
}

