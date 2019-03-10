package shs.gui;

import javax.swing.JFrame;

import com.sun.istack.internal.logging.Logger;

import shs.gui.window.Authentication;
import shs.gui.window.ObjectGestion;
import shs.gui.window.WindowList;
import shs.server.DataConfig;


public class Gui extends JFrame{

	private static Logger logger = Logger.getLogger(DataConfig.class); 
	
	private WindowList currentScreen;
	private GuiController controller;
	
	/**
	 * Create the application.
	 */
	public Gui(GuiController controller) {
		this.controller = controller;
		this.setBounds(100, 100, 1400, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		changeWindow(WindowList.AUTHENTICATION);
		this.setVisible(true);
	}
	
	public void changeWindow(WindowList newWindow) {
		switch(newWindow) {
		case AUTHENTICATION : 
			this.setContentPane(new Authentication(controller));
			repaint();
			break;
		case OBJECTGESTION : 
			this.setContentPane(new ObjectGestion(controller));
			repaint();
			break;
		default :
			logger.info("#ERROR : Gui > ChangeWindow : Unknow window");
		}
	}

}