package shs.gui;

import java.sql.Connection;

import shs.server.Controller;
import shs.server.JDBCConnectionPool;

public class GuiSession {

	// TEMPORARY
	// TODO : Generate a JSON to send to the Controller instead of using a Controller object. Each of this function should be adapted
	// It should also contains the operator identificator
	private Controller controller;
	
	
	public GuiSession() {
		// Get a dedicated connection from the pool to the object
		JDBCConnectionPool pool = new JDBCConnectionPool();
		controller = new Controller(pool);
	}
	
	public boolean connection(String identifiant, String password) {
		return controller.connection(identifiant, password);
	}

	public int nbObject() {
		return controller.nbObject();
	}
	
	public boolean addObject(String typeCapteur) {
		return controller.addObject(typeCapteur);
	}

}
