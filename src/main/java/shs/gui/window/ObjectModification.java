package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JComboBox<String> emplacement; 
	private JLabel lblEmplacement; 
	private JComboBox<String> etatCapteur; 
	private JLabel lblEtatCapteur;
	private JCheckBox confirmation; 
	private JButton valider; 
	private JButton returnButton; 
	
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
		labTitre.setBounds(511, 103, 347, 28);
		this.add(labTitre);
		
		typeCapteur = new JComboBox();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setBounds(571, 396, 265, 42);
		this.add(typeCapteur);
		
		lblTypeCapteur = new JLabel("Type de capteur :");
		lblTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTypeCapteur.setBounds(381, 250, 152, 28);
		this.add(lblTypeCapteur);
		
		emplacement = new JComboBox();
		emplacement.setFont(new Font("Cambria Math", Font.BOLD, 16));
		emplacement.setBounds(571, 243, 265, 42);
		this.add(emplacement);
		
		lblEmplacement = new JLabel("Emplacement :");
		lblEmplacement.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblEmplacement.setBounds(381, 403, 152, 28);
		this.add(lblEmplacement);
		
		etatCapteur = new JComboBox();
		etatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		etatCapteur.setBounds(571, 322, 265, 42);
		this.add(etatCapteur);
		
		lblEtatCapteur = new JLabel("Etat du capteur : ");
		lblEtatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblEtatCapteur.setBounds(381, 329, 152, 28);
		this.add(lblEtatCapteur);
		
		confirmation = new JCheckBox("Je confirme vouloir modifier cet objet.");
		confirmation.setBounds(509, 579, 265, 25);
		this.add(confirmation);
		
		valider = new JButton("Valider");
		valider.setFont(new Font("Cambria Math", Font.BOLD, 16));
		valider.setBounds(558, 626, 165, 42);
		this.add(valider);
		
		JLabel label = new JLabel("-----------------------------------------------------------------------------------------------------------------------------------------------");
		label.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label.setBounds(315, 157, 740, 16);
		this.add(label);
		
		JLabel label_1 = new JLabel("-----------------------------------------------------------------------------------------------------------------------------------------------");
		label_1.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label_1.setBounds(315, 500, 740, 16);
		this.add(label_1);
		
		returnButton = new JButton("Retour");
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.setBounds(56, 40, 98, 48);
		returnButton.addActionListener(this);
		this.add(returnButton);
		
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(returnButton)) {
				controller.getGui().changeWindow(WindowList.OBJECTGESTION);
		}
		
	}
	
}
