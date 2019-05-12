package shs.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import shs.common.Tool;

public class DataConfigClient {
	
	private static final String PROPERTIES_FILE = "configurationClient"; 
	private static String SERVER_URL; 
	private static Integer SERVER_PORT;
	
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
			SERVER_URL = properties.getProperty("SERVER_URL");
			SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));
		} catch ( FileNotFoundException e ) {
			Tool.logger.info( "The properties file " + PROPERTIES_FILE + " wasn't found.");
		} catch ( IOException e ) {
			Tool.logger.info( "Impossible to load the properties file " + PROPERTIES_FILE, e );
		}
	}

	public static String getSERVER_URL() {
		return SERVER_URL;
	}

	public static Integer getSERVER_PORT() {
		return SERVER_PORT;
	}


}
