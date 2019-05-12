package shs.simulator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import shs.common.Tool;

public class DataConfigSimu {

	private static final String PROPERTIES_FILE = "configurationSimu"; 
	private static String SERVER_URL; 
	private static Integer SERVER_PORT;
	private static Integer MIN_TIME_AUTO; 
	private static Integer MAX_TIME_AUTO;
	private static Integer MIN_HYGRO_VALUE; 
	private static Integer MAX_HYGRO_VALUE;
	private static Integer MIN_SMOKE_VALUE;
	private static Integer MAX_SMOKE_VALUE;
	private static Integer MIN_TEMPERATURE_VALUE;
	private static Integer MAX_TEMPERATURE_VALUE;
	
	
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
			MIN_TIME_AUTO = Integer.parseInt(properties.getProperty("MIN_TIME_AUTO"));
			MAX_TIME_AUTO = Integer.parseInt(properties.getProperty("MAX_TIME_AUTO"));
			MIN_HYGRO_VALUE = Integer.parseInt(properties.getProperty("MIN_HYGRO_VALUE"));
			MAX_HYGRO_VALUE = Integer.parseInt(properties.getProperty("MAX_HYGRO_VALUE"));
			MIN_SMOKE_VALUE= Integer.parseInt(properties.getProperty("MIN_SMOKE_VALUE"));
			MAX_SMOKE_VALUE= Integer.parseInt(properties.getProperty("MAX_SMOKE_VALUE"));
			MIN_TEMPERATURE_VALUE= Integer.parseInt(properties.getProperty("MIN_TEMPERATURE_VALUE"));
			MAX_TEMPERATURE_VALUE= Integer.parseInt(properties.getProperty("MAX_TEMPERATURE_VALUE"));

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
	
	public static Integer getMIN_TIME_AUTO() {
		return MIN_TIME_AUTO;
	}

	public static Integer getMAX_TIME_AUTO() {
		return MAX_TIME_AUTO;
	}

	public static Integer getMIN_HYGRO_VALUE() {
		return MIN_HYGRO_VALUE;
	}

	public static Integer getMAX_HYGRO_VALUE() {
		return MAX_HYGRO_VALUE;
	}

	public static Integer getMIN_SMOKE_VALUE() {
		return MIN_SMOKE_VALUE;
	}

	public static Integer getMAX_SMOKE_VALUE() {
		return MAX_SMOKE_VALUE;
	}

	public static Integer getMIN_TEMPERATURE_VALUE() {
		return MIN_TEMPERATURE_VALUE;
	}

	public static Integer getMAX_TEMPERATURE_VALUE() {
		return MAX_TEMPERATURE_VALUE;
	}

}
