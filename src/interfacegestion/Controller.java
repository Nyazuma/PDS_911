package interfacegestion;


import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionpool.DataConfig;
import connectionpool.JDBCConnectionPool;

public class Controller {

	/**
	 * Initial poolConnection Object
	 */
	private JDBCConnectionPool poolConnection; 

	/**
	 * Constructeur
	 */
	public Controller(JDBCConnectionPool pool) {
		this.poolConnection = pool; 
	}

	/**
	 * Check connection. 
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	public boolean connection(String identifiant, String password) {

		String requete = "Select count(*) from Personnel where Identifiant_Personnel='" + identifiant + "'"; 
		String requete2 = "Select count(*) from Personnel where Identifiant_Personnel='" + identifiant + "' and MotDePasse_Personnel = '" + password + "'"; 

		System.out.println("Connection - Controller");

		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			if(resultat.getInt(1) == 1) {
				Statement statement2 = co1.createStatement(); 
				ResultSet resultat2 = statement2.executeQuery(requete2); 
				resultat2.next(); 
				System.out.println("debug : " + resultat2.getInt(1));
				if (resultat2.getInt(1)==1) {
					poolConnection.closeConnection(co1);
					System.out.println("Connection SUCCEED");
					return true; 
				}
				else {
					poolConnection.closeConnection(co1);
					System.out.println("Connection FAILED");
					return false; 
				}
			}
			else {
				poolConnection.closeConnection(co1);
				System.out.println("Connection FAILED");
				return false; 
			}


		}catch (SQLException e) {
			poolConnection.closeConnection(co1);
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
	public int nbObject() {
		String requete = "Select count(*) from Capteurs";  
		
		DataConfig.getInstanceConfig();
		poolConnection = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			System.out.println("nbObject SUCCEED");
			poolConnection.closeConnection(co1);
			return resultat.getInt(1); 
			
		}catch (SQLException e) {
			poolConnection.closeConnection(co1);
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
	public boolean addObject(String typeCapteur) {
		String requete = "INSERT INTO Capteurs (Type_Capteur, Etat_Capteur, ID_Emplacement) VALUES ('"+ typeCapteur +"', 1, 1)"; 
		
		DataConfig.getInstanceConfig();
		poolConnection = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			statement.executeUpdate(requete);
			System.out.println("addObject SUCCED");
			return true; 
			
		}catch (SQLException e) {
			poolConnection.closeConnection(co1);
			System.out.println("addObject FAILED - SQL EXCEPTION");
			System.out.println(requete);
			e.printStackTrace();
			return false; 
		}
	}

}
