package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shs.gui.GuiController;

public class Menu extends JPanel implements ActionListener{

	private GuiController controller; 

	private JLabel titleLabel;
	private JButton ObjectGestionButton; 
	private JButton monitoringButton; 
	private JButton mapButton; 
	private JButton statisticsButton; 

	public Menu(GuiController controller) {
		this.controller = controller; 

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		titleLabel = new JLabel("Veuillez indiquer l'action que vous souhaitez r\u00E9aliser : ");
		titleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		titleLabel.setBounds(541, 128, 400, 22);
		this.add(titleLabel);

		ObjectGestionButton = new JButton("Gestion des objets");
		ObjectGestionButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		ObjectGestionButton.setBounds(703, 449, 320, 164);
		ObjectGestionButton.setBackground(new Color(0, 204, 153));
		ObjectGestionButton.addActionListener(this); 
		this.add(ObjectGestionButton);

		monitoringButton = new JButton("Surveillance des objets");
		monitoringButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		monitoringButton.setBounds(703, 260, 320, 175);
		monitoringButton.setBackground(new Color(255, 153, 204));
		monitoringButton.addActionListener(this); 
		this.add(monitoringButton);

		mapButton = new JButton("Plan de la r\u00E9sidence");
		mapButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		mapButton.setBounds(363, 261, 328, 173);
		mapButton.setBackground(new Color(255, 102, 102));
		mapButton.addActionListener(this); 
		this.add(mapButton);

		statisticsButton = new JButton("Statistiques des objets");
		statisticsButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		statisticsButton.setBounds(363, 447, 328, 166);
		statisticsButton.setBackground(new Color(153, 255, 204));
		statisticsButton.addActionListener(this); 
		this.add(statisticsButton);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(ObjectGestionButton)) {
			controller.getGui().changeWindow(WindowList.OBJECTGESTION);
		}
		
		if(event.getSource().equals(monitoringButton)) {
			controller.getGui().changeWindow(WindowList.MONITORING);
		}
		if(event.getSource().equals(mapButton)) {
			
		}
		if(event.getSource().equals(statisticsButton)) {
			
		}
		
	}

}
