package shs.connection.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import shs.gui.GuiSession;
import shs.server.DataConfig;
import shs.server.JDBCConnectionPool;

public class SessionTest {

	@Test
	public void test() {
		DataConfig.getInstanceConfig();
		JDBCConnectionPool poolConnection = new JDBCConnectionPool();		
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		GuiSession session = new GuiSession();
		assertTrue(session.connection("Justin911", "911"));
		int nombre = session.nbObject();
		assertTrue(session.addObject("Capteur Ouverture"));
		assertTrue(session.nbObject()==nombre+1);
		// 
	}

}
