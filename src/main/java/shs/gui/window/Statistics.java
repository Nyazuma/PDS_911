package shs.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shs.gui.GuiController;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;

public class Statistics extends JPanel implements ActionListener{
	
	private JComboBox<String> typeCapteur;
	private JComboBox<String> residence;
	private JComboBox<String> zone;
	private JComboBox<String> floor;
	private JButton btnRetour; 
	private JButton btnValider;
	private JButton btnValider2;
	private JDateChooser dateFrom;
	private JDateChooser dateTo;
	private DefaultComboBoxModel<String> modelTypeCapteur;
	private DefaultComboBoxModel<String> modelStateCaptor; 
	private DefaultComboBoxModel<String> modelResidence; 
	private DefaultComboBoxModel<String> modelFloor; 
	private DefaultComboBoxModel<String> modelEmplacement; 
	private JComboBox<String> stateCaptor; 
	private JLabel objectNumberLabel;
	private JLabel objectAdded;
	private JLabel objectDeleted;
	private JLabel objectModified;
	private JLabel objectAlert;
	private JLabel fetchResult;

	private GuiController controller;
	
	public Statistics(GuiController controller) {
		
		this.controller = controller; 
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.controller.getGui().setSize(screensize);
		this.controller.getGui().setLocationRelativeTo(null);
		setBackground(new Color(95, 158, 160));
		this.setLayout(null);
		
		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(12, 15, 90, 44);
		btnRetour.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnRetour.addActionListener(this);
		this.add(btnRetour);
		
		objectNumberLabel = new JLabel("");
		objectNumberLabel.setForeground(Color.RED);
		objectNumberLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectNumberLabel.setBounds(267, 437, 55, 28);
		objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
		this.add(objectNumberLabel);
		
		JLabel lblTypeCapteur = new JLabel("Type capteur");
		lblTypeCapteur.setBounds(88, 101, 116, 16);
		add(lblTypeCapteur);
		
		// We get the list of captor types
		modelTypeCapteur = new DefaultComboBoxModel<String>(controller.readReferentiels());	
		typeCapteur = new JComboBox<String>();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setModel(modelTypeCapteur);
		typeCapteur.setBounds(12, 130, 233, 32);
		this.add(typeCapteur);
		
		JLabel lblEtatCapteur = new JLabel("Etat capteur");
		lblEtatCapteur.setBounds(364, 101, 116, 16);
		add(lblEtatCapteur);
		
		modelStateCaptor = new DefaultComboBoxModel<String>(new String[] {"0", "1"});
		stateCaptor = new JComboBox<String>();
		stateCaptor.setFont(new Font("Cambria Math", Font.BOLD, 16));
		stateCaptor.setModel(modelStateCaptor);
		stateCaptor.setBounds(351, 130, 116, 32);
		stateCaptor.insertItemAt("", 0);
		this.add(stateCaptor);
		
		
		JLabel lblResidence = new JLabel("Residence");
		lblResidence.setBounds(548, 101, 116, 16);
		add(lblResidence);
		
		// We get the residences list
		modelResidence = new DefaultComboBoxModel<String>(controller.readResidences());
		residence = new JComboBox<String>(); 
		residence.setFont(new Font("Cambria Math", Font.BOLD, 16));
		residence.setModel(modelResidence);
		residence.setBounds(530, 130, 134, 32);
		residence.insertItemAt("", 0);
		this.add(residence);
		
		JLabel lblNombresDeCapteurs = new JLabel("Nombres de capteurs");
		lblNombresDeCapteurs.setBounds(583, 29, 177, 16);
		add(lblNombresDeCapteurs);
		
		
		JLabel lblEmplacement = new JLabel("Emplacement");
		lblEmplacement.setBounds(753, 101, 116, 16);
		add(lblEmplacement);
		
		// We get the list of zones
		modelEmplacement = new DefaultComboBoxModel<String>(controller.readEmplacements());
		zone = new JComboBox<String>(); 
		zone.setFont(new Font("Cambria Math", Font.BOLD, 16));
		zone.setModel(modelEmplacement);
		zone.setBounds(735, 130, 134, 32);
		zone.insertItemAt("", 0);
		this.add(zone); 
	
		JLabel lblEtage = new JLabel("Etage");
		lblEtage.setBounds(996, 101, 116, 16);
		add(lblEtage);
		
		// We get the list of pieces
		modelFloor = new DefaultComboBoxModel<String>(controller.readEtages());
		floor = new JComboBox<String>(); 
		floor.setFont(new Font("Cambria Math", Font.BOLD, 16));
		floor.setModel(modelFloor);
		floor.setBounds(963, 130, 161, 32);
		floor.insertItemAt("", 0);
		this.add(floor);
		
		JLabel lblNombreDeLogements = new JLabel("Nombre de logements");
		lblNombreDeLogements.setBounds(88, 489, 134, 26);
		add(lblNombreDeLogements);
		
		JLabel lblResultat = new JLabel("Resultat :");
		lblResultat.setBounds(375, 268, 134, 26);
		add(lblResultat);
		
		JLabel lblNombresTotalDe = new JLabel("Nombre total de capteurs");
		lblNombresTotalDe.setBounds(88, 438, 192, 26);
		add(lblNombresTotalDe);
		
		dateFrom = new JDateChooser();
		dateFrom.setDateFormatString("yyyy-MM-dd");
		dateFrom.setBounds(576, 371, 96, 22);
		this.add(dateFrom);
		
		JLabel lblFrom = new JLabel("De");
		lblFrom.setBounds(530, 371, 56, 16);
		add(lblFrom);
		
		dateTo = new JDateChooser();
		dateTo.setDateFormatString("yyyy-MM-dd");
		dateTo.setBounds(758, 371, 96, 22);
		this.add(dateTo);
		
		JLabel lblTo = new JLabel("A");
		lblTo.setBounds(704, 371, 56, 16);
		add(lblTo);
		
		JLabel lblCaptorDeleted = new JLabel("Capteurs ajoute :");
		lblCaptorDeleted.setBounds(530, 443, 123, 16);
		add(lblCaptorDeleted);
		
		JLabel lblCapteursSupprimes = new JLabel("Capteurs supprimes :");
		lblCapteursSupprimes.setBounds(807, 443, 123, 16);
		add(lblCapteursSupprimes);
		
		JLabel lblCapteursModifies = new JLabel("Capteurs modifies :");
		lblCapteursModifies.setBounds(530, 499, 123, 16);
		add(lblCapteursModifies);
		
		btnValider = new JButton("Valider");
		btnValider.setBounds(918, 371, 97, 25);
		btnValider.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnValider.addActionListener(this);
		this.add(btnValider);
		
		JLabel lblAlertes = new JLabel("Alertes :");
		lblAlertes.setBounds(807, 499, 56, 16);
		add(lblAlertes);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.setBounds(686, 560, 97, 25);
		add(btnDetails);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1241, 89, 540, 226);
		add(scrollPane);
		
