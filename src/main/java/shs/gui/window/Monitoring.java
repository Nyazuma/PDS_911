package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
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
	protected JLabel lowAlertIconPanel;
	protected JLabel highAlertIconPanel;
	protected JLabel lowAlertLabel;
	protected JLabel highAlertLabel;
	protected JButton modifySensorButton;
	protected JButton toggleDisabledAlertsButton;
	protected JLabel detailsAlertLabel;
	protected JTextPane detailsAlertText;
	protected JLabel listAlertsLabel;

	private Boolean displayDisabledAlerts;
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
		displayMapButton.setBounds(810, 730, 166, 54);
		add(displayMapButton);

		disableAlertButton = new JButton("Désactiver l'alerte");
		disableAlertButton.setBounds(1170, 730, 163, 54);
		disableAlertButton.addActionListener(this);
		add(disableAlertButton);

		refreshButton = new JButton("Rafraichir manuellement");
		refreshButton.setBounds(493, 156, 227, 25);
		refreshButton.addActionListener(this);
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
		modifySensorButton.setBounds(995, 730, 157, 54);
		add(modifySensorButton);

		toggleDisabledAlertsButton = new JButton("Afficher les alertes désactivées");
		toggleDisabledAlertsButton.addActionListener(this);
		toggleDisabledAlertsButton.setBounds(493, 184, 227, 25);
		add(toggleDisabledAlertsButton);

		detailsAlertLabel = new JLabel("Alerte sélectionnée :");
		detailsAlertLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		detailsAlertLabel.setBounds(810, 327, 200, 16);
		add(detailsAlertLabel);

		detailsAlertText = new JTextPane();
		detailsAlertText.setEditable(false);
		detailsAlertText.setBounds(810, 357, 522, 343);
		add(detailsAlertText);

		listAlertsLabel = new JLabel("Liste des dernières alertes :");
		listAlertsLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		listAlertsLabel.setBounds(40, 183, 265, 16);
		add(listAlertsLabel);

		displayDisabledAlerts = false;
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
		if(displayDisabledAlerts)
			proceedData = rawAlertsData;
		else {
			proceedData = new ArrayList<List<String>>();
			for(List<String> line : rawAlertsData) {
				if(line.get(4).equals("1"))
					proceedData.add(line);
			}
		}
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
			return;
		}
		if(event.getSource().equals(disableAlertButton)) {
			if(alertsTable.getSelectedRow()> -1) {
				System.out.println(displayedData[alertsTable.getSelectedRow()][0]);
				controller.changeAlert(Integer.valueOf((String) displayedData[alertsTable.getSelectedRow()][0]), false);
				refreshAlertsThread.interrupt();
			}
			else {
				detailsAlertText.setText("Vous n'avez pas sélectionné d'alerte à désactiver!");
			}
			return;
		}
		if(event.getSource().equals(modifySensorButton)) {
			return;
		}
		if(event.getSource().equals(refreshButton)) {
			refreshAlertsThread.interrupt();
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
		String message = "Alerte ID : " + displayedData[line][0];
		detailsAlertText.setText(message);
	}

}

