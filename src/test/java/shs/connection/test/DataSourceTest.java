package shs.connection.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import shs.server.DataConfig;

public class DataSourceTest {

	@Test
	public void testGetInstanceConfig() throws SQLException {
		System.out.println("getInstanceConfig");
		
		DataConfig.getInstanceConfig();
		
		assertEquals("127.0.0.1:3306", DataConfig.getPROPERTY_URL());
		assertEquals("jdbc:mysql", DataConfig.getPROPERTY_DRIVER()); 
		assertEquals("911_user", DataConfig.getPROPERTY_USERNAME());
		assertEquals("toto", DataConfig.getPROPERTY_PASSWORD()); 
		assertEquals("root", DataConfig.getPROPERTY_USER_ADMIN()); 
		assertEquals("root", DataConfig.getPROPERTY_PASSWORD_ADMIN());
		assertEquals("10", DataConfig.getPROPERTY_NB_CONNEXION());
	}

}
