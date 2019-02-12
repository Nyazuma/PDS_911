package interfaceGestion;


import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionPool.DataConfig;
import connectionPool.JDBCConnectionPool;

public class Controller {

	private JDBCConnectionPool poolConnection; 

	public Controller(JDBCConnectionPool pool) {
		this.poolConnection = pool; 
	}

	public boolean connection(String identifiant, String password) {

		String requete = "Select count(*) from Personel where identifiant=" + identifiant; 
		String requete2 = "Select count(*) from Personel where identifiant=" + identifiant + "and password = " + password; 

		System.out.println("Connection - Controller");

		DataConfig.getInstanceConfig();
		poolConnection = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			if(resultat.getInt(0) == 1) {
				Statement statement2 = co1.createStatement(); 
				ResultSet resultat2 = statement2.executeQuery(requete2); 
				resultat2.next(); 
				if (resultat2.getInt(0)==1) {
					poolConnection.closeConnection(co1);
					System.out.println("Conection SUCCED");
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
			return false; 
		}
	}
	
	public int nbObjectConnect() {
		String requete = "Select count(*) from Capteurs"; 
		int nbObject = 0; 
		
		DataConfig.getInstanceConfig();
		poolConnection = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			resultat.next(); 
			while(!resultat.isLast()) {
				nbObject++; 
				resultat.next(); 
			}
			return nbObject; 
			
		}catch (SQLException e) {
			poolConnection.closeConnection(co1);
			System.out.println("Connection FAILED - SQL EXCEPTION");
			return 0; 
		}
	}
	
	public boolean addObject(String typeCapteur) {
		String requete = "INSERT INTO table (Type_Capteur, Etat_Capteur) VALUES ('"+ typeCapteur +"', '1')"; 
		
		DataConfig.getInstanceConfig();
		poolConnection = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Connection co1 = null;
		try {
			co1 = poolConnection.getConnection();
			Statement statement = co1.createStatement();
			statement.executeUpdate(requete);
			return true; 
			
		}catch (SQLException e) {
			poolConnection.closeConnection(co1);
			System.out.println("Connection FAILED - SQL EXCEPTION");
			return false; 
		}
	}

}
