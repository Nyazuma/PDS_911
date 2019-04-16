package shs.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import shs.gui.GuiController;

public class Map extends JPanel implements ActionListener{
	
	//TODO Revoir la taille des JLabel qui n'augmentent pas !
	//TODO Aller chercher l'image sur le serveur 
	//TODO Placer les boutons sur l'image
	//TODO récupérer la liste des objets sans emplacements
	//TODO gérer l'affectation des objets sur un emplacement 
	//TODO afficher un récapitulatif pour chaque objet
	//TODO gérer la libération d'un capteur 
	//TODO mettre une légende 

	/**
	 * Image variables
	 */
	private BufferedImage image; 
	private JLabel picLabel;
	private ImageIcon imageIcon; 


	private JButton btnRetour; 
	private JTable table;
	JComboBox<String> comboStage; 

	private GuiController controller; 

	public Map(GuiController controller) {
		this.controller = controller; 
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.controller.getGui().setSize(screensize);
		this.controller.getGui().setLocationRelativeTo(null);
		setBackground(new Color(95, 158, 160));
		this.setLayout(null);

		//this.controller.getGui().getContentPane().add(this); 
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(12, 15, 90, 44);
		btnRetour.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnRetour.addActionListener(this);
		this.add(btnRetour);

		table = new JTable();
		table.setBounds(12, 99, 438, 527);
		table.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(table);

		comboStage = new JComboBox<String>();
		comboStage.setModel(new DefaultComboBoxModel(new String[] {"Etage 1", "Etage 2"}));
		comboStage.setBounds(108, 661, 204, 44);
		comboStage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		comboStage.addActionListener(this);
		this.add(comboStage);

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("Etage1.JPG");
		try {
			image = ImageIO.read(getClass().getResource("/images/Etage1.JPG"));  
		}catch(Exception e) {
			System.out.println("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
			
		imageIcon = new ImageIcon(image); 
		picLabel = new JLabel(imageIcon); 
		picLabel.setBounds(470, 0, 1500, 1000);
		this.add(picLabel);

	}


	public void actionPerformed(ActionEvent event) {
		
		
		if(event.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}

		if(event.getSource().equals(comboStage)) {

			if(comboStage.getSelectedItem().toString().equals("Etage 1")) {
				try {
					image = ImageIO.read(getClass().getResource("/images/Etage1.JPG"));  
				} catch (IOException e) {
					System.out.println("ERROR - IMAGE NOT FOUND");
					e.printStackTrace();
				}
				imageIcon.setImage(image);
				picLabel.setIcon(imageIcon);
			}

			if(comboStage.getSelectedItem().toString().equals("Etage 2")) {
				try {
					image = ImageIO.read(getClass().getResource("/images/Etage2.png"));  
				} catch (IOException e) {
					System.out.println("ERROR - IMAGE NOT FOUND");
					e.printStackTrace();
				}
				imageIcon.setImage(image);
				picLabel.setIcon(imageIcon);

			}
			picLabel.repaint();
		}

	}

}
