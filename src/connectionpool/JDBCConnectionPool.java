package connectionpool;

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
			init(); 
		}
		logger.info("Chargement du pool en : " + (System.currentTimeMillis()-debut) + "ms");
	}

	public void init() {
		Connection connection = null;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			// Database name (attention à la casse, notamment de la db), username, password
			connection = DriverManager.getConnection(DataConfig.getPROPERTY_DRIVER() + "://" + DataConfig.getPROPERTY_URL() + "/" + DataConfig.getPROPERTY_DB(),
					DataConfig.getPROPERTY_NOM_UTILISATEUR(), DataConfig.getPROPERTY_MOT_DE_PASSE());
			listConnections.add(connection);
		}
		catch(Exception e){ 
			logger.info("Une connection a rencontré un problème avec " +e.getStackTrace());
			if ( connection != null )
				try {
					connection.close(); // Si un problème survient, on ferme la connexion
					listConnections.remove(connection);
				} catch ( SQLException ignore ) {}

		}
	}

	public Connection getConnection() {
		Connection availableConnection = listConnections.get(0);
		listConnections.remove(availableConnection);
		// On rajoute des connections si on s'approche de la fin
		if(statusConnection()<=5) {
			for(int i=0;i<5; i++)
				init();
		}
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

