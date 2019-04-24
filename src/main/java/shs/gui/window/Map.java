package shs.gui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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


	//TODO Faire fonctionner le update. 
	//TODO surcharger le constructeur pour récupérer la liste des alertes. -map(List<Capteurs>)
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
	private JComboBox<String> comboStage;
	private JLabel lblMessage; 

	/**
	 * Legend
	 */
	private JButton freePlace; 
	private JButton occupiedPlace; 
	private JButton alertPlace; 
	private JLabel lblFreePlace; 
	private JLabel lblOccupiedPlace; 
	private JLabel lblAlertPlace;
	private JLabel lblLegend; 

	private GuiController controller; 
	private String[] tabImage; 

	/**
	 * Gestion Table
	 */
	private JTable objectTable; 
	private List<List<String>> listObjectNullEmplacement = new ArrayList<List<String>>(); 
	List<List<String>> listAllCapteurs = new ArrayList<List<String>>(); 
	private Object[][] data;
	private JScrollPane scrollPane;

	/**
	 * Gestion Emplacements
	 */
	private List<List<String>> listEmplacement;
	private List<JButton> listJButtonsEtage1; 
	private List<JButton> listJButtonsEtage2; 
	private List<String> listEmplacementOccupied; 



	//-------------------------------------------------------
	// CONTROLER
	//-------------------------------------------------------
	public Map(GuiController controller) {
		this.controller = controller; 

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.controller.getGui().setSize(screensize);
		this.controller.getGui().setLocationRelativeTo(null);
		setBackground(new Color(95, 158, 160));
		this.setLayout(null);

		btnRetour = new JButton("Retour");
		btnRetour.setBounds(12, 15, 90, 44);
		btnRetour.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnRetour.addActionListener(this);
		this.add(btnRetour);

		//To get only the objects with an empty emplacement.

		listAllCapteurs = controller.listCapteurs();
		for(int i= 0; i<listAllCapteurs.size(); i++) {
			if((listAllCapteurs.get(i).get(3) == null)) {
				listObjectNullEmplacement.add(listAllCapteurs.get(i));
			}
		}
		gestionListObject();
		this.add(scrollPane, BorderLayout.CENTER);

		comboStage = new JComboBox<String>();
		comboStage.setModel(new DefaultComboBoxModel<String>(new String[] {"Etage 1", "Etage 2"}));
		comboStage.setBounds(108, 661, 204, 44);
		comboStage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		comboStage.addActionListener(this);
		this.add(comboStage);


		// Create JButton from the listEmplacement
		listEmplacement = controller.EmplacementFull(); 
		listJButtonsEtage1 = new ArrayList<JButton>();
		listJButtonsEtage2 = new ArrayList<JButton>(); 
		listEmplacementOccupied = listEmplacementOccupied(listEmplacement);
		boolean stop = true; 

		for(int ligne = 0; ligne< listEmplacement.size(); ligne++) {
			stop = true; 
			for(int i = 0; i<listEmplacementOccupied.size();  i++) {
				if(listEmplacement.get(i).get(0).equals(listEmplacementOccupied.get(i))) {
					JButton newButton = new JButton(); 
					newButton.setBounds(
							Integer.parseInt(listEmplacement.get(ligne).get(3)),
							Integer.parseInt(listEmplacement.get(ligne).get(4)), 
							Integer.parseInt(listEmplacement.get(ligne).get(5)), 
							Integer.parseInt(listEmplacement.get(ligne).get(6))
							);
					newButton.setBackground(Color.GREEN);
					if(listEmplacement.get(ligne).get(2).toString().equals("1")) {
						listJButtonsEtage1.add(newButton); stop = false; 
					}
					else {
						listJButtonsEtage2.add(newButton); stop = false; 
					}
				}

			}
			if(stop = true) {
				JButton newButton = new JButton(); 
				newButton.setBounds(Integer.parseInt(listEmplacement.get(ligne).get(3)),
						Integer.parseInt(listEmplacement.get(ligne).get(4)), 
						Integer.parseInt(listEmplacement.get(ligne).get(5)), 
						Integer.parseInt(listEmplacement.get(ligne).get(6)));
				newButton.setBackground(Color.GRAY);
				if(listEmplacement.get(ligne).get(2).toString().equals("1"))
					listJButtonsEtage1.add(newButton); 
				else 
					listJButtonsEtage2.add(newButton); 
			}
		}


		if(comboStage.getSelectedItem().toString().equals("Etage 1")) {
			removeButton(listJButtonsEtage2);
			displayButton(listJButtonsEtage1);
		}
		else {
			removeButton(listJButtonsEtage1);
			displayButton(listJButtonsEtage2);
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

		lblMessage = new JLabel();
		lblMessage.setBounds(12, 711, 467, 33);
		lblMessage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMessage.setForeground(Color.RED);
		this.add(lblMessage);

		freePlace = new JButton();
		freePlace.setBounds(242, 819, 99, 33);
		freePlace.setBackground(Color.GRAY); 
		freePlace.setEnabled(false);
		this.add(freePlace);

		occupiedPlace = new JButton();
		occupiedPlace.setBounds(242, 865, 99, 33);
		occupiedPlace.setBackground(Color.GREEN); 
		occupiedPlace.setEnabled(false);
		this.add(occupiedPlace);

		alertPlace = new JButton();
		alertPlace.setBounds(242, 912, 99, 33);
		alertPlace.setBackground(Color.RED); 
		alertPlace.setEnabled(false);
		this.add(alertPlace);



		lblFreePlace = new JLabel("Emplacement libre : ");
		lblFreePlace.setBounds(39, 830, 302, 25);
		lblFreePlace.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(lblFreePlace);

		lblOccupiedPlace = new JLabel("Emplacement occup\u00E9 : ");
		lblOccupiedPlace.setBounds(39, 869, 302, 25);
		lblOccupiedPlace.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(lblOccupiedPlace);

		lblAlertPlace = new JLabel("Emplacement en alerte : ");
		lblAlertPlace.setBounds(39, 916, 302, 25);
		lblAlertPlace.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(lblAlertPlace);

		lblLegend = new JLabel("L\u00E9gende");
		lblLegend.setBounds(148, 767, 193, 25);
		lblLegend.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(lblLegend);

	}


	/**
	 * 
	 * @param listButton
	 */
	public void displayButton(List<JButton> listButton) {
		for(int ligne = 0; ligne< listButton.size(); ligne++) {
			listButton.get(ligne).addActionListener(this);
			this.add(listButton.get(ligne));
		}
		this.revalidate();
	}

	/**
	 * 
	 * @param listButton
	 */
	public void removeButton(List<JButton> listButton) {
		for(int ligne = 0; ligne< listButton.size(); ligne++) {
			this.remove(listButton.get(ligne));
		}
		this.revalidate();
	}


	/**
	 * This methode return the list of the Emplacement occupied identify by the ID_Emplacement
	 * @param listEmplacement
	 * @return
	 */
	public List<String> listEmplacementOccupied(List<List<String>> listEmplacement) {

		List<String> listEmplacementOccupied = new ArrayList<String>(); 
		for (int i = 0; i<listAllCapteurs.size(); i++) {
			if(listAllCapteurs.get(i).get(3)!= null) {
				for(int j=0; j<listEmplacement.size(); j++) {
					if(listEmplacement.get(j).get(0).equals(listAllCapteurs.get(i).get(3))){
						listEmplacementOccupied.add(listEmplacement.get(j).get(0)); 
					}

				}
			}

		}
		return listEmplacementOccupied; 
	}

	/**
	 * This method is used to display the JTable of objects
	 */
	public void gestionListObject() {
		String[] header = {"ID_capteur", "Type du capteur", "Etat capteur", "Mac"}; 
		Integer x = listObjectNullEmplacement.size(); 
		Integer y;
		if(x>0) { 
			// If the result is not empty, we could fill our table
			y = listObjectNullEmplacement.get(0).size()-1; //we don't want the "Emplacement" ID
			data = new Object[x][y]; 
			Integer lineNumber = 0;
			Integer columnNumber = 0;
			for(List<String> line : listObjectNullEmplacement) {
				for(String column : line) {
					if(columnNumber<=1) {
						data[lineNumber][columnNumber]=column;
						columnNumber++;
						continue;
					}
					if(columnNumber==2) {
						if(column.equals("0"))
							data[lineNumber][columnNumber]="Disable";
						else
							data[lineNumber][columnNumber]="Enable";
						columnNumber++;
						continue;
					}
					if(columnNumber==3) {
						// We don't want the "emplacement ID"
						columnNumber++;
						continue;
					}
					if(columnNumber==4) {
						data[lineNumber][columnNumber-1]=column;
						columnNumber++;
					}
				}
				columnNumber=0;
				lineNumber++;
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

	/**
	 * Method use to get the values of the selected row.
	 * @return
	 */
	private List<String> getRowUpdate(){
		int ligne = objectTable.getSelectedRow(); 
		List<String> listUpdate = new ArrayList<String>(); 
		for(int i = 0; i<data[ligne].length; i++) {
			if(data[ligne][i]!=null)
				listUpdate.add(data[ligne][i].toString()); 
			else
				listUpdate.add(null);
		}
		return listUpdate;
	}

	/**
	 * This methode return the ID_Emplacement of the JButton selected as a String
	 * @param button
	 * @return
	 */
	public String getIdEmplacement(JButton button) {
		for(int i= 0; i< listEmplacement.size(); i++) {
			if(Integer.parseInt(listEmplacement.get(i).get(3)) == button.getX() && Integer.parseInt(listEmplacement.get(i).get(4)) == button.getY()){
				return listEmplacement.get(i).get(0);
			}
		}
		return null; 

	}

	//-------------------------------------------------------
	// ACTION PERFORMED ON THE ELEMENTS OF THE FRAME
	//-------------------------------------------------------


	public void actionPerformed(ActionEvent event) {

		if(comboStage.getSelectedItem().toString().equals("Etage 1")) {
			for(int i = 0; i< listJButtonsEtage1.size(); i++) {
				if(event.getSource().equals(listJButtonsEtage1.get(i))){
					if(objectTable.getSelectedRow() != -1) {
						listJButtonsEtage1.get(i).setBackground(Color.GREEN);
						if(controller.updateEmplacementObject(getRowUpdate().get(0), getIdEmplacement(listJButtonsEtage1.get(i)))) {
							gestionListObject();
							objectTable.repaint(); 
						}
						
						lblMessage.setText("");
						lblMessage.repaint();
						
						
					}
					else {
						lblMessage.setText("Veuillez sélectionner une ligne dans le tableau.");
						lblMessage.repaint();
					}
				}
			}
		}

		if(event.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}

		if(event.getSource().equals(comboStage)) {

			if(comboStage.getSelectedItem().toString().equals("Etage 1")) {
				removeButton(listJButtonsEtage2);

				try {
					image = ImageIO.read(getClass().getResource("/images/"+ tabImage[0]));  
				} catch (IOException e) {
					System.out.println("ERROR - IMAGE NOT FOUND");
					e.printStackTrace();
				}
				imageIcon.setImage(image);
				picLabel.setIcon(imageIcon);
				displayButton(listJButtonsEtage1);


			}

			if(comboStage.getSelectedItem().toString().equals("Etage 2")) {
				removeButton(listJButtonsEtage1);
				try {
					image = ImageIO.read(getClass().getResource("/images/"+ tabImage[1]));  
				} catch (IOException e) {
					System.out.println("ERROR - IMAGE NOT FOUND");
					e.printStackTrace();
				}
				displayButton(listJButtonsEtage2);
				imageIcon.setImage(image);
				picLabel.setIcon(imageIcon);


			}
			this.repaint();

		}

	}

}
