package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shs.gui.GuiController;

public class ObjectModification extends JPanel implements ActionListener{

	private GuiController controller;

	private JLabel labTitre; 
	private JComboBox<String> typeCapteur; 
	private JLabel lblTypeCapteur; 
	private JComboBox<String> etatCapteur; 
	private JLabel lblEtatCapteur;
	private JCheckBox confirmation; 
	private JButton valider; 
	private JButton returnButton; 
	private JLabel message; 
	private JComboBox<String> zoneCapteur; 
	private JLabel lblZone; 
	private JComboBox<String> pieceCapteur; 
	private JLabel lblPiece; 
	private JComboBox<String> residenceCapteur; 
	private JLabel lblResidence; 

	//Initialization of the JComboBox with a model
	private DefaultComboBoxModel<String> modelTypeCapteur;
	private DefaultComboBoxModel<String> modelEtatCapteur; 
	private DefaultComboBoxModel<String> modelResidence; 
	private DefaultComboBoxModel<String> modelPiece; 
	private DefaultComboBoxModel<String> modelZone; 



	/**
	 * Initialize the contents of the frame.
	 */
	public ObjectModification(GuiController controller) {

		this.controller = controller; 

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		labTitre = new JLabel("Vous pouvez modifier l'objet que vous avez s\u00E9lectionn\u00E9");
		labTitre.setFont(new Font("Cambria Math", Font.BOLD, 16));
		labTitre.setBounds(511, 103, 447, 28);
		this.add(labTitre);

		modelTypeCapteur = new DefaultComboBoxModel<String>(new String[] {"Capteur de température", "Capteur de fumee", "Capteur ouverture ", "Capteur hygrométrique", "Capteur de présence", "Capteur appel ", "Bracelet"});	
		typeCapteur = new JComboBox<String>();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setModel(modelTypeCapteur);
		typeCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(1).toString());
		typeCapteur.setBounds(571, 243, 265, 42);
		this.add(typeCapteur);

		lblTypeCapteur = new JLabel("Type de capteur :");
		lblTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTypeCapteur.setBounds(381, 250, 152, 28);
		this.add(lblTypeCapteur);

		modelEtatCapteur = new DefaultComboBoxModel<String>(new String[] {"Enable", "Disable"});
		etatCapteur = new JComboBox<String>();
		etatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etatCapteur.setModel(modelEtatCapteur);
		etatCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(2).toString());
		etatCapteur.setBounds(571, 322, 265, 42);
		this.add(etatCapteur);


		lblEtatCapteur = new JLabel("Etat du capteur : ");
		lblEtatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblEtatCapteur.setBounds(381, 329, 152, 28);
		this.add(lblEtatCapteur);

		// We get the residences list
		List<List<String>> listResidences = controller.readResidences();
		String[] tabResidences = new String[listResidences.size()];
		int i =0;
		for(List<String> line : listResidences) {
			tabResidences[i]=line.get(1);
			i++;
		}
		modelResidence = new DefaultComboBoxModel<String>(tabResidences);
		residenceCapteur = new JComboBox<String>(); 
		residenceCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		residenceCapteur.setModel(modelResidence);
		residenceCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		residenceCapteur.setBounds(571, 396, 265, 42);
		this.add(residenceCapteur);

		lblResidence = new JLabel("Résidence :"); 
		lblResidence.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblResidence.setBounds(381, 403, 152, 28);
		this.add(lblResidence); 

		// We get the list of zones
		List<List<String>> listZones = controller.readZones();
		String[] tabZones = new String[listZones.size()];
		i =0;
		for(List<String> line : listZones) {
			tabZones[i]=line.get(0);
			i++;
		}
		modelZone = new DefaultComboBoxModel<String>(tabZones);
		zoneCapteur = new JComboBox<String>(); 
		zoneCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		zoneCapteur.setModel(modelZone);
		zoneCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(4).toString());
		zoneCapteur.setBounds(571, 477, 265, 42);
		this.add(zoneCapteur); 

		// We get the list of pieces
		List<List<String>> listPieces = controller.readPieces();
		String[] tabPieces = new String[listPieces.size()];
		i =0;
		for(List<String> line : listPieces) {
			tabPieces[i]=line.get(0);
			i++;
		}
		modelPiece = new DefaultComboBoxModel<String>(tabPieces);
		pieceCapteur = new JComboBox<String>(); 
		pieceCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		pieceCapteur.setModel(modelPiece);
		pieceCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(5).toString());
		pieceCapteur.setBounds(571, 556, 265, 42);
		this.add(pieceCapteur); 

		lblPiece = new JLabel("Pi\u00E8ce :");
		lblPiece.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblPiece.setBounds(381, 563, 152, 28);
		this.add(lblPiece); 

		lblZone = new JLabel("Zone :");
		lblZone.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblZone.setBounds(381, 484, 152, 28);
		this.add(lblZone); 


		confirmation = new JCheckBox("Je confirme vouloir modifier cet objet.");
		confirmation.setBackground(new Color(95, 158, 160));
		confirmation.setFont(new Font("Cambria Math", Font.BOLD, 16));
		confirmation.setBounds(512, 692, 350, 25);
		confirmation.addActionListener(this);
		this.add(confirmation);

		valider = new JButton("Valider");
		valider.setFont(new Font("Cambria Math", Font.BOLD, 16));
		valider.setBounds(555, 747, 165, 42);
		valider.setEnabled(false);
		valider.addActionListener(this);
		this.add(valider);

		message = new JLabel();
		message.setFont(new Font("Cambria Math", Font.BOLD, 16));
		message.setBounds(389, 802, 559, 16);
		message.setEnabled(false);
		this.add(message);

		JLabel label = new JLabel("------------------------------------------------------------------------------------------------------------------------------------------");
		label.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label.setBounds(315, 179, 740, 16);
		this.add(label);

		JLabel label_1 = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");
		label_1.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label_1.setBounds(315, 632, 740, 16);
		this.add(label_1);

		returnButton = new JButton("Retour");
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.setBounds(56, 40, 98, 48);
		returnButton.addActionListener(this);
		this.add(returnButton);

	}

	private List<String> gestionUpdate() {
		List<String> listValues = new ArrayList<String>(); 
		listValues.add(ObjectGestion.getRowUpdate().get(0).toString());
		listValues.add(typeCapteur.getSelectedItem().toString()); 
		if(etatCapteur.getSelectedItem().toString().contentEquals("Enable"))
			listValues.add("1"); 
		else 
			listValues.add("0"); 
		listValues.add(residenceCapteur.getSelectedItem().toString());
		listValues.add(zoneCapteur.getSelectedItem().toString()); 
		listValues.add(pieceCapteur.getSelectedItem().toString()); 
		return listValues; 

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(returnButton)) {
			controller.getGui().changeWindow(WindowList.OBJECTGESTION);

		}
		if(event.getSource().equals(confirmation)) {
			if(confirmation.isSelected()) 
				valider.setEnabled(true);

			else 
				valider.setEnabled(false);
			valider.repaint();
		}

		if(event.getSource().equals(valider) && confirmation.isSelected()) {
			boolean test = controller.update(gestionUpdate()); 
			if(test) {
				message.setText("L'object a été modifié avec succès !"); 
				message.setForeground(Color.WHITE);
				message.setEnabled(true);
			}
			else {
				message.setText("Une erreur est survenue. L'object n'a pas pu être modifié.");
				message.setForeground(Color.RED);
				message.setEnabled(true);
			}
		}


	}

}
