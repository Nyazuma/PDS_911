package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;

import shs.gui.GuiController;

public class Monitoring extends JPanel implements ActionListener{

	private GuiController controller;
	
	protected JButton returnButton; 
	protected JTable table;
	protected JButton displayMapButton;
	protected JButton disableAlertButton;
	protected JButton refreshButton;
	protected JPanel lowAlertIconPanel;
	protected JPanel highAlertIconPanel;
	protected JLabel lowAlertLabel;
	protected JLabel highAlertLabel;
	protected JButton modifySensorButton;
	protected JButton toggleDisabledAlertsButton;
	protected JLabel detailsAlertLabel;
	protected JButton detailsButton;
	protected JTextPane detailAlertText;
	protected JLabel listAlertsLabel;
	
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
		refreshButton.setBounds(374, 156, 217, 25);
		add(refreshButton);
		
		lowAlertIconPanel = new JPanel();
		lowAlertIconPanel.setLayout(null);
		lowAlertIconPanel.setBounds(840, 74, 196, 97);
		add(lowAlertIconPanel);
		
		highAlertIconPanel = new JPanel();
		highAlertIconPanel.setBounds(1105, 74, 196, 97);
		add(highAlertIconPanel);
		highAlertIconPanel.setLayout(null);
		
		lowAlertLabel = new JLabel("Une alarme de niveau 1 ou 2 est active!");
		lowAlertLabel.setBounds(822, 173, 236, 36);
		add(lowAlertLabel);
		
		highAlertLabel = new JLabel("Une alarme de niveau 3 est active!");
		highAlertLabel.setBounds(1105, 173, 207, 36);
		add(highAlertLabel);
		
		modifySensorButton = new JButton("Modifier le capteur");
		modifySensorButton.addActionListener(this);
		modifySensorButton.setBounds(955, 730, 157, 54);
		add(modifySensorButton);
		
		toggleDisabledAlertsButton = new JButton("Afficher les alertes désactivées");
		toggleDisabledAlertsButton.addActionListener(this);
		toggleDisabledAlertsButton.setBounds(374, 184, 217, 25);
		add(toggleDisabledAlertsButton);
		
		detailsAlertLabel = new JLabel("Alerte sélectionnée :");
		detailsAlertLabel.setBounds(776, 327, 133, 16);
		add(detailsAlertLabel);
		
		detailsButton = new JButton(">> Détails");
		detailsButton.addActionListener(this);
		detailsButton.setBounds(626, 503, 109, 36);
		add(detailsButton);
		
		detailAlertText = new JTextPane();
		detailAlertText.setBounds(770, 357, 522, 343);
		add(detailAlertText);
		
		listAlertsLabel = new JLabel("Liste des derni\u00E8res alertes :");
		listAlertsLabel.setBounds(62, 183, 175, 16);
		add(listAlertsLabel);
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
			return;
		}
		if(event.getSource().equals(detailsButton)) {
			return;
		}
		
	}

	
}

