package testObject;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import connectionPool.DataSource;

public class DataSourceTest {

	@Test
	public void testGetInstanceConfig() throws SQLException {
		System.out.println("getInstanceConfig");
		
		DataSource.getInstanceConfig();
		
		assertEquals("www.google.fr", DataSource.getPROPERTY_URL());
		assertEquals("eze", DataSource.getPROPERTY_DRIVER()); 
		
		assertEquals("faizaan", DataSource.getPROPERTY_NOM_UTILISATEUR());
		
		assertEquals("faizaanmdp", DataSource.getPROPERTY_MOT_DE_PASSE()); 
		
		assertEquals("admin", DataSource.getPROPERTY_NOM_UTILISATEUR_ADMIN()); 
		
		assertEquals("adminmdp", DataSource.getPROPERTY_MOT_DE_PASSE_ADMIN());
		
		assertEquals("10", DataSource.getPROPERTY_NB_CONNEXION());
	}

}
