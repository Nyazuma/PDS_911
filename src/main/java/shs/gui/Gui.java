package shs.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import com.sun.istack.internal.logging.Logger;

import shs.gui.window.Authentification;
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
		changeWindow(WindowList.AUTHENTIFICATION);
		this.setVisible(true);
	}
	
	public void changeWindow(WindowList newWindow) {
		switch(newWindow) {
		case AUTHENTIFICATION : 
			this.setContentPane(new Authentification(controller));
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