		JLabel lblSocksRestants = new JLabel("Socks restants :");
		lblSocksRestants.setBounds(88, 547, 134, 16);
		add(lblSocksRestants);
		
		objectAdded = new JLabel("0");
		objectAdded.setForeground(Color.RED);
		objectAdded.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectAdded.setBounds(646, 437, 55, 28);		
		this.add(objectAdded);
		
		objectDeleted = new JLabel("0");
		objectDeleted.setForeground(Color.RED);
		objectDeleted.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectDeleted.setBounds(941, 437, 55, 28); 
		this.add(objectDeleted);
		
		objectModified = new JLabel("0");
		objectModified.setForeground(Color.RED);
		objectModified.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectModified.setBounds(646, 494, 55, 28);
		this.add(objectModified);
		
		objectAlert = new JLabel("0");
		objectAlert.setForeground(Color.RED);
		objectAlert.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectAlert.setBounds(862, 494, 55, 28);
		this.add(objectAlert);
		
		btnValider2 = new JButton("Valider");
		btnValider2.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnValider2.setBounds(535, 222, 97, 25);
		btnValider2.addActionListener(this);
		this.add(btnValider2);
		
		fetchResult = new JLabel("0");
		fetchResult.setForeground(Color.RED);
		fetchResult.setFont(new Font("Cambria Math", Font.BOLD, 16));
		fetchResult.setBounds(442, 268, 55, 28);
		this.add(fetchResult);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}
		
		if (e.getSource().equals(btnValider)) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			objectAdded.setText(Integer.toString(controller.nbObjectAdded(dateFormat.format(dateFrom.getDate()), dateFormat.format(dateTo.getDate()))));
			objectDeleted.setText(Integer.toString(controller.nbObjectDeleted(dateFormat.format(dateFrom.getDate()), dateFormat.format(dateTo.getDate()))));
			objectModified.setText(Integer.toString(controller.nbObjectUpdated(dateFormat.format(dateFrom.getDate()), dateFormat.format(dateTo.getDate()))));
			objectAlert.setText(Integer.toString(controller.nbObjectAlert(dateFormat.format(dateFrom.getDate()), dateFormat.format(dateTo.getDate()))));
		}
		
		if (e.getSource().equals(btnValider2)) {
			fetchResult.setText(Integer.toString(controller.nbObjectFetch(typeCapteur.getSelectedItem().toString(), stateCaptor.getSelectedItem().toString(), 
					zone.getSelectedItem().toString(), floor.getSelectedItem().toString(), residence.getSelectedItem().toString())));
		}
		
	}
}
