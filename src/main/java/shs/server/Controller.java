package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shs.common.Message;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgConnection;
import shs.common.MsgDeleteObject;
import shs.common.MsgIntResult;
import shs.common.MsgListObject;
import shs.common.Tool;

public class Controller {

	/***
	 *  Initialize poolConnection Object
	 */
	private JDBCConnectionPool connectionPool;
	private Connection connection;

	/**
	 * Constructor
	 */
	public Controller(JDBCConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		this.connection = connectionPool.getConnection();
	}
	
	public void closeController() {
		connectionPool.closeConnection(connection);
	}

	public String treatmentRequest(String request) {
		Message input = Tool.jsonToMessage(request);
		Boolean resultBoolean;
		Integer resultInteger;
		List<List<String>> resultList; 
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
			case LISTOBJECT : 
				resultList = listObject(); 
				MsgListObject answer4 = new MsgListObject(resultList); 
				return Tool.messageToJSON(answer4);
			case DELETEOBJECT : 
				resultBoolean = deleteObeject(((MsgDeleteObject)input).getObject()); 
				MsgBooleanResult answer5 = new MsgBooleanResult(resultBoolean); 
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

		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			if (resultat.getInt(1)==1) {
				Tool.logger.info("Connection SUCCEED");
				
				return true; 
			}
			else {
				Tool.logger.info("Connection FAILED");
				
				return false; 
			}
		}catch (SQLException e) {
			Tool.logger.info("Connection FAILED - SQL EXCEPTION");
			e.printStackTrace();
			
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

		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			Tool.logger.info("nbObject SUCCEED");
			
			return resultat.getInt(1); 

		}catch (SQLException e) {
			Tool.logger.info("nbObject FAILED - SQL EXCEPTION");
			
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

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			Tool.logger.info("addObject SUCCEED");
			
			return true; 

		}catch (SQLException e) {
			Tool.logger.info("addObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			
			return false; 
		}
	}
	
	public boolean deleteObeject(String idCapteur) {
		String request = "DELETE FROM Capteurs WHERE ID_Capteur ='" +idCapteur + "'";
		try {
			Statement statement = connection.createStatement(); 
			statement.executeUpdate(request); 
			Tool.logger.info("deleteObject SUCCED");
			return true; 
		}catch(SQLException e) {
			Tool.logger.info("deleteObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			return false; 
		}
	}
	
	public List<List<String>> listObject(){
		String request = "SELECT * FROM Capteurs"; 
		List<List<String>> list = new ArrayList<List<String>>();

		try {
			Statement statement = connection.createStatement(); 
			ResultSet result = statement.executeQuery(request); 
			ResultSetMetaData resultMetada = result.getMetaData(); 
			while(result.next()) {
				List<String> record = new ArrayList<String>(); 
				for (int i = 1; i<= resultMetada.getColumnCount(); i++) {
					record.add(result.getString(i)); 
				}
				list.add(record); 
			}
			
			Tool.logger.info("getListObject SUCCED");
			return list; 
		}catch (SQLException e) {
			Tool.logger.info("getListObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			return null; 
		}
	}

}
