package shs.gui;

import javax.swing.JFrame;

import shs.common.Tool;
import shs.gui.window.Authentication;
import shs.gui.window.ObjectGestion;
import shs.gui.window.WindowList;


public class Gui extends JFrame{ 
	
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
	
	/**
	 * Load the precized panel in the JFrame
	 */
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
			Tool.logger.info("#ERROR : Gui > ChangeWindow : Unknow window");
		}
	}

}