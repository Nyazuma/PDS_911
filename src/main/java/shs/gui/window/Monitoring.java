package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;

import shs.common.Tool;
import shs.gui.GuiController;

public class Monitoring extends JPanel implements ActionListener{

	private GuiController controller;
	
	protected JButton returnButton; 
	protected JTable table;
	protected JButton displayMapButton;
	protected JButton disableAlertButton;
	protected JButton refreshButton;
	protected JLabel lowAlertIconPanel;
	protected JLabel highAlertIconPanel;
	protected JLabel lowAlertLabel;
	protected JLabel highAlertLabel;
	protected JButton modifySensorButton;
	protected JButton toggleDisabledAlertsButton;
	protected JLabel detailsAlertLabel;
	protected JButton detailsButton;
	protected JTextPane detailAlertText;
	protected JLabel listAlertsLabel;
	
	private Boolean displayDisabledAlerts;
	
	public Monitoring(GuiController controller) {
		
		this.controller=controller;
		
		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);
		
		returnButton = new JButton("Retour");
		returnButton.setBounds(12, 15, 90, 44);
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.addActionListener(this);
		add(returnButton);
		
		table = new JTable();
		table.setBounds(62, 212, 529, 572);
		add(table);
		
		displayMapButton = new JButton("Visualiser sur le plan");
		displayMapButton.addActionListener(this);
		displayMapButton.setBounds(770, 730, 166, 54);
		add(displayMapButton);
		
		disableAlertButton = new JButton("Désactiver l'alerte");
		disableAlertButton.setBounds(1129, 730, 163, 54);
		add(disableAlertButton);
		
		refreshButton = new JButton("Rafraichir manuellement");
		refreshButton.setBounds(365, 156, 227, 25);
		add(refreshButton);
		
		URL url = null;
		ImageIcon imageIcon = null;
		try {
			url = this.getClass().getResource("/images/lowAlert.gif");
			imageIcon = new ImageIcon(url);
			lowAlertIconPanel = new JLabel(imageIcon);
			lowAlertIconPanel.setBounds(860, 60, 150, 150);
			add(lowAlertIconPanel);
		}catch(Exception e) {
			Tool.logger.error("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
		
		try {
			url = this.getClass().getResource("/images/highAlert.gif");
			imageIcon = new ImageIcon(url);
			highAlertIconPanel = new JLabel(imageIcon);
			highAlertIconPanel.setBounds(1125, 60, 150, 150);
			add(highAlertIconPanel);
		}catch(Exception e) {
			Tool.logger.error("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
		
		lowAlertLabel = new JLabel("Une alarme de niveau 1 ou 2 est active!");
		lowAlertLabel.setBounds(822, 220, 236, 36);
		add(lowAlertLabel);
		
		highAlertLabel = new JLabel("Une alarme de niveau 3 est active!");
		highAlertLabel.setBounds(1105, 220, 207, 36);
		add(highAlertLabel);
		
		modifySensorButton = new JButton("Modifier le capteur");
		modifySensorButton.addActionListener(this);
		modifySensorButton.setBounds(955, 730, 157, 54);
		add(modifySensorButton);
		
		toggleDisabledAlertsButton = new JButton("Afficher les alertes désactivées");
		toggleDisabledAlertsButton.addActionListener(this);
		toggleDisabledAlertsButton.setBounds(365, 184, 227, 25);
		add(toggleDisabledAlertsButton);
		
		detailsAlertLabel = new JLabel("Alerte sélectionnée :");
		detailsAlertLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		detailsAlertLabel.setBounds(776, 327, 200, 16);
		add(detailsAlertLabel);
		
		detailsButton = new JButton(">> Détails");
		detailsButton.addActionListener(this);
		detailsButton.setBounds(626, 503, 109, 36);
		add(detailsButton);
		
		detailAlertText = new JTextPane();
		detailAlertText.setBounds(770, 357, 522, 343);
		add(detailAlertText);
		
		listAlertsLabel = new JLabel("Liste des dernières alertes :");
		listAlertsLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		listAlertsLabel.setBounds(62, 183, 265, 16);
		add(listAlertsLabel);
		
		displayDisabledAlerts = false;
	}

	private void refreshTableAlert() {
		
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(returnButton)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
			return;
		}
		if(event.getSource().equals(displayMapButton)) {
			return;
		}
		if(event.getSource().equals(disableAlertButton)) {
			return;
		}
		if(event.getSource().equals(refreshButton)) {
			return;
		}
		if(event.getSource().equals(modifySensorButton)) {
			return;
		}
		if(event.getSource().equals(toggleDisabledAlertsButton)) {
			if(displayDisabledAlerts) {
				displayDisabledAlerts = false;
				toggleDisabledAlertsButton.setText("Afficher les alertes désactivées");		
			}else {
				displayDisabledAlerts = true;
				toggleDisabledAlertsButton.setText("Masquer les alertes désactivées");
			}
			return;
		}
		if(event.getSource().equals(detailsButton)) {
			return;
		}
		
	}

	
}

