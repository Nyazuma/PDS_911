package testobject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import connectionpool.DataConfig;
import connectionpool.JDBCConnectionPool;
import interfacegestion.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ControllerTest {

	@Test
	public void test() {
		DataConfig.getInstanceConfig();
		JDBCConnectionPool poolConnection = new JDBCConnectionPool();		
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), poolConnection.statusConnection());
		Controller control = new Controller(poolConnection);
		assertTrue(control.connection("Justin911", "911"));
		int nombre = control.nbObject();
		assertTrue(control.addObject("Capteur Ouverture"));
		assertTrue(control.nbObject()==nombre+1);
		// 
	}

}
