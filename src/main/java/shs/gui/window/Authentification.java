package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shs.gui.GuiController;

public class Authentification extends JPanel implements ActionListener {

	private GuiController controller;
	
	//TODO change to english variable
	
	protected JLabel lblAuthentification;
	protected JLabel lblIdentifiant;
	protected JLabel lblMotDePasse;
	protected JTextField identifiantTxt;
	protected JPasswordField passwordField;
	protected JButton ValiderButton; 
	protected JLabel label1;
	protected JLabel label2;
	
	protected JLabel ConnectionFailed;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public Authentification(GuiController controller) {
		
		this.controller = controller;
		
		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);
		
		lblAuthentification = new JLabel("Authentification");
		lblAuthentification.setFont(new Font("Cambria Math", Font.BOLD, 23));
		lblAuthentification.setBounds(609, 267, 189, 43);
		this.add(lblAuthentification);

		lblIdentifiant = new JLabel("Identifiant :");
		lblIdentifiant.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblIdentifiant.setBounds(514, 326, 106, 30);
		this.add(lblIdentifiant);

		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblMotDePasse.setBounds(503, 369, 122, 30);
		this.add(lblMotDePasse);

		identifiantTxt = new JTextField();
		identifiantTxt.setBounds(637, 327, 223, 26);
		identifiantTxt.setColumns(10);
		this.add(identifiantTxt);

		ValiderButton = new JButton("Valider");
		ValiderButton.setFont(new Font("Cambria Math", Font.BOLD, 15));
		ValiderButton.addActionListener(this);

		ValiderButton.setBounds(609, 486, 189, 43);
		this.add(ValiderButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(637, 371, 223, 26);
		this.add(passwordField);

		label1 = new JLabel("--------------------------------------------------------------------------------------------------");
		label1.setBounds(502, 433, 501, 16);
		this.add(label1);

		label2 = new JLabel("--------------------------------------------------------------------------------------------------");
		label2.setBounds(502, 249, 501, 16);
		this.add(label2);
		
		ConnectionFailed = new JLabel("Identifiant ou mot de passe invalide.");
		ConnectionFailed.setForeground(new Color(128, 0, 0));
		ConnectionFailed.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		ConnectionFailed.setBounds(573, 457, 255, 16);
		// ConnectionFailed label is only visible after a password error
		
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(ValiderButton)) {
			Boolean connectionEstablished = controller.connection(identifiantTxt.getText(), String.valueOf((passwordField.getPassword())));
			if(connectionEstablished){
				controller.getGui().changeWindow(WindowList.OBJECTGESTION);
			}
			else
				this.add(ConnectionFailed);
				controller.getGui().repaint();
		}
	}
}
