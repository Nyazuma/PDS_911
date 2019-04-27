package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import shs.common.Message;
import shs.common.MessageType;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgChangeAlert;
import shs.common.MsgConnection;
import shs.common.MsgDeleteEmplacement;
import shs.common.MsgDeleteObject;
import shs.common.MsgIntResult;
import shs.common.MsgListResult;
import shs.common.MsgReportCall;
import shs.common.MsgReportHygro;
import shs.common.MsgReportMotion;
import shs.common.MsgReportOpening;
import shs.common.MsgReportRFID;
import shs.common.MsgReportSmoke;
import shs.common.MsgReportTemperature;
import shs.common.MsgUpdateEmplacement;
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
			resultList = addObject(((MsgAddObject)input).getObject(), ((MsgAddObject)input).getAddresseMac());
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
		case LISTETAGES :
			resultList = listEtages();
			MsgListResult answer12 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer12);
		case REPORTRFID :
			reportRFID(((MsgReportRFID)input).getId());
			return null;
		case REPORTCALL :
			reportCall(((MsgReportCall) input).getId());
			return null;
		case REPORTSMOKE :
			reportSmoke(((MsgReportSmoke) input).getId(), ((MsgReportSmoke)input).getSmokeValue());
			return null;
		case REPORTMOTION :
			reportMotion(((MsgReportMotion) input).getId());
			return null;
		case REPORTTEMPERATURE :
			reportTemperature(((MsgReportTemperature) input).getId(), ((MsgReportTemperature) input).getTemperature());
			return null;
		case REPORTHYGRO :
			reportHygro(((MsgReportHygro) input).getId(), ((MsgReportHygro) input).getHygroValue());
			return null;
		case REPORTOPENING :
			reportOpening(((MsgReportOpening) input).getId());
			return null;
		case MONITORING :
			resultList = monitoring();
			MsgListResult answer13 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer13);
		case CHANGEALERT :
			changeAlert(((MsgChangeAlert) input).getId(), ((MsgChangeAlert) input).getStatus());
			return null;
		case LISTCAPTEURS : 
			resultList = listCapteurs();
			MsgListResult answer15 = new MsgListResult(resultList); 
			return Tool.messageToJSON(answer15);
		case UPDATEEMPLACEMENT : 
			resultBoolean = updateEmplacementObject(((MsgUpdateEmplacement)input).getSensorID(), ((MsgUpdateEmplacement)input).getLocationID()); 
			MsgBooleanResult answer16 = new MsgBooleanResult(resultBoolean); 
			return Tool.messageToJSON(answer16); 
		case DELETEEMPLACEMENT : 
			resultBoolean = deleteEmplacementObject(((MsgDeleteEmplacement)input).getSensorID()); 
			MsgBooleanResult answer17 = new MsgBooleanResult(resultBoolean); 
			return Tool.messageToJSON(answer17);
			
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
	}//
	/**
	 * Add an object to the base.
	 * 
	 * @param typeCapteur
	 * @return
	 */
	private List<List<String>> addObject(String typeCapteur, String addresseMac) {
		String request = "INSERT INTO Capteurs (Type_Capteur, Etat_Capteur, ID_Emplacement, Mac_Capteur) VALUES ('"+ typeCapteur +"', TRUE, null,'" + addresseMac + "')"; 
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			ResultSet generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();
			int id = generatedKeys.getInt(1);
			request = "";
			System.out.println(typeCapteur);
			System.out.println();
			if(typeCapteur.equals("Bracelet RFID"))
				request = "INSERT INTO CapteursRFID(ID_CapteurRFID, NiveauAlerte_CapteurRFID) values(" + id + ",2)";
			if(typeCapteur.equals("Capteur appel"))
				request = "INSERT INTO CapteursAppel(ID_CapteurAppel, NiveauAlerte_CapteurAppel) values(" + id +", 2)";
			if(typeCapteur.equals("Capteur de fumée"))
				request = "INSERT INTO CapteursFumee(ID_CapteurFumee, Seuil_CapteurFumee) values(" + id + ", 50)";
			if(typeCapteur.equals("Capteur de présence"))
				request = "INSERT INTO CapteursPresence(ID_CapteurPresence, Debut_CapteurPresence, Fin_CapteurPresence) values(" + id +", '00:00:00', '23:59:59')";
			if(typeCapteur.equals("Capteur de température"))
				request = "INSERT INTO CapteursTemperature(ID_CapteurTemperature, Min_CapteurTemperature, Max_CapteurTemperature) values(" + id +", 15, 27)";
			if(typeCapteur.equals("Capteur hygrométrique"))
				request = "INSERT INTO CapteursHygro(ID_CapteurHygro, Seuil_CapteurHygro) values(" + id + ", 90)";
			if(typeCapteur.equals("Capteur ouverture"))
				request = "INSERT INTO CapteursOuverture(ID_CapteurOuverture, Debut_CapteurOuverture, Fin_CapteurOuverture) values(" + id +", '00:00:00', '23:59:59')";
			statement.executeUpdate(request);
		}catch (SQLException e) {
			Tool.logger.error("addObject FAILED - SQL EXCEPTION : " + request);
		}
		return listObjects(); 
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
		//		String requestEmplacement = "SELECT ID_Emplacement FROM Emplacements INNER JOIN Residences ON Residences.ID_Residence=Emplacements.ID_Residence "
		//				+ "					WHERE Nom_Residence='" + attribute.get(3) + "' AND Zone_Emplacement='" + attribute.get(4) + "' AND Piece_Emplacement='" + attribute.get(5) + "'";
		String request = "";
		try {
			Statement statement = connection.createStatement(); 
			//			ResultSet resultEmplacement = statement.executeQuery(requestEmplacement); 
			//if(!resultEmplacement.next()) {
			// The "Emplacement" doesn't exit, we have to create it
			// We get the Residence ID
			String requestResidence = "SELECT ID_Residence FROM Residences WHERE Nom_Residence='" + attribute.get(3) + "'";
			ResultSet resultResidence = statement.executeQuery(requestResidence); 
			resultResidence.next();
			int id = resultResidence.getInt(1);
			// We create the "Emplacement" with the Residence ID
			//				String createEmplacement = "INSERT INTO Emplacements(Piece_Emplacement, Zone_Emplacement, ID_Residence) VALUES('" +attribute.get(5) + "', '" + attribute.get(4)+ "', '" + id + "')" ;
			//				statement.executeUpdate(createEmplacement); 
			// We get the ID from the "Emplacement" we just created
			//				String requestEmplacementNew = "SELECT ID_Emplacement FROM Emplacements WHERE Zone_Emplacement='" + attribute.get(4) + "' AND Piece_Emplacement='" + attribute.get(5) + "'"
			//						+ " AND ID_Residence='" + id + "'";
			//				ResultSet resultEmplacementNew = statement.executeQuery(requestEmplacementNew); 
			//				resultEmplacementNew.next();
			//	int idNew = resultEmplacementNew.getInt(1);
			//				int idNew = 6 ;
			request = "UPDATE Capteurs SET Type_Capteur = '"+ attribute.get(1) +"', Etat_Capteur = '"+ attribute.get(2) + "', ID_Emplacement = '" + "', Mac_Capteur = '"+ attribute.get(0) + "' WHERE ID_Capteur ='"+ attribute.get(0)+"'";
			//			}

			//				int idNew = 6 ;
			//				request = "UPDATE Capteurs SET Type_Capteur = '"+ attribute.get(1) +"', Etat_Capteur = '"+ attribute.get(2) + "', ID_Emplacement = '" + idNew + "' WHERE ID_Capteur ='"+ attribute.get(0)+"'";
			//			}
			//			else {
			//				request = "UPDATE Capteurs SET Type_Capteur = '"+ attribute.get(1) +"', Etat_Capteur = '"+ attribute.get(2) + "', ID_Emplacement = '" + resultEmplacement.getInt(1) + "' WHERE ID_Capteur ='"+ attribute.get(0)+"'";
			//			}

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
		String request = "SELECT ID_Capteur, Type_Capteur, Etat_Capteur, Nom_Residence, Niveau_Etage, Nom_Emplacement, Mac_Capteur " + 
				"FROM Capteurs LEFT JOIN Emplacements ON Capteurs.ID_Emplacement=Emplacements.ID_Emplacement " + 
				"LEFT JOIN Etages ON Emplacements.ID_Etage=Etages.ID_Etage " + 
				"LEFT JOIN Residences ON Etages.ID_Residence=Residences.ID_Residence"; 
		return getList(request);

	}

	private List<List<String>> listCapteurs(){
		String request ="Select * from Capteurs where Type_Capteur != 'Bracelet RFID';"; 
		return getList(request); 
	}

	private List<List<String>> listResidences() {
		String request = "SELECT * FROM Residences INNER JOIN Adresses ON Residences.ID_Addresse=Adresses.ID_Addresse;"; 
		return getList(request);
	}

	private List<List<String>> listReferentiels(){
		String request = "SELECT Type_Capteur FROM Referentiel_Capteurs ORDER BY Type_Capteur;";
		return getList(request);
	}

	private List<List<String>> listEmplacements(){
		String request = "SELECT * FROM Emplacements;";
		return getList(request);
	}

	private List<List<String>> listEtages(){
		String request = "SELECT * FROM Etages;";
		return getList(request);
	}

	private void report(Integer id, String message, Integer level) {
		String request = "INSERT INTO Notifications(Niveau_Notification, Date_Notification, Message_Notification, Etat_Notification, ID_Capteur)" 
				+ " VALUES(" + level + ", now(), '" + message + "', TRUE,'" + id + "');";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
		}catch(SQLException e) {
			System.out.println("error : " + request);
			Tool.logger.error("report FAILED - SQL EXCEPTION");
		}
	}

	private void reportRFID(Integer id) {
		Integer level = 3;
		try {
			Statement statement = connection.createStatement();
			String request = "SELECT * FROM CapteursRFID WHERE ID_CapteurRFID=" + id;
			ResultSet result = statement.executeQuery(request); 
			result.next();
			level = result.getInt(2);
		} catch (SQLException e) {}
		String message = "Le bracelet a généré un appel!";
		report(id, message, level);
	}

	private void reportCall(Integer id) {
		Integer level = 3;
		try {
			Statement statement = connection.createStatement();
			String request = "SELECT * FROM CapteursAppel WHERE ID_CapteurAppel=" + id;
			ResultSet result = statement.executeQuery(request); 
			result.next();
			level = result.getInt(2);
		} catch (SQLException e) {}
		String message = "Le bouton appel a généré une alerte!";
		report(id, message, level);
	}


	private void reportSmoke(Integer id, Integer smokeValue) {
		if(MemoryCache.addCacheData(id, (float)smokeValue)) {
			Float avg = MemoryCache.getAverageValue(id);
			// We get the parameters of the sensor
			Integer sensibility = -1;
			try {
				Statement statement = connection.createStatement();
				String request = "SELECT * FROM CapteursFumee WHERE ID_CapteurFumee=" + id;
				ResultSet result = statement.executeQuery(request); 
				result.next();
				sensibility = result.getInt(2);
			} catch (SQLException e) {}
			if(smokeValue>=sensibility) {
				String message = "Alarme incendie active! (" + Math.round(avg) + "% de fumée dernièrement)";
				report(id, message, 3);
			}
		}
	}

	private void reportMotion(Integer id) {
		if(MemoryCache.addCacheData(id)) {
			Time before = Time.valueOf("00:00:00");
			Time after = Time.valueOf("23:59:59");
			try {
				Statement statement = connection.createStatement();
				String request = "SELECT * FROM CapteursPresence WHERE ID_CapteurPresence=" + id;
				ResultSet result = statement.executeQuery(request); 
				result.next();
				before = result.getTime(2);
				after = result.getTime(3);
			} catch (SQLException e) {}
			Time current = Time.valueOf(new Time(System.currentTimeMillis()).toString());
			if(before.before(current) && after.after(current)) {
				String message = "Un mouvement a été détecté!";
				report(id, message, 1);
			}
		}
	}

	private void reportTemperature(Integer id, float temperature ) {
		if(MemoryCache.addCacheData(id, temperature)) {
			Integer avg = Math.round(MemoryCache.getAverageValue(id));
			Integer min = 15;
			Integer max = 27;
			try {
				Statement statement = connection.createStatement();
				String request = "SELECT * FROM CapteursTemperature WHERE ID_CapteurTemperature=" + id;
				ResultSet result = statement.executeQuery(request); 
				result.next();
				min = result.getInt(2);
				max = result.getInt(3);
			} catch (SQLException e) {}
			if(avg<min || avg>max) {
				String message = "Une température anormale a été détectée ("  + avg + "°C)";
				report(id, message, 2);
			}
		}
	}

	private void reportHygro(Integer id, Integer hygroValue) {
		if(MemoryCache.addCacheData(id, (float)hygroValue)) {
			Integer avg = Math.round(MemoryCache.getAverageValue(id));
			Integer seuil = 90;
			try {
				Statement statement = connection.createStatement();
				String request = "SELECT * FROM CapteursHygro WHERE ID_CapteurHygro=" + id;
				ResultSet result = statement.executeQuery(request); 
				result.next();
				seuil = result.getInt(2);
			} catch (SQLException e) {}
			if(seuil<=avg) {
				String message = "Un taux d''humidité anormal a été détecté (" + avg + "%)";
				report(id, message, 1);
			}
		}
	}

	private void reportOpening(Integer id) {
		if(MemoryCache.addCacheData(id)) {
			Time before = Time.valueOf("00:00:00");
			Time after = Time.valueOf("23:59:59");
			try {
				Statement statement = connection.createStatement();
				String request = "SELECT * FROM CapteursOuverture WHERE ID_CapteurOuverture=" + id;
				ResultSet result = statement.executeQuery(request); 
				result.next();
				before = result.getTime(2);
				after = result.getTime(3);
			} catch (SQLException e) {}
			Time current = Time.valueOf(new Time(System.currentTimeMillis()).toString());
			if(before.before(current) && after.after(current)) {
				String message = "Une ouverture de porte/fenêtre a été détectée!";
				report(id, message, 1);
			}
		}
	}

	private void changeAlert(Integer id, Boolean status) {
		String request = "UPDATE Notifications SET Etat_Notification=" +  Boolean.toString(status) + " WHERE ID_Notification=" + id;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
		}catch (SQLException e) {
			Tool.logger.error("changeAlert FAILED - SQL EXCEPTION");
		}
	}

	private List<List<String>> monitoring() {
		String request = "SELECT ID_Notification, Niveau_Notification, Date_Notification, Message_Notification, Etat_Notification, Notifications.ID_Capteur, Type_Capteur, " +
				"Mac_Capteur, Capteurs.ID_Emplacement, Nom_Emplacement, Niveau_Etage, Nom_Residence FROM Notifications " +
				"INNER JOIN Capteurs ON Notifications.ID_Capteur=Capteurs.ID_Capteur " + 
				"LEFT JOIN Emplacements ON Capteurs.ID_Emplacement=Emplacements.ID_Emplacement " + 
				"LEFT JOIN Etages ON Emplacements.ID_Etage=Etages.ID_Etage " + 
				"LEFT JOIN Residences ON Etages.ID_Residence=Residences.ID_Residence " + 
				"WHERE Date_Notification >= DATE_SUB(now(), INTERVAL 3 DAY) " + 
				"ORDER BY Date_Notification DESC ";
		return getList(request);
	}
	
	private boolean updateEmplacementObject(String ID_Capteur, String ID_Emplacement) {
		String request = "UPDATE Capteurs SET ID_Emplacement =" + ID_Emplacement +" WHERE ID_Capteur =" + ID_Capteur +";";
		try {
			System.out.println(request);
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			return true; 
		}catch (SQLException e) {
			Tool.logger.error("updateEmplacementObject FAILED - SQL EXCEPTION");
			return false;
		}
		
	}
	
	private boolean deleteEmplacementObject(String ID_Capteur) {
		String request = "UPDATE Capteurs SET ID_Emplacement = null WHERE ID_Capteur = '" + ID_Capteur +"'";
		try {
			System.out.println(request);
			Statement statement = connection.createStatement();
			statement.executeUpdate(request);
			return true; 
		}catch (SQLException e) {
			Tool.logger.error("deleteEmplacementObject FAILED - SQL EXCEPTION");
			return false;
		}
	}


}
