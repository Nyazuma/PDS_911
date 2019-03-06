package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.istack.internal.logging.Logger;

public class Controller {


	private static Logger logger = Logger.getLogger(DataConfig.class); 
	/***
	 *  Initialize poolConnection Object
	 */
	private JDBCConnectionPool poolConnection;

	/**
	 * Constructor
	 */
	public Controller(JDBCConnectionPool poolConnection) {
		this.poolConnection = poolConnection;
	}

	/**
	 * Check connection. 
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	public boolean connection(String identifiant, String password) {

		String request = "Select count(*) from Personnel where Identifiant_Personnel='" + identifiant + "' and MotDePasse_Personnel = '" + password + "'"; 

		logger.info("Connection - Controller");

		Connection connection = poolConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			if (resultat.getInt(1)==1) {
				logger.info("Connection SUCCEED");
				poolConnection.closeConnection(connection);
				return true; 
			}
			else {
				logger.info("Connection FAILED");
				poolConnection.closeConnection(connection);
				return false; 
			}
		}catch (SQLException e) {
			logger.info("Connection FAILED - SQL EXCEPTION");
			e.printStackTrace();
			poolConnection.closeConnection(connection);
			return false; 
		}
	}

	/**
	 * Return the object number associate to the account.
	 * 
	 * @return
	 */
	public int nbObject() {
		String request = "Select count(*) from Capteurs";  
		
		Connection connection = poolConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			logger.info("nbObject SUCCEED");
			poolConnection.closeConnection(connection);
			return resultat.getInt(1); 

		}catch (SQLException e) {
			logger.info("nbObject FAILED - SQL EXCEPTION");
			poolConnection.closeConnection(connection);
			return 0; 
		}
	}

	/**
	 * Add an object to the base.
	 * 
	 * @param typeCapteur
	 * @return
	 */
	public boolean addObject(String typeCapteur) {
		String request = "INSERT INTO Capteurs (Type_Capteur, Etat_Capteur, ID_Emplacement) VALUES ('"+ typeCapteur +"', 1, 1)"; 

		//TODO see the 31 entity limit
		Connection connection = poolConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			logger.info("addObject SUCCEED");
			poolConnection.closeConnection(connection);
			return true; 

		}catch (SQLException e) {
			logger.info("addObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			poolConnection.closeConnection(connection);
			return false; 
		}
	}

}
