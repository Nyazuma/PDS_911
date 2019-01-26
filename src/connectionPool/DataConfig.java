package connectionPool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import com.sun.istack.internal.logging.Logger;

//*******************************


public class DataConfig {

	
	private static final String FICHIER_CONFIGURATION = "configuration"; 
	private static Logger logger = Logger.getLogger(DataConfig.class); 
	private static  String PROPERTY_URL; 
	private static  String PROPERTY_DB; 
	private static  String PROPERTY_DRIVER;
	private static  String PROPERTY_NOM_UTILISATEUR;
	private static  String PROPERTY_MOT_DE_PASSE;
	private static  String PROPERTY_NB_CONNEXION; 
	private static  String PROPERTY_NOM_UTILISATEUR_ADMIN; 
	private static  String PROPERTY_MOT_DE_PASSE_ADMIN; 


	public static void getInstanceConfig() {
		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierConfiguration = classLoader.getResourceAsStream(FICHIER_CONFIGURATION);
		
		if (fichierConfiguration == null) {
			
			logger.info("Le fichier configuration " + FICHIER_CONFIGURATION + " est introuvable." );
		}

		try {
			logger.info("Récupération des instances du fichier de configuration");
			properties.load(fichierConfiguration);
			PROPERTY_URL = properties.getProperty("PROPERTY_URL");
			PROPERTY_DB = properties.getProperty("PROPERTY_DB");
			PROPERTY_DRIVER = properties.getProperty("PROPERTY_DRIVER");
			PROPERTY_NOM_UTILISATEUR = properties.getProperty("PROPERTY_USERNAME");
			PROPERTY_MOT_DE_PASSE = properties.getProperty("PROPERTY_PASSWORD");
			PROPERTY_NB_CONNEXION = properties.getProperty("PROPERTY_NB_CONNEXION"); 
			PROPERTY_NOM_UTILISATEUR_ADMIN = properties.getProperty("PROPERTY_USER_ADMIN"); 
			PROPERTY_MOT_DE_PASSE_ADMIN = properties.getProperty("PROPERTY_PASSWORD_ADMIN"); 

			

		} catch ( FileNotFoundException e ) {
			logger.info( "Le fichier de configuration " + FICHIER_CONFIGURATION + " est introuvable.");
		} catch ( IOException e ) {
			
			logger.info( "Impossible de charger le fichier de configuration " + FICHIER_CONFIGURATION, e );
		}

	}


	public static String getPROPERTY_URL() {
		return PROPERTY_URL;
	}
	public static void setPROPERTY_URL(String PROPERTY_URL) {
		PROPERTY_URL = PROPERTY_URL;
	}
	
	public static String getPROPERTY_DB() {
		return PROPERTY_DB;
	}
	public static void setPROPERTY_DB(String PROPERTY_DB) {
		PROPERTY_DB = PROPERTY_DB;
	}

	public static String getPROPERTY_DRIVER() {
		return PROPERTY_DRIVER;
	}
	public static void setPROPERTY_DRIVER(String PROPERTY_DRIVER) {
		PROPERTY_DRIVER = PROPERTY_DRIVER;
	}

	
	public static String getPROPERTY_NOM_UTILISATEUR() {
		return PROPERTY_NOM_UTILISATEUR;
	}
	public static void setPROPERTY_NOM_UTILISATEUR(String PROPERTY_NOM_UTILISATEUR) {
		PROPERTY_NOM_UTILISATEUR = PROPERTY_NOM_UTILISATEUR;
	}
	
	
	public static String getPROPERTY_MOT_DE_PASSE() {
		return PROPERTY_MOT_DE_PASSE;
	}
	public static void setPROPERTY_MOT_DE_PASSE(String PROPERTY_MOT_DE_PASSE) {
		PROPERTY_MOT_DE_PASSE = PROPERTY_MOT_DE_PASSE;
	}
	
	
	public static String getPROPERTY_NB_CONNEXION() {
		return PROPERTY_NB_CONNEXION;
	}
	public static void setPROPERTY_NB_CONNEXION(String PROPERTY_NB_CONNEXION) {
		PROPERTY_NB_CONNEXION = PROPERTY_NB_CONNEXION;
	}


	public static String getPROPERTY_NOM_UTILISATEUR_ADMIN() {
		return PROPERTY_NOM_UTILISATEUR_ADMIN;
	}
	public static void setPROPERTY_NOM_UTILISATEUR_ADMIN(String PROPERTY_NOM_UTILISATEUR_ADMIN) {
		PROPERTY_NOM_UTILISATEUR_ADMIN = PROPERTY_NOM_UTILISATEUR_ADMIN;
	}


	public static String getPROPERTY_MOT_DE_PASSE_ADMIN() {
		return PROPERTY_MOT_DE_PASSE_ADMIN;
	}
	public static void setPROPERTY_MOT_DE_PASSE_ADMIN(String PROPERTY_MOT_DE_PASSE_ADMIN) {
		PROPERTY_MOT_DE_PASSE_ADMIN = PROPERTY_MOT_DE_PASSE_ADMIN;
	}


}
