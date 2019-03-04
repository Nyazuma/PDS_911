package shs.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
	
	/**
	 * Check connection. 
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	public boolean connection(Connection connection, String identifiant, String password) {

		String requete = "Select count(*) from Personnel where Identifiant_Personnel='" + identifiant + "'"; 
		String requete2 = "Select count(*) from Personnel where Identifiant_Personnel='" + identifiant + "' and MotDePasse_Personnel = '" + password + "'"; 

		System.out.println("Connection - Controller");

		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			if(resultat.getInt(1) == 1) {
				Statement statement2 = connection.createStatement(); 
				ResultSet resultat2 = statement2.executeQuery(requete2); 
				resultat2.next(); 
				System.out.println("debug : " + resultat2.getInt(1));
				if (resultat2.getInt(1)==1) {
					System.out.println("Connection SUCCEED");
					return true; 
				}
				else {
					System.out.println("Connection FAILED");
					return false; 
				}
			}
			else {
				System.out.println("Connection FAILED");
				return false; 
			}


		}catch (SQLException e) {
			System.out.println("Connection FAILED - SQL EXCEPTION");
			e.printStackTrace();
			return false; 
		}
	}
	
	/**
	 * Return the object number associate to the account.
	 * 
	 * @return
	 */
	public int nbObject(Connection connection) {
		String requete = "Select count(*) from Capteurs";  
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			System.out.println("nbObject SUCCEED");
			return resultat.getInt(1); 
			
		}catch (SQLException e) {
			System.out.println("nbObject FAILED - SQL EXCEPTION");
			return 0; 
		}
	}
	
	/**
	 * Add an object to the base.
	 * 
	 * @param typeCapteur
	 * @return
	 */
	public boolean addObject(Connection connection, String typeCapteur) {
		String requete = "INSERT INTO Capteurs (Type_Capteur, Etat_Capteur, ID_Emplacement) VALUES ('"+ typeCapteur +"', 1, 1)"; 
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(requete);
			System.out.println("addObject SUCCED");
			return true; 
			
		}catch (SQLException e) {
			System.out.println("addObject FAILED - SQL EXCEPTION");
			System.out.println(requete);
			e.printStackTrace();
			return false; 
		}
	}

}
