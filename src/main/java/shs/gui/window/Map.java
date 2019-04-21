package shs.gui.window;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private String[] tabImage; 

	/**
	 * Gestion Table
	 */
	private JTable objectTable; 
	private List<List<String>> listObject = new ArrayList<List<String>>(); 
	private Object[][] data;
	private JScrollPane scrollPane;
	
	/**
	 * Gestion Emplacements
	 */
	private List<List<String>> listEmplacement;
	private List<JButton> listJButtonsEtage1; 
	

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

//		table = new JTable();
//		table.setBounds(12, 99, 438, 527);
//		table.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		this.add(table);
		
		listObject = controller.readObjects();
		gestionListObject();
		this.add(scrollPane, BorderLayout.CENTER);

		comboStage = new JComboBox<String>();
		comboStage.setModel(new DefaultComboBoxModel(new String[] {"Etage 1", "Etage 2"}));
		comboStage.setBounds(108, 661, 204, 44);
		comboStage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		comboStage.addActionListener(this);
		this.add(comboStage);
		
		listEmplacement = controller.EmplacementFull(); 
		listJButtonsEtage1 = new ArrayList<JButton>();
		
		for(int ligne = 0; ligne< listEmplacement.size(); ligne++) {
			if(listEmplacement.get(ligne).get(2).toString().equals("1")  && comboStage.getSelectedItem().toString().equals("Etage 1") ) {
			JButton newButton = new JButton(); 
			newButton.setBounds(Integer.parseInt(listEmplacement.get(ligne).get(3)), Integer.parseInt(listEmplacement.get(ligne).get(4)), Integer.parseInt(listEmplacement.get(ligne).get(5)), Integer.parseInt(listEmplacement.get(ligne).get(6)));
			listJButtonsEtage1.add(newButton); 
			this.add(newButton);
			}
		}
		
		
		//Get table Etage and read Image on line 
		tabImage = controller.readEtageImage(); 
		try {
			image = ImageIO.read(getClass().getResource("/images/" + tabImage[0]));  
		}catch(Exception e) {
			System.out.println("ERROR - IMAGE NOT FOUND");
			e.printStackTrace();
		}
		

		imageIcon = new ImageIcon(image); 
		picLabel = new JLabel(imageIcon); 
		picLabel.setBounds(491, 99, 1345, 824);
		this.add(picLabel);

	}
	
	public void gestionListObject() {
		String[] header = {"ID_capteur", "Type du capteur",  "Etat capteur", "Mac"}; 
		Integer x = listObject.size(); 
		Integer y;
		if(x>0) { 
			System.out.println("Je suis la ");
			// If the result is not empty, we could fill our table
			y = listObject.get(0).size();
			data = new Object[x][y]; 
			Integer i = 0;
			Integer j = 0;
			for(List<String> line : listObject) {
				for(String column : line) {
					if(j!=2)
						data[i][j]=column;
					else {
						// Status
						if(column.equals("1")) 
							data[i][2] = "Enable"; 
						else 
							data[i][2] = "Disable"; 
					}
					j++;
				}
				j=0;
				i++;
			}

			objectTable = new JTable(data, header);
			// We cancel the user selection of a table case.
			objectTable.setDefaultEditor(Object.class, null);
			//Hide ID column
			objectTable.getColumnModel().getColumn(0).setMinWidth(0);
			objectTable.getColumnModel().getColumn(0).setMaxWidth(0);
		}
		else {
			// if the result is empty, the table will be empty
			objectTable = new JTable();
		}


		scrollPane = new JScrollPane(objectTable);
		scrollPane.setBounds(12, 99, 438, 527);

	}


	public void actionPerformed(ActionEvent event) {


		if(event.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}

		if(event.getSource().equals(comboStage)) {

			if(comboStage.getSelectedItem().toString().equals("Etage 1")) {
				try {
					image = ImageIO.read(getClass().getResource("/images/"+ tabImage[0]));  
				} catch (IOException e) {
					System.out.println("ERROR - IMAGE NOT FOUND");
					e.printStackTrace();
				}
				imageIcon.setImage(image);
				picLabel.setIcon(imageIcon);
			}

			if(comboStage.getSelectedItem().toString().equals("Etage 2")) {
				try {
					image = ImageIO.read(getClass().getResource("/images/"+ tabImage[1]));  
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
