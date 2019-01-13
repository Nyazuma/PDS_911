package interfaceGestion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IhmBean {
	


protected JPanel panel = new JPanel(); 
	
	//****************************
	// AUTHENTIFICATION VARIABLES
	//****************************
	protected static JTextField textName = new JTextField(); 
	protected static JTextField textpwd = new JTextField(); 
	protected static JButton buttonValider = new JButton("Valider"); 
	
	
	protected  JPanel IhmAunthentification(){
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(128, 159, 255));
		JLabel label = new JLabel("AUTHENTIFICATION"); 
		JPanel flowPan = new JPanel(new FlowLayout()); 
		JPanel gridPan = new JPanel(new GridLayout(3, 1)); 
		JPanel gridPan2 = new JPanel(new GridLayout(2, 2)); 
		
		gridPan.add(label); 
		gridPan2.add(new JLabel("NAME")); 
		gridPan2.add(textName); 
		gridPan2.add(new JLabel("PASSWORD"));
		gridPan2.add(textpwd); 
		gridPan.add(gridPan2, BorderLayout.CENTER);
		gridPan.add(buttonValider); 
		
		flowPan.add(gridPan, BorderLayout.CENTER); 
		panel.add(flowPan, BorderLayout.CENTER); 
		return panel; 
	}
}