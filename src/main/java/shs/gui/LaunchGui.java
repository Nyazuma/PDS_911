package shs.gui;

import java.awt.EventQueue;

import shs.server.DataConfig;

public class LaunchGui {

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataConfig.getInstanceConfig();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					GuiController controller = new GuiController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
