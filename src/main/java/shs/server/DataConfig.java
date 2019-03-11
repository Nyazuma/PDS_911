package shs.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import shs.common.Tool;

//*******************************


public class DataConfig {
	
	private static final String PROPERTIES_FILE = "configuration"; 
	private static  String PROPERTY_URL; 
	private static  String PROPERTY_DB; 
	private static  String PROPERTY_DRIVER;
	private static  String PROPERTY_USERNAME;
	private static  String PROPERTY_PASSWORD;
	private static  String PROPERTY_NB_CONNEXION; 
	private static  String PROPERTY_USER_ADMIN; 
	private static  String PROPERTY_PASSWORD_ADMIN; 


	public static void getInstanceConfig() {
		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierConfiguration = classLoader.getResourceAsStream(PROPERTIES_FILE);
		
		if (fichierConfiguration == null) {
			
			Tool.logger.info("The properties file " + PROPERTIES_FILE + " wasn't found." );
		}

		try {
			Tool.logger.info("Properties file loading");
			properties.load(fichierConfiguration);
			PROPERTY_URL = properties.getProperty("PROPERTY_URL");
			PROPERTY_DB = properties.getProperty("PROPERTY_DB");
			PROPERTY_DRIVER = properties.getProperty("PROPERTY_DRIVER");
			PROPERTY_USERNAME = properties.getProperty("PROPERTY_USERNAME");
			PROPERTY_PASSWORD = properties.getProperty("PROPERTY_PASSWORD");
			PROPERTY_NB_CONNEXION = properties.getProperty("PROPERTY_NB_CONNEXION"); 
			PROPERTY_USER_ADMIN = properties.getProperty("PROPERTY_USER_ADMIN"); 
			PROPERTY_PASSWORD_ADMIN = properties.getProperty("PROPERTY_PASSWORD_ADMIN"); 

			

		} catch ( FileNotFoundException e ) {
			Tool.logger.info( "The properties file " + PROPERTIES_FILE + " wasn't found.");
		} catch ( IOException e ) {
			
			Tool.logger.info( "Impossible to load the properties file " + PROPERTIES_FILE, e );
		}

	}


	public static String getPROPERTY_URL() {
		return PROPERTY_URL;
	}
	public static void setPROPERTY_URL(String NEW_PROPERTY_URL) {
		PROPERTY_URL = NEW_PROPERTY_URL;
	}
	
	public static String getPROPERTY_DB() {
		return PROPERTY_DB;
	}
	public static void setPROPERTY_DB(String NEW_PROPERTY_DB) {
		PROPERTY_DB = NEW_PROPERTY_DB;
	}

	public static String getPROPERTY_DRIVER() {
		return PROPERTY_DRIVER;
	}
	public static void setPROPERTY_DRIVER(String NEW_PROPERTY_DRIVER) {
		PROPERTY_DRIVER = NEW_PROPERTY_DRIVER;
	}

	
	public static String getPROPERTY_USERNAME() {
		return PROPERTY_USERNAME;
	}
	public static void setPROPERTY_USERNAME(String NEW_PROPERTY_USERNAME) {
		PROPERTY_USERNAME = NEW_PROPERTY_USERNAME;
	}
	
	
	public static String getPROPERTY_PASSWORD() {
		return PROPERTY_PASSWORD;
	}
	public static void setPROPERTY_PASSWORD(String NEW_PROPERTY_PASSWORD) {
		PROPERTY_PASSWORD = NEW_PROPERTY_PASSWORD;
	}
	
	
	public static String getPROPERTY_NB_CONNEXION() {
		return PROPERTY_NB_CONNEXION;
	}
	public static void setPROPERTY_NB_CONNEXION(String NEW_PROPERTY_NB_CONNEXION) {
		PROPERTY_NB_CONNEXION = NEW_PROPERTY_NB_CONNEXION;
	}


	public static String getPROPERTY_USER_ADMIN() {
		return PROPERTY_USER_ADMIN;
	}
	public static void setPROPERTY_USER_ADMIN(String NEW_PROPERTY_USER_ADMIN) {
		PROPERTY_USER_ADMIN = NEW_PROPERTY_USER_ADMIN;
	}


	public static String getPROPERTY_PASSWORD_ADMIN() {
		return PROPERTY_PASSWORD_ADMIN;
	}
	public static void setPROPERTY_MOT_DE_PASSE_ADMIN(String NEW_PROPERTY_PASSWORD_ADMIN) {
		PROPERTY_PASSWORD_ADMIN = NEW_PROPERTY_PASSWORD_ADMIN;
	}


}
