package shs.gui;

import shs.server.Controller;
import shs.server.JDBCConnectionPool;

public class GuiController {

	// TEMPORARY
	// TODO : Generate a JSON to send to the Controller instead of using a Controller object. Each of this function should be adapted
	// It should also contains the operator identificator
	private Controller controller;
	
	// JFrame of the application
	protected Gui gui;
	
	public GuiController() {
		// Get a dedicated connection from the pool to the object
		JDBCConnectionPool pool = new JDBCConnectionPool();
		controller = new Controller(pool);
		this.gui=new Gui(this);
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

	/**
	 * Getter of the Gui
	 */
	public Gui getGui() {
		return gui;
	}
}
