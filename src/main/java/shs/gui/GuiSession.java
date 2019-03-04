package shs.gui;

import java.sql.Connection;

import shs.server.Controller;
import shs.server.JDBCConnectionPool;

public class GuiSession {

	// TEMPORARY
	// TODO : Generate a JSON to send to the Controller instead of using a Controller object. Each of this function should be adapted
	// It should also contains the operator identificator
	private Connection connection;
	Controller controller;
	
	
	public GuiSession() {
		// Get a dedicated connection from the pool to the object
		JDBCConnectionPool pool = new JDBCConnectionPool();
		connection = pool.getConnection();
		controller = new Controller();
	}
	
	public boolean connection(String identifiant, String password) {
		return controller.connection(connection, identifiant, password);
	}

	public int nbObject() {
		return controller.nbObject(connection);
	}
	
	public boolean addObject(String typeCapteur) {
		return controller.addObject(connection, typeCapteur);
	}

}
