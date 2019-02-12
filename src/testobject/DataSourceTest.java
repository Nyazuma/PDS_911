package testobject;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import connectionpool.DataConfig;

public class DataSourceTest {

	@Test
	public void testGetInstanceConfig() throws SQLException {
		System.out.println("getInstanceConfig");
		
		DataConfig.getInstanceConfig();
		
		assertEquals("127.0.0.1:3306", DataConfig.getPROPERTY_URL());
		assertEquals("jdbc:mysql", DataConfig.getPROPERTY_DRIVER()); 
		assertEquals("911_user", DataConfig.getPROPERTY_NOM_UTILISATEUR());
		assertEquals("toto", DataConfig.getPROPERTY_MOT_DE_PASSE()); 
		assertEquals("root", DataConfig.getPROPERTY_NOM_UTILISATEUR_ADMIN()); 
		assertEquals("root", DataConfig.getPROPERTY_MOT_DE_PASSE_ADMIN());
		assertEquals("10", DataConfig.getPROPERTY_NB_CONNEXION());
	}

}
