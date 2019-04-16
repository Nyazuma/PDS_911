package shs.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shs.gui.GuiController;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class Statistics extends JPanel implements ActionListener{
	
	private JComboBox<String> typeCapteur;
	private JComboBox<String> residence;
	private JComboBox<String> zone;
	private JComboBox<String> etage;
	private JButton btnRetour; 
	private DefaultComboBoxModel<String> modelTypeCapteur;
	private DefaultComboBoxModel<String> modelEtatCapteur; 
	private DefaultComboBoxModel<String> modelResidence; 
	private DefaultComboBoxModel<String> modelEtage; 
	private DefaultComboBoxModel<String> modelEmplacement; 
	private JComboBox<String> etatCapteur; 
	private JLabel objectNumberLabel;
	
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
		//typeCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(1).toString());
		typeCapteur.setBounds(12, 130, 233, 32);
		this.add(typeCapteur);
		
		JLabel lblEtatCapteur = new JLabel("Etat capteur");
		lblEtatCapteur.setBounds(364, 101, 116, 16);
		add(lblEtatCapteur);
		
		modelEtatCapteur = new DefaultComboBoxModel<String>(new String[] {"Enable", "Disable"});
		etatCapteur = new JComboBox<String>();
		etatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etatCapteur.setModel(modelEtatCapteur);
		//etatCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(2).toString());
		etatCapteur.setBounds(351, 130, 116, 32);
		this.add(etatCapteur);
		
		
		JLabel lblResidence = new JLabel("Residence");
		lblResidence.setBounds(548, 101, 116, 16);
		add(lblResidence);
		
		// We get the residences list
		modelResidence = new DefaultComboBoxModel<String>(controller.readResidences());
		residence = new JComboBox<String>(); 
		residence.setFont(new Font("Cambria Math", Font.BOLD, 16));
		residence.setModel(modelResidence);
		//residence.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		residence.setBounds(530, 130, 134, 32);
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
		this.add(zone); 
	
		JLabel lblEtage = new JLabel("Etage");
		lblEtage.setBounds(996, 101, 116, 16);
		add(lblEtage);
		
		// We get the list of pieces
		modelEtage = new DefaultComboBoxModel<String>(controller.readEtages());
		etage = new JComboBox<String>(); 
		etage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etage.setModel(modelEtage);
		etage.setBounds(963, 130, 161, 32);
		this.add(etage);
		
		JLabel lblNombreDeLogements = new JLabel("Nombre de logements");
		lblNombreDeLogements.setBounds(88, 489, 134, 26);
		add(lblNombreDeLogements);
		
		JLabel lblResultat = new JLabel("Resultat :");
		lblResultat.setBounds(375, 268, 134, 26);
		add(lblResultat);
		
		JLabel lblNombresTotalDe = new JLabel("Nombre total de capteurs");
		lblNombresTotalDe.setBounds(88, 438, 192, 26);
		add(lblNombresTotalDe);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(758, 371, 96, 22);
		add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(576, 371, 96, 22);
		add(dateChooser_1);
		
		JLabel lblFrom = new JLabel("De");
		lblFrom.setBounds(530, 371, 56, 16);
		add(lblFrom);
		
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
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(918, 371, 97, 25);
		add(btnValider);
		
		JLabel lblAlertes = new JLabel("Alertes :");
		lblAlertes.setBounds(807, 499, 56, 16);
		add(lblAlertes);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.setBounds(686, 560, 97, 25);
		add(btnDetails);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(537, 612, 540, 226);
		add(scrollPane);
		
		JLabel lblSocksRestants = new JLabel("Socks restants :");
		lblSocksRestants.setBounds(88, 547, 134, 16);
		add(lblSocksRestants);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}
		
	}
}
