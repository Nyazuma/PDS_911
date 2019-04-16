package shs.connection.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import shs.server.DataConfig;
import shs.server.JDBCConnectionPool;

public class JDBCConnectionPoolTest {

	private JDBCConnectionPool connectionPool;
	
	@Before
	public void initJDBCConnectionPool() {
		System.out.println("initJDBCConnectionPool");
		
		DataConfig.getInstanceConfig();
		connectionPool = new JDBCConnectionPool();
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), connectionPool.statusConnection());
		Connection co1 = null;
		try {
			co1 = connectionPool.getConnection();
			Statement statement = co1.createStatement();
			statement.executeUpdate("DELETE FROM test WHERE id=1");
			connectionPool.closeConnection(co1);
		}catch (SQLException e) {
			connectionPool.closeConnection(co1);
		}
	}

	@Test
	public void testJDBCConnectionPool() {
		System.out.println("testJDBCConnectionPool");
		
		Connection co1 = connectionPool.getConnection();
		Connection co2 = connectionPool.getConnection();
		assertNotEquals(co1, co2);
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION())-2, connectionPool.statusConnection());
		// Test requete
		try {
			Statement statement = co1.createStatement();
			statement.executeUpdate("INSERT INTO test(id, nom, MotDePasse) VALUES (1, 'Jean-Paul', 'toto')");
			ResultSet resultat = statement.executeQuery( "SELECT nom  FROM test WHERE id=1");
			resultat.next();
			assertEquals(resultat.getString("nom"), "Jean-Paul");
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		connectionPool.closeConnection(co1);
		connectionPool.closeConnection(co2);
		assertEquals(Integer.parseInt(DataConfig.getPROPERTY_NB_CONNEXION()), connectionPool.statusConnection());
	}
}
