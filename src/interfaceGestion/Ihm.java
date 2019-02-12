package interfaceGestion;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectionPool.DataConfig;
import connectionPool.JDBCConnectionPool;


public class Ihm implements ActionListener{

	private JFrame frmInterfaceDeGestion;
	/**
	 * ACTION PERFORMED ELEMENTS - INITIALIZE
	 */
	private JTextField identifiantTxt;
	private JPasswordField passwordField;
	private JTable tableObjet;
	private JButton ValiderButton; 

	private Controller control;

	/**
	 * ACTION PERFORMED ELEMENTS - GESTION OBJECT
	 */
	private JButton btnAjouter; 
	private JComboBox<?> listCapteur;
	private JLabel lblNewLabel; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataConfig.getInstanceConfig();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ihm window = new Ihm();
					window.frmInterfaceDeGestion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ihm() {
		initialize();
		control = new Controller(new JDBCConnectionPool());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInterfaceDeGestion = new JFrame();
		frmInterfaceDeGestion.setTitle("Interface de gestion des objets connect\u00E9s");
		frmInterfaceDeGestion.getContentPane().setBackground(new Color(95, 158, 160));
		frmInterfaceDeGestion.getContentPane().setFont(new Font("Cambria Math", Font.BOLD, 17));
		frmInterfaceDeGestion.setBounds(100, 100, 1400, 900);
		frmInterfaceDeGestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInterfaceDeGestion.getContentPane().setLayout(null);

		JLabel lblAuthentification = new JLabel("Authentification");
		lblAuthentification.setFont(new Font("Cambria Math", Font.BOLD, 23));
		lblAuthentification.setBounds(609, 267, 189, 43);
		frmInterfaceDeGestion.getContentPane().add(lblAuthentification);

		JLabel lblIdentifiant = new JLabel("Identifiant :");
		lblIdentifiant.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblIdentifiant.setBounds(514, 326, 106, 30);
		frmInterfaceDeGestion.getContentPane().add(lblIdentifiant);

		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblMotDePasse.setBounds(503, 369, 122, 30);
		frmInterfaceDeGestion.getContentPane().add(lblMotDePasse);

		identifiantTxt = new JTextField();
		identifiantTxt.setBounds(637, 327, 223, 26);
		frmInterfaceDeGestion.getContentPane().add(identifiantTxt);
		identifiantTxt.setColumns(10);

		ValiderButton = new JButton("Valider");
		ValiderButton.setFont(new Font("Cambria Math", Font.BOLD, 15));
		ValiderButton.addActionListener(this);

		ValiderButton.setBounds(609, 486, 189, 43);
		frmInterfaceDeGestion.getContentPane().add(ValiderButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(637, 371, 223, 26);
		frmInterfaceDeGestion.getContentPane().add(passwordField);

		JLabel label = new JLabel("--------------------------------------------------------------------------------------------------");
		label.setBounds(502, 433, 501, 16);
		frmInterfaceDeGestion.getContentPane().add(label);

		JLabel label_1 = new JLabel("--------------------------------------------------------------------------------------------------");
		label_1.setBounds(502, 249, 501, 16);
		frmInterfaceDeGestion.getContentPane().add(label_1);


	}

	private void failConnexion() {
		JLabel lblNewLabel = new JLabel("Identifiant ou mot de passe invalide.");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		lblNewLabel.setBounds(573, 457, 255, 16);
		frmInterfaceDeGestion.getContentPane().add(lblNewLabel);
		frmInterfaceDeGestion.repaint();
	}

	private void gestionObjet() {
		frmInterfaceDeGestion.getContentPane().removeAll();
		frmInterfaceDeGestion.revalidate();

		JLabel lblNombreDobjectConnect = new JLabel("Nombre d'objects connect\u00E9s : ");
		lblNombreDobjectConnect.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblNombreDobjectConnect.setBounds(48, 184, 246, 28);
		frmInterfaceDeGestion.getContentPane().add(lblNombreDobjectConnect);
		
		JLabel lblAjouterUnObjet = new JLabel("Ajouter un nouvel objet :");
		lblAjouterUnObjet.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblAjouterUnObjet.setBounds(843, 219, 236, 16);
		frmInterfaceDeGestion.getContentPane().add(lblAjouterUnObjet);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblNewLabel.setBounds(317, 184, 64, 28);
		frmInterfaceDeGestion.getContentPane().add(lblNewLabel);
		
		JLabel lblGestionDesNouveaux = new JLabel("Gestion des nouveaux objets");
		lblGestionDesNouveaux.setFont(new Font("Cambria Math", Font.BOLD, 20));
		lblGestionDesNouveaux.setBounds(525, 25, 291, 41);
		frmInterfaceDeGestion.getContentPane().add(lblGestionDesNouveaux);
		
		listCapteur = new JComboBox();
		listCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		listCapteur.setModel(new DefaultComboBoxModel(new String[] {"Capteur de temp\u00E9rature", "Capteur de fum\u00E9e", "Capteur ouverture ", "Capteur hygrom\u00E9trique", "Capteur de pr\u00E9sence", "Capteur appel ", "Bracelet"}));
		listCapteur.setBounds(969, 279, 309, 33);
		frmInterfaceDeGestion.getContentPane().add(listCapteur);
		
		JLabel lbltypeDuCapteur = new JLabel("*Type du capteur : ");
		lbltypeDuCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lbltypeDuCapteur.setBounds(800, 279, 169, 33);
		frmInterfaceDeGestion.getContentPane().add(lbltypeDuCapteur);
		
		tableObjet = new JTable();
		tableObjet.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Identifiant du capteur", "Type du capteur", "Etat"
			}
		));
		tableObjet.setBounds(12, 225, 727, 413);
		frmInterfaceDeGestion.getContentPane().add(tableObjet);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnAjouter.setBounds(970, 438, 162, 41);
		btnAjouter.addActionListener(this);
		frmInterfaceDeGestion.getContentPane().add(btnAjouter);
		frmInterfaceDeGestion.repaint();
		frmInterfaceDeGestion.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		System.out.println("Hello from the other side1 " + event.getSource());
		if(event.getSource().equals(ValiderButton)) {
			if(!(control.connection(identifiantTxt.getText(), String.valueOf(passwordField.getPassword())))) {
				failConnexion();
			}
			else
			{
				gestionObjet();
				lblNewLabel.setText(Integer.toString(control.nbObject())); 
			}
		}
		else {
			control.addObject(listCapteur.getSelectedItem().toString());
			lblNewLabel.setText(Integer.toString(control.nbObject())); 
			
		}

	}
}