package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shs.common.Message;
import shs.common.MessageType;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgConnection;
import shs.common.MsgDeleteObject;
import shs.common.MsgIntResult;
import shs.common.MsgListResult;
import shs.common.MsgReportRFID;
import shs.common.MsgUpdateObject;
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

	/**
	 * 
	 * @param request
	 * @return
	 */
	public String treatmentRequest(String request) {
		Message input = Tool.jsonToMessage(request);
		Boolean resultBoolean;
		Integer resultInteger;
		List<List<String>> resultList; 
		switch(input.getType()) {
		case PING :
			return Tool.messageToJSON(new Message(MessageType.PING));
		case CONNECTION : 
			resultBoolean = connection(((MsgConnection)input).getUsername(), ((MsgConnection)input).getPassword());
			MsgBooleanResult answer1 = new MsgBooleanResult(resultBoolean);
			return Tool.messageToJSON(answer1);
		case ADDOBJECT :
			resultList = addObject(((MsgAddObject)input).getObject());
			MsgListResult answer2 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer2);
		case NUMBEROBJECT :
			resultInteger = nbObjects();
			MsgIntResult answer3 = new MsgIntResult(resultInteger);
			return Tool.messageToJSON(answer3);
		case LISTOBJECTS : 
			resultList = listObjects(); 
			MsgListResult answer4 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer4);
		case DELETEOBJECT : 
			resultList = deleteObject(((MsgDeleteObject)input).getObject()); 
			MsgListResult answer5 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer5);
		case UPDATEOBJECT : 
			resultBoolean = updateObject(((MsgUpdateObject)input).getObject()); 
			MsgBooleanResult answer6 = new MsgBooleanResult(resultBoolean); 
			return Tool.messageToJSON(answer6);
		case LISTZONES :
			resultList = listZones();
			MsgListResult answer7 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer7);
		case LISTPIECES :
			resultList = listPieces();
			MsgListResult answer8 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer8);
		case LISTRESIDENCES :
			resultList = listResidences();
			MsgListResult answer9 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer9);
		case LISTREFERENTIELS :
			resultList = listReferentiels();
			MsgListResult answer10 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer10);
		case LISTEMPLACEMENTS :
			resultList = listEmplacements();
			MsgListResult answer11 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer11);
		case REPORTRFID :
			reportRFID(((MsgReportRFID)input).getID());
			return null;
		default:
			Tool.logger.error("#Error : Controller > treatmentRequest : Unknow request " + request);
			return null;
		}	
	}


	/**
	 * Check connection. 
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	private boolean connection(String identifiant, String password) {

		String request = "Select count(*) from Personnels where Identifiant_Personnel='" + identifiant + "' and MotDePasse_Personnel = '" + password + "'"; 

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
			Tool.logger.error("Connection FAILED - SQL EXCEPTION");
			System.out.println(request);
			e.printStackTrace();

			return false; 
		}
	}

	/**
	 * Return the object number associate to the account.
	 * 
	 * @return
	 */
	private int nbObjects() {
		String request = "Select count(*) from Capteurs";  


		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(request);
			resultat.next(); 
			Tool.logger.info("nbObject SUCCEED");

			return resultat.getInt(1); 

		}catch (SQLException e) {
			Tool.logger.error("nbObject FAILED - SQL EXCEPTION");

			return 0; 
		}
	}

	/**
	 * Add an object to the base.
	 * 
	 * @param typeCapteur
	 * @return
	 */
	private List<List<String>> addObject(String typeCapteur) {
		String request = "INSERT INTO Capteurs (Type_Capteur, Etat_Capteur, ID_Emplacement) VALUES ('"+ typeCapteur +"', 1, 1)"; 

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			Tool.logger.info("addObject SUCCEED");

			return listObjects(); 

		}catch (SQLException e) {
			Tool.logger.error("addObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();

			return listObjects(); 
		}
	}

	/**
	 * 
	 * @param idCapteur
	 * @return
	 */
	private List<List<String>> deleteObject(String idCapteur) {
		String request = "DELETE FROM Capteurs WHERE ID_Capteur ='" +idCapteur + "'";
		try {
			Statement statement = connection.createStatement(); 
			statement.executeUpdate(request); 
			Tool.logger.info("deleteObject SUCCED");
			return listObjects(); 
		}catch(SQLException e) {
			Tool.logger.error("deleteObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			return listObjects(); 
		}
	}

	/**
	 * 
	 * @param attribute
	 * @return
	 */
	private boolean updateObject(List<String> attribute) {
		String requestEmplacement = "SELECT ID_Emplacement FROM Emplacement INNER JOIN Residences ON Residences.ID_Residence=Emplacement.ID_Residence "
				+ "					WHERE Nom_Residence='" + attribute.get(3) + "' AND Zone_Emplacement='" + attribute.get(4) + "' AND Piece_Emplacement='" + attribute.get(5) + "'";
		String request = "";
		try {
			Statement statement = connection.createStatement(); 
			ResultSet resultEmplacement = statement.executeQuery(requestEmplacement); 
			if(!resultEmplacement.next()) {
				// The "Emplacement" doesn't exit, we have to create it
				// We get the Residence ID
				String requestResidence = "SELECT ID_Residence FROM Residences WHERE Nom_Residence='" + attribute.get(3) + "'";
				ResultSet resultResidence = statement.executeQuery(requestResidence); 
				resultResidence.next();
				int id = resultResidence.getInt(1);
				// We create the "Emplacement" with the Residence ID
				String createEmplacement = "INSERT INTO Emplacement(Piece_Emplacement, Zone_Emplacement, ID_Residence) VALUES('" +attribute.get(5) + "', '" + attribute.get(4)+ "', '" + id + "')" ;
				statement.executeUpdate(createEmplacement); 
				// We get the ID from the "Emplacement" we just created
				String requestEmplacementNew = "SELECT ID_Emplacement FROM Emplacement WHERE Zone_Emplacement='" + attribute.get(4) + "' AND Piece_Emplacement='" + attribute.get(5) + "'"
						+ " AND ID_Residence='" + id + "'";
				ResultSet resultEmplacementNew = statement.executeQuery(requestEmplacementNew); 
				resultEmplacementNew.next();
				int idNew = resultEmplacementNew.getInt(1);
				request = "UPDATE Capteurs SET Type_Capteur = '"+ attribute.get(1) +"', Etat_Capteur = '"+ attribute.get(2) + "', ID_Emplacement = '" + idNew + "' WHERE ID_Capteur ='"+ attribute.get(0)+"'";
			}
			else {
				request = "UPDATE Capteurs SET Type_Capteur = '"+ attribute.get(1) +"', Etat_Capteur = '"+ attribute.get(2) + "', ID_Emplacement = '" + resultEmplacement.getInt(1) + "' WHERE ID_Capteur ='"+ attribute.get(0)+"'";
			}

			statement.executeUpdate(request); 
			return true; 
		}catch(SQLException e) {
			Tool.logger.info("updateObject FAILED - SQL EXCEPTION : " + request);
			e.printStackTrace();
			return false; 
		}

	}

	/**
	 * getList()
	 * @return a list given by the select request
	 */

	private List<List<String>> getList(String sql){
		List<List<String>> list = new ArrayList<List<String>>();

		try {
			Statement statement = connection.createStatement(); 
			ResultSet result = statement.executeQuery(sql); 
			ResultSetMetaData resultMetada = result.getMetaData(); 
			while(result.next()) {
				List<String> record = new ArrayList<String>(); 
				for (int i = 1; i<= resultMetada.getColumnCount(); i++) {
					record.add(result.getString(i)); 
				}
				list.add(record); 
			}

			return list; 
		}catch (SQLException e) {
			Tool.logger.error("getList FAILED - SQL EXCEPTION :\n " + sql);
			e.printStackTrace();
			return null; 
		}
	}

	private List<List<String>> listObjects(){
		String request = "SELECT ID_Capteur, Type_Capteur, Etat_Capteur, Nom_Residence, Niveau_Etage, Nom_Emplacement " + 
				"FROM Capteurs INNER JOIN Emplacements ON Capteurs.ID_Emplacement=Emplacements.ID_Emplacement " + 
				"INNER JOIN Etages ON Emplacements.ID_Etage=Etages.ID_Etage " + 
				"INNER JOIN Residences ON Etages.ID_Residence=Residences.ID_Residence"; 
		return getList(request);

	}


	private List<List<String>> listResidences() {
		String request = "SELECT * FROM Residences INNER JOIN Adresses ON Residences.ID_Addresse=Adresses.ID_Addresse;"; 
		return getList(request);
	}



	private List<List<String>> listPieces() {
		String request = "SELECT DISTINCT Piece_Emplacement FROM Emplacement;";
		return getList(request);
	}


	private List<List<String>> listZones() {
		String request = "SELECT DISTINCT Zone_Emplacement FROM Emplacement;";
		return getList(request);
	}

	private List<List<String>> listReferentiels(){
		String request = "SELECT Type_Capteur FROM Referentiel_Capteurs ORDER BY Type_Capteur;";
		return getList(request);
	}
	
	private List<List<String>> listEmplacements(){
		String request = "SELECT Nom_Emplacement FROM Emplacements;";
		return getList(request);
	}

	private void reportRFID(Integer id) {
		String message = "Le bracelet a généré un appel!";
		String request = "INSERT INTO Notifications(Niveau_Notification, Date_Notification, Message_Notification, Numerique_Notification, ID_Capteur)" 
				+ " VALUES(2, now(), '" + message + "', null,'" + id + "');";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
		}catch(SQLException e) {
			Tool.logger.error("reportRFID FAILED - SQL EXCEPTION");
		}

	}

}
