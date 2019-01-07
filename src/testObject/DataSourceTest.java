package testObject;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import connectionPool.DataConfig;

public class DataSourceTest {

	@Test
	public void testGetInstanceConfig() throws SQLException {
		System.out.println("getInstanceConfig");
		
		DataConfig.getInstanceConfig();
		
		assertEquals("www.google.fr", DataConfig.getPROPERTY_URL());
		assertEquals("eze", DataConfig.getPROPERTY_DRIVER()); 
		assertEquals("faizaan", DataConfig.getPROPERTY_NOM_UTILISATEUR());
		assertEquals("faizaanmdp", DataConfig.getPROPERTY_MOT_DE_PASSE()); 
		assertEquals("admin", DataConfig.getPROPERTY_NOM_UTILISATEUR_ADMIN()); 
		assertEquals("adminmdp", DataConfig.getPROPERTY_MOT_DE_PASSE_ADMIN());
		assertEquals("10", DataConfig.getPROPERTY_NB_CONNEXION());
	}

}
