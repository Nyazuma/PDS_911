package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import shs.common.Tool;
import shs.gui.GuiController;

public class Monitoring extends JPanel implements ActionListener{

	private GuiController controller;

	protected JButton returnButton; 
	protected JScrollPane scrollPane;
	protected JTable alertsTable;
	protected JButton displayMapButton;
	protected JButton disableAlertButton;
	protected JButton refreshButton;
	protected JButton displayLevel;
	protected JLabel lowAlertIconPanel;
	protected JLabel highAlertIconPanel;
	protected JLabel lowAlertLabel;
	protected JLabel highAlertLabel;
	protected JButton toggleDisabledAlertsButton;
	protected JLabel detailsAlertLabel;
	protected JTextPane detailsAlertText;
	protected JLabel listAlertsLabel;

	private Boolean displayDisabledAlerts;
	private Boolean displayAlertsThreeOnly;
	private Boolean stopThread;
	private Thread refreshAlertsThread;
	private List<List<String>> rawAlertsData;
	private Object[][] displayedData;

	public Monitoring(final GuiController controller) {

		this.controller=controller;

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		returnButton = new JButton("Retour");
		returnButton.setBounds(12, 15, 90, 44);
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.addActionListener(this);
		add(returnButton);

		displayMapButton = new JButton("Visualiser sur le plan");
		displayMapButton.addActionListener(this);
		displayMapButton.setBounds(810, 730, 244, 54);
		add(displayMapButton);

		disableAlertButton = new JButton("Désactiver l'alerte");
		disableAlertButton.setBounds(1089, 730, 244, 54);
		disableAlertButton.addActionListener(this);
		add(disableAlertButton);

		refreshButton = new JButton("Rafraichir manuellement");
		refreshButton.setBounds(493, 128, 227, 25);
		refreshButton.addActionListener(this);
		add(refreshButton);

		displayLevel = new JButton("Afficher uniquement les niveaux 3");
		displayLevel.setBounds(493, 156, 227, 25);
		displayLevel.addActionListener(this);
		add(displayLevel);

		toggleDisabledAlertsButton = new JButton("Masquer les alertes désactivées");
		toggleDisabledAlertsButton.addActionListener(this);
		toggleDisabledAlertsButton.setBounds(493, 184, 227, 25);
		add(toggleDisabledAlertsButton);

		URL url = null;
		ImageIcon imageIcon = null;
		try {
			url = this.getClass().getResource("/images/lowAlert.gif");
			imageIcon = new ImageIcon(url);
			lowAlertIconPanel = new JLabel(imageIcon);
		}catch(Exception e) {
			lowAlertIconPanel = new JLabel();
			Tool.logger.error("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
		lowAlertIconPanel.setBounds(860, 60, 150, 150);

		try {
			url = this.getClass().getResource("/images/highAlert.gif");
			imageIcon = new ImageIcon(url);
			highAlertIconPanel = new JLabel(imageIcon);
		}catch(Exception e) {
			highAlertIconPanel = new JLabel();
			Tool.logger.error("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
		highAlertIconPanel.setBounds(1125, 60, 150, 150);

		lowAlertLabel = new JLabel("Une alarme de niveau 1 ou 2 est active!");
		lowAlertLabel.setBounds(822, 220, 236, 36);

		highAlertLabel = new JLabel("Une alarme de niveau 3 est active!");
		highAlertLabel.setBounds(1105, 220, 207, 36);

		detailsAlertLabel = new JLabel("Alerte sélectionnée :");
		detailsAlertLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		detailsAlertLabel.setBounds(810, 327, 200, 16);
		add(detailsAlertLabel);

		detailsAlertText = new JTextPane();
		detailsAlertText.setFont(new Font("Cambria Math", Font.CENTER_BASELINE, 16));
		detailsAlertText.setEditable(false);
		detailsAlertText.setBounds(810, 357, 522, 343);
		add(detailsAlertText);

		listAlertsLabel = new JLabel("Liste des dernières alertes :");
		listAlertsLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		listAlertsLabel.setBounds(40, 183, 265, 16);
		add(listAlertsLabel);

		displayDisabledAlerts = true;
		displayAlertsThreeOnly = false;
		refreshAlertsThread = new Thread() {
			public void run(){
				while(!stopThread) {
					try {
						rawAlertsData=controller.listNotifications();
						refreshTableAlert();
						Thread.sleep(20*1000);
					}catch(InterruptedException e) {}
				}
			}
		};
		stopThread = false;
		refreshAlertsThread.start();

	}

	private void refreshTableAlert() {
		String[] header = {"ID_Notification", "Niveau", "Date_Notification", "Message_Notification", 
				"Etat", "ID_Capteur", "Type_Capteur", "Mac_Capteur", "ID_Emplacement", "Nom_Emplacement", 
				"Niveau_Etage", "Nom_Residence"};
		List<List<String>> proceedData;
		boolean lowLevelActive = false;
		boolean highLevelActive = false;
		proceedData = new ArrayList<List<String>>();
		for(List<String> line : rawAlertsData) {
			if(!lowLevelActive && !line.get(1).equals("3") && line.get(4).equals("1"))
				lowLevelActive=true;
			if(!highLevelActive && line.get(1).equals("3") && line.get(4).equals("1"))
				highLevelActive=true;
			if(!displayDisabledAlerts && line.get(4).equals("0"))
				continue;
			if(displayAlertsThreeOnly && !line.get(1).equals("3"))
				continue;
			proceedData.add(line);
		}
		refreshGraphicsAlerts(lowLevelActive, highLevelActive);
		Integer backupIDSelectedRow = -1;
		if(alertsTable!= null && alertsTable.getSelectedRow()>-1)
			backupIDSelectedRow = Integer.valueOf((String) displayedData[alertsTable.getSelectedRow()][0]);
		displayedData = new Object[proceedData.size()][header.length];
		int lineCursor = 0;
		int columnCursor = 0;
		for(List<String> line : proceedData) {
			for(String column : line) {
				if(columnCursor==4) {
					if(column.equals("0"))
						displayedData[lineCursor][columnCursor] = "Finie";
					else
						displayedData[lineCursor][columnCursor] = "Active";
				}
				else {
					displayedData[lineCursor][columnCursor] = column;
				}
				columnCursor++;
			}
			lineCursor++;
			columnCursor=0;
		}
		alertsTable = new JTable(displayedData, header);
		if(backupIDSelectedRow>-1) {
			for(int rowSelected=0; rowSelected < displayedData.length; rowSelected++)
				if(displayedData[rowSelected][0].equals(backupIDSelectedRow.toString())) {
					alertsTable.setRowSelectionInterval(rowSelected, rowSelected);
					break;
				}
		}
		alertsTable.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		alertsTable.setDefaultRenderer(String.class, centerRenderer);
		for(int i=0; i<header.length;i++) {
			if(!(i==1 || i==2 || i==3 || i==4)) {
				alertsTable.getColumnModel().getColumn(i).setMinWidth(0);
				alertsTable.getColumnModel().getColumn(i).setMaxWidth(0);
				continue;
			}
			alertsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		alertsTable.getColumnModel().getColumn(1).setMaxWidth(100);
		alertsTable.getColumnModel().getColumn(4).setMaxWidth(100);

		alertsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (alertsTable.getSelectedRow() > -1) {
					constructDetails(alertsTable.getSelectedRow());
					if(displayedData[alertsTable.getSelectedRow()][4].equals("Active"))
						disableAlertButton.setText("Désactiver l'alerte");
					else
						disableAlertButton.setText("Réactiver l'alerte");
				}
			}
		});

		if(scrollPane!=null)
			remove(scrollPane);
		scrollPane = new JScrollPane(alertsTable);
		scrollPane.setBounds(40, 212, 680, 572);
		add(scrollPane);
		controller.getGui().revalidate();
		controller.getGui().repaint();

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(returnButton)) {
			stopThread=true;
			refreshAlertsThread.interrupt();
			controller.getGui().changeWindow(WindowList.MENU);
			return;
		}
		if(event.getSource().equals(displayMapButton)) {
			if(alertsTable.getSelectedRow() == -1) {
				detailsAlertText.setText("Vous n'avez pas sélectionné d'alerte à localiser!");
			}
			else if(displayedData[alertsTable.getSelectedRow()][6].equals("Bracelet RFID")) {
				detailsAlertText.setText("Un bracelet RFID n'a pas d'emplacement à visualiser");
			}
			else {
				stopThread=true;
				refreshAlertsThread.interrupt();
				controller.getGui().loadWindowMap((String) displayedData[alertsTable.getSelectedRow()][8]);
			}
			return;
		}
		if(event.getSource().equals(disableAlertButton)) {
			if(alertsTable.getSelectedRow()> -1) {
				if(displayedData[alertsTable.getSelectedRow()][4].equals("Active")) {
					controller.changeAlert(Integer.valueOf((String) displayedData[alertsTable.getSelectedRow()][0]), false);
					disableAlertButton.setText("Réactiver l'alerte");
					detailsAlertText.setText("Alerte désactivée");
				}
				else {
					controller.changeAlert(Integer.valueOf((String) displayedData[alertsTable.getSelectedRow()][0]), true);
					disableAlertButton.setText("Désactiver l'alerte");
					detailsAlertText.setText("Alerte réactivée");
				}
				refreshAlertsThread.interrupt();
			}
			else {
				detailsAlertText.setText("Vous n'avez pas sélectionné d'alerte à désactiver!");
			}
			return;
		}
		if(event.getSource().equals(refreshButton)) {
			refreshAlertsThread.interrupt();
			return;
		}
		if(event.getSource().equals(displayLevel)) {
			if(displayAlertsThreeOnly) {
				displayAlertsThreeOnly=false;
				displayLevel.setText("Afficher uniquement les niveaux 3");
			}
			else {
				displayAlertsThreeOnly=true;
				displayLevel.setText("Afficher tous les niveaux d'alertes");
			}
			refreshTableAlert();
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
			refreshTableAlert();
			return;
		}

	}

	private void constructDetails(Integer line) {
		String message = "Alerte de niveau " + displayedData[line][1] + "  ";
		if(displayedData[line][4].equals("Active"))
			message+=" /!\\ ACTIVE /!\\ \n\n";
		else
			message+="(terminée) \n\n";
		message+="Emise il y a " + timeDifference(displayedData[line][2]) + " par un " + displayedData[line][6]  + " \n";
		message+="Message : '" + displayedData[line][3] + "'\n\n";
		message+="Le capteur émetteur est localisé dans la résidence " + displayedData[line][11] + ", \n";
		message+="Etage " + displayedData[line][10] + ", Emplacement '" + displayedData[line][9] + "' ";
		message+="(ID Emplacement : " + displayedData[line][8] + ")\n";
		message+="Visualiser le capteur sur le plan pour plus de précision\n\n";
		message+="Informations techniques :\n";
		message+="Alerte n°" + displayedData[line][0] + ", émise le " + displayedData[line][2] + " (heure du serveur)\n";
		message+="Adresse mac du capteur: " + displayedData[line][7] + ", ID capteur n°" + displayedData[line][5];


		detailsAlertText.setText(message);
	}


	private String timeDifference(Object date) {

		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		Timestamp eventDate = java.sql.Timestamp.valueOf((String) date);

		long milliseconds = currentDate.getTime() - eventDate.getTime();
		
		//if the result is weird, the server time should probably be fixed :  date +%T -s "11:14:00"
		if(milliseconds<0)
			return "(wrong server time)";
		int seconds = (int) milliseconds / 1000;
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = (seconds % 3600) % 60;

		String answer = "";
		if(hours>0) {
			if(hours==1)
				answer+= "1 heure, ";
			else
				answer+= hours + " heures, ";
		}
		if(minutes>0) {
			if(minutes==1)
				answer+= "1 minute, ";
			else
				answer+= minutes + " minutes, ";
		}
		if(seconds<2)
			answer+= seconds + " seconde";
		else
			answer+= seconds + " secondes";
		
		return answer;
	}

	private void refreshGraphicsAlerts(Boolean lowLevelActive, Boolean highLevelActive) {
		if(lowLevelActive) {
			add(lowAlertLabel);
			add(lowAlertIconPanel);
		}
		else {
			remove(lowAlertLabel);
			remove(lowAlertIconPanel);
		}

		if(highLevelActive) {
			add(highAlertLabel);
			add(highAlertIconPanel);
		}
		else {
			remove(highAlertLabel);
			remove(highAlertIconPanel);
		}
	}
}

