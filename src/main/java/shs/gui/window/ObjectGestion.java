package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shs.gui.GuiController;

public class ObjectGestion extends JPanel implements ActionListener {

	private GuiController controller;

	// TODO change to english variable
	protected JTable tableObjet;
	protected JButton btnAjouter; 
	protected JComboBox<?> listCapteur;
	protected JLabel lblNewLabel; 
	protected JLabel lblGestionDesNouveaux;
	protected JLabel lblNombreDobjectConnect;
	protected JLabel lblAjouterUnObjet;
	protected JLabel lbltypeDuCapteur;

	public ObjectGestion(GuiController controller) {
		
		this.controller = controller;
		
		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		lblNombreDobjectConnect = new JLabel("Nombre d'objects connect\u00E9s : ");
		lblNombreDobjectConnect.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblNombreDobjectConnect.setBounds(48, 184, 246, 28);
		this.add(lblNombreDobjectConnect);

		lblAjouterUnObjet = new JLabel("Ajouter un nouvel objet :");
		lblAjouterUnObjet.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblAjouterUnObjet.setBounds(843, 219, 236, 16);
		this.add(lblAjouterUnObjet);

		lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblNewLabel.setBounds(317, 184, 64, 28);
		lblNewLabel.setText(Integer.toString(controller.nbObject())); 
		this.add(lblNewLabel);

		lblGestionDesNouveaux = new JLabel("Gestion des nouveaux objets");
		lblGestionDesNouveaux.setFont(new Font("Cambria Math", Font.BOLD, 20));
		lblGestionDesNouveaux.setBounds(525, 25, 291, 41);
		this.add(lblGestionDesNouveaux);

		listCapteur = new JComboBox();
		listCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		listCapteur.setModel(new DefaultComboBoxModel(new String[] {"Capteur de temp\u00E9rature", "Capteur de fum\u00E9e", "Capteur ouverture ", "Capteur hygrom\u00E9trique", "Capteur de pr\u00E9sence", "Capteur appel ", "Bracelet"}));
		listCapteur.setBounds(969, 279, 309, 33);
		this.add(listCapteur);

		lbltypeDuCapteur = new JLabel("*Type du capteur : ");
		lbltypeDuCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lbltypeDuCapteur.setBounds(800, 279, 169, 33);
		this.add(lbltypeDuCapteur);

		tableObjet = new JTable();
		tableObjet.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Identifiant du capteur", "Type du capteur", "Etat"
				}
				));
		tableObjet.setBounds(12, 225, 727, 413);
		this.add(tableObjet);

		btnAjouter = new JButton("Ajouter");
		btnAjouter.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnAjouter.setBounds(970, 438, 162, 41);
		btnAjouter.addActionListener(this);
		this.add(btnAjouter);
	}

	public void actionPerformed(ActionEvent event) {

		if(event.getSource().equals(btnAjouter)){
			controller.addObject(listCapteur.getSelectedItem().toString());
			lblNewLabel.setText(Integer.toString(controller.nbObject())); 
		}

	}
}
