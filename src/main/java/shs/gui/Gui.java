package shs.gui;

import javax.swing.JFrame;

import shs.common.Tool;
import shs.gui.window.Authentication;
import shs.gui.window.Map;
import shs.gui.window.Menu;
import shs.gui.window.Monitoring;
import shs.gui.window.ObjectGestion;
import shs.gui.window.ObjectModification;
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
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Load the precized panel in the JFrame
	 */
	public void changeWindow(WindowList newWindow) {
		switch(newWindow) {
		case MENU : 
			this.setContentPane(new Menu(controller));
			repaint();
			setVisible(true);
			break;
		case AUTHENTICATION : 
			this.setContentPane(new Authentication(controller));
			repaint();
			setVisible(true);
			break;
		case OBJECTGESTION : 
			this.setContentPane(new ObjectGestion(controller));
			repaint();
			setVisible(true);
			break;
		case OBJECTMODIFICATION :
			this.setContentPane(new ObjectModification(controller));
			repaint();
			setVisible(true);
			break;
		case MONITORING :
			this.setContentPane(new Monitoring(controller));
			repaint();
			setVisible(true);
			break;
		case MAP : 
			this.setContentPane(new Map(controller));
			repaint();  
			setVisible(true); 
			break; 
		default :
			Tool.logger.info("#ERROR : Gui > ChangeWindow : Unknow window");
		}
	}

}