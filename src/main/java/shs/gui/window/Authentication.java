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

public class Authentication extends JPanel implements ActionListener {

	private GuiController controller;
	
	protected JLabel authenticationtTitleLabel;
	protected JLabel loginTitleLabel;
	protected JLabel passwordTitleLabel;
	protected JTextField loginField;
	protected JPasswordField passwordField;
	protected JButton validateButton; 
	protected JLabel lineLabel1;
	protected JLabel lineLabel2;
	
	protected JLabel connectionFailedTitleLabel;
	
	protected JLabel serverConnectionFailedTitleLabel;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public Authentication(GuiController controller) {
		
		this.controller = controller;
		
		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);
		
		authenticationtTitleLabel = new JLabel("Authentification");
		authenticationtTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 23));
		authenticationtTitleLabel.setBounds(609, 267, 189, 43);
		this.add(authenticationtTitleLabel);

		loginTitleLabel = new JLabel("Identifiant :");
		loginTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 17));
		loginTitleLabel.setBounds(514, 326, 106, 30);
		this.add(loginTitleLabel);

		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblMotDePasse.setBounds(503, 369, 122, 30);
		this.add(lblMotDePasse);

		loginField = new JTextField();
		loginField.setBounds(637, 327, 223, 26);
		loginField.setColumns(10);
		this.add(loginField);

		validateButton = new JButton("Valider");
		validateButton.setFont(new Font("Cambria Math", Font.BOLD, 15));
		validateButton.addActionListener(this);

		validateButton.setBounds(609, 486, 189, 43);
		this.add(validateButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(637, 371, 223, 26);
		this.add(passwordField);

		lineLabel1 = new JLabel("--------------------------------------------------------------------------------------------------");
		lineLabel1.setBounds(502, 433, 501, 16);
		this.add(lineLabel1);

		lineLabel2 = new JLabel("--------------------------------------------------------------------------------------------------");
		lineLabel2.setBounds(502, 249, 501, 16);
		this.add(lineLabel2);
		
		connectionFailedTitleLabel = new JLabel("Identifiant ou mot de passe invalide.");
		connectionFailedTitleLabel.setForeground(new Color(128, 0, 0));
		connectionFailedTitleLabel.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		connectionFailedTitleLabel.setBounds(573, 457, 255, 16);
		// ConnectionFailed label is only visible after a password error
		
		serverConnectionFailedTitleLabel = new JLabel("Aucune connexion au serveur est disponible");
		serverConnectionFailedTitleLabel.setForeground(new Color(128, 0, 0));
		serverConnectionFailedTitleLabel.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		serverConnectionFailedTitleLabel.setBounds(550, 457, 320, 16);
		// serverConnectionFailed label is only visible when server is offline
		if(!controller.ping()) {
			this.add(serverConnectionFailedTitleLabel);
		}
		
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(validateButton)) {
			Boolean connectionEstablished = controller.connection(loginField.getText(), String.valueOf((passwordField.getPassword())));
			if(connectionEstablished){
				controller.getGui().changeWindow(WindowList.OBJECTGESTION);
			}
			else
				this.add(connectionFailedTitleLabel);
				controller.getGui().repaint();
		}
	}
}
