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
		
		JLabel lblTypeCapteur = new JLabel("Type capteur");
		lblTypeCapteur.setBounds(34, 101, 116, 16);
		add(lblTypeCapteur);
		
		// We get the list of captor types
		modelTypeCapteur = new DefaultComboBoxModel<String>(controller.readReferentiels());	
		typeCapteur = new JComboBox<String>();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setModel(modelTypeCapteur);
		//typeCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(1).toString());
		typeCapteur.setBounds(12, 130, 134, 32);
		this.add(typeCapteur);
		
		JLabel lblEtatCapteur = new JLabel("Etat capteur");
		lblEtatCapteur.setBounds(222, 101, 116, 16);
		add(lblEtatCapteur);
		
		modelEtatCapteur = new DefaultComboBoxModel<String>(new String[] {"Enable", "Disable"});
		etatCapteur = new JComboBox<String>();
		etatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etatCapteur.setModel(modelEtatCapteur);
		//etatCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(2).toString());
		etatCapteur.setBounds(222, 130, 116, 32);
		this.add(etatCapteur);
		
		
		JLabel lblResidence = new JLabel("Residence");
		lblResidence.setBounds(413, 101, 116, 16);
		add(lblResidence);
		
		// We get the residences list
		modelResidence = new DefaultComboBoxModel<String>(controller.readResidences());
		residence = new JComboBox<String>(); 
		residence.setFont(new Font("Cambria Math", Font.BOLD, 16));
		residence.setModel(modelResidence);
		//residence.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		residence.setBounds(395, 130, 134, 32);
		this.add(residence);
		
		JLabel lblNombresDeCapteurs = new JLabel("Nombres de capteurs");
		lblNombresDeCapteurs.setBounds(365, 25, 122, 16);
		add(lblNombresDeCapteurs);
		
		
		JLabel lblEmplacement = new JLabel("Emplacement");
		lblEmplacement.setBounds(598, 101, 116, 16);
		add(lblEmplacement);
		
		// We get the list of zones
		modelEmplacement = new DefaultComboBoxModel<String>(controller.readEmplacements());
		zone = new JComboBox<String>(); 
		zone.setFont(new Font("Cambria Math", Font.BOLD, 16));
		zone.setModel(modelEmplacement);
		zone.setBounds(565, 130, 134, 32);
		this.add(zone); 
	
		JLabel lblEtage = new JLabel("Etage");
		lblEtage.setBounds(793, 101, 116, 16);
		add(lblEtage);
		
		// We get the list of pieces
		modelEtage = new DefaultComboBoxModel<String>(controller.readEtages());
		etage = new JComboBox<String>(); 
		etage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etage.setModel(modelEtage);
		etage.setBounds(758, 130, 161, 32);
		this.add(etage); 
		
		
		JLabel lblMoyenne = new JLabel("Moyenne");
		lblMoyenne.setBounds(97, 361, 134, 26);
		add(lblMoyenne);
		
		JLabel lblNombreDeLogements = new JLabel("Nombre de logements");
		lblNombreDeLogements.setBounds(97, 412, 134, 26);
		add(lblNombreDeLogements);
		
		JLabel lblNombreDeCapteurs = new JLabel("Nombre de capteurs");
		lblNombreDeCapteurs.setBounds(97, 463, 134, 26);
		add(lblNombreDeCapteurs);
		
		JLabel lblResultat = new JLabel("Resultat :");
		lblResultat.setBounds(222, 249, 134, 26);
		add(lblResultat);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}
		
	}
}
