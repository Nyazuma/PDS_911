package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shs.common.Message;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgConnection;
import shs.common.MsgIntResult;
import shs.common.Tool;

public class Controller {

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

	public String treatmentRequest(String request) {
		Message input = Tool.jsonToMessage(request);
		Boolean resultBoolean;
		Integer resultInteger;
		switch(input.getType()) {
			case CONNECTION : 
				resultBoolean = connection(((MsgConnection)input).getUsername(), ((MsgConnection)input).getPassword());
				MsgBooleanResult answer1 = new MsgBooleanResult(resultBoolean);
				return Tool.messageToJSON(answer1);
			case ADDOBJECT :
				resultBoolean = addObject(((MsgAddObject)input).getObject());
				MsgBooleanResult answer2 = new MsgBooleanResult(resultBoolean);
				return Tool.messageToJSON(answer2);
			case NUMBEROBJECT :
				resultInteger = nbObject();
				MsgIntResult answer3 = new MsgIntResult(resultInteger);
				return Tool.messageToJSON(answer3);
			default:
				Tool.logger.info("#Error : Controller > treatmentRequest : Unknow request " + request);
				return "";
		}	
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

		Tool.logger.info("Connection - Controller");

		Connection connection = poolConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			if (resultat.getInt(1)==1) {
				Tool.logger.info("Connection SUCCEED");
				poolConnection.closeConnection(connection);
				return true; 
			}
			else {
				Tool.logger.info("Connection FAILED");
				poolConnection.closeConnection(connection);
				return false; 
			}
		}catch (SQLException e) {
			Tool.logger.info("Connection FAILED - SQL EXCEPTION");
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
			Tool.logger.info("nbObject SUCCEED");
			poolConnection.closeConnection(connection);
			return resultat.getInt(1); 

		}catch (SQLException e) {
			Tool.logger.info("nbObject FAILED - SQL EXCEPTION");
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

		Connection connection = poolConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			Tool.logger.info("addObject SUCCEED");
			poolConnection.closeConnection(connection);
			return true; 

		}catch (SQLException e) {
			Tool.logger.info("addObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			poolConnection.closeConnection(connection);
			return false; 
		}
	}

}
