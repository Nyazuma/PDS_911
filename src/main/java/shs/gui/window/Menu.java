package shs.gui.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shs.gui.GuiController;

public class Menu extends JPanel implements ActionListener{

	private GuiController controller; 

	private JLabel lblTitre;
	private JButton btnGestionObjet; 
	private JButton btnSurveillance; 
	private JButton btnPlan; 
	private JButton btnStatistique; 

	public Menu(GuiController controller) {
		this.controller = controller; 

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		lblTitre = new JLabel("Veuillez indiquer l'action que vous souhaitez r\u00E9aliser : ");
		lblTitre.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTitre.setBounds(541, 128, 320, 22);
		this.add(lblTitre);

		btnGestionObjet = new JButton("Gestion des objets");
		btnGestionObjet.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnGestionObjet.setBounds(703, 449, 320, 164);
		btnGestionObjet.setBackground(new Color(0, 204, 153));
		btnGestionObjet.addActionListener(this); 
		this.add(btnGestionObjet);

		btnSurveillance = new JButton("Surveillance des objets");
		btnSurveillance.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnSurveillance.setBounds(703, 260, 320, 175);
		btnSurveillance.setBackground(new Color(255, 153, 204));
		btnSurveillance.addActionListener(this); 
		this.add(btnSurveillance);

		btnPlan = new JButton("Plan de la r\u00E9sidence");
		btnPlan.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnPlan.setBounds(363, 261, 328, 173);
		btnPlan.setBackground(new Color(255, 102, 102));
		btnPlan.addActionListener(this); 
		this.add(btnPlan);

		btnStatistique = new JButton("Statistique des objets");
		btnStatistique.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnStatistique.setBounds(363, 447, 328, 166);
		btnStatistique.setBackground(new Color(153, 255, 204));
		btnStatistique.addActionListener(this); 
		this.add(btnStatistique);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnGestionObjet)) {
			controller.getGui().changeWindow(WindowList.OBJECTGESTION);
		}
		
		if(event.getSource().equals(btnSurveillance)) {
			
		}
		if(event.getSource().equals(btnPlan)) {
			
		}
		if(event.getSource().equals(btnStatistique)) {
			
		}
		
	}

}
