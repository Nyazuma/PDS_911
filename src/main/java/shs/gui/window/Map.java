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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import shs.gui.GuiController;

public class Map extends JPanel implements ActionListener{


	//TODO Faire fonctionner le update. 
	//TODO surcharger le constructeur pour récupérer la liste des alertes. -map(List<Capteurs>)
	//TODO gérer la libération d'un capteur 
	//TODO mettre une légende 

	/**
	 * Management Image
	 */
	private BufferedImage image; 
	private JLabel picLabel;
	private ImageIcon imageIcon; 


	private JButton btnRetour; 
	private JComboBox<String> comboStage;
	private JLabel lblMessage; 

	/**
	 * Management delete
	 */
	private JCheckBox checkBoxDelete;

	/**
	 * Management Legend
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
	 * Management Table
	 */
	private JTable objectTable; 
	private List<List<String>> listObjectNullEmplacement = new ArrayList<List<String>>(); 
	List<List<String>> listAllCapteurs = new ArrayList<List<String>>(); 
	private Object[][] data;
	private JScrollPane scrollPane;

	/**
	 * Management location
	 */
	private List<List<String>> listEmplacement;
	private List<JButton> listJButtonsEtage1; 
	private List<JButton> listJButtonsEtage2; 
	private List<String> listEmplacementOccupied; 

	/**
	 * Management Information frame
	 */
	JLabel lblTitle; 
	JLabel infoTypeCapteur;
	JLabel infoStateCapteur;
	JLabel infoStairsCapteur;
	JLabel infoRoomCapteur;
	JLabel infoAddressMac;

	//-------------------------------------------------------
	// CONTROLLER
	//-------------------------------------------------------
	/**
	 * Main Controller
	 * @param controller
	 */
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
				if(listEmplacement.get(ligne).get(0).equals(listEmplacementOccupied.get(i))) {
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


		//Get table Etage and read Image on line.
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

		// JLabel used to display messages (see messages in ActionPerformed).
		lblMessage = new JLabel();
		lblMessage.setBounds(12, 711, 467, 33);
		lblMessage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMessage.setForeground(Color.RED);
		this.add(lblMessage);


		// Legend part of the interface.
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

		checkBoxDelete= new JCheckBox("Veuillez cocher cette case pour enlever l'emplacement d'un capteur ");
		checkBoxDelete.setBounds(508, 23, 668, 25);
		lblLegend.setFont(new Font("Cambria Math", Font.BOLD, 16));
		checkBoxDelete.setBackground(new Color(95, 158, 160));
		this.add(checkBoxDelete);

	}


	/**
	 * Display the button on the screen
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
	 * Remove the button of the screen
	 * @param listButton
	 */
	public void removeButton(List<JButton> listButton) {
		for(int ligne = 0; ligne< listButton.size(); ligne++) {
			this.remove(listButton.get(ligne));
		}
		this.revalidate();
	}

	/**
	 * This methode return true if the location is free and false otherwise
	 * @param button
	 * @return
	 */
	public boolean isEmplacementFree(JButton button) {
		List<String> listEmplacementIDOccupied = new ArrayList<String>();
		listEmplacementIDOccupied = listEmplacementOccupied(listEmplacement); 

		for (int i = 0; i< listEmplacementIDOccupied.size(); i++) {
			if(getIdEmplacement(button).equals(listEmplacementIDOccupied.get(i))) {
				return false;
			}
		}
		return true; 

	}

	/**
	 * This method return the ID_Emplacement of the JButton selected as a String
	 * @param button
	 * @return
	 */
	private String getIdEmplacement(JButton button) {
		for(int i= 0; i< listEmplacement.size(); i++) {
			if(Integer.parseInt(listEmplacement.get(i).get(3)) == button.getX() && Integer.parseInt(listEmplacement.get(i).get(4)) == button.getY()){
				return listEmplacement.get(i).get(0);
			}
		}
		return null; 

	}

	/**
	 * This method return the ID of the Object from a specific location
	 * @param button
	 * @return
	 */
	private String getIdObject(JButton button) {
		String locationID = getIdEmplacement(button); 
		for (int i = 0; i<listAllCapteurs.size(); i++) {
			if(listAllCapteurs.get(1).get(3).equals(locationID)) {
				return listAllCapteurs.get(1).get(0);
			}
		}
		return null; 
	}

	/**
	 * This method return the list of the location occupied identify by the ID_Emplacement
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
	 * Method used to determine if the position of the sensor is pertinent or not. 
	 * Two sensor of the same type can't be neighbour. 
	 * @param button
	 * @return
	 */
	private boolean isLocationPertinent(JButton button) {

		String beforeEmplacementID = null; 
		String afterEmplacementID = null; 

		String beforeTypeCapteur = null; 
		String afterTypeCapteur = null; 

		String typeCapteur = getRowUpdate().get(1);
		String locationID = getIdEmplacement(button); 



		for(int i = 0; i<listEmplacement.size(); i++) {
			if(listEmplacement.get(i).get(0).equals(locationID)) {
				if(listEmplacement.get(i-1)!= null) {
					System.out.println("1");
					beforeEmplacementID = listEmplacement.get(i-1).get(0);
				}
				if(listEmplacement.get(i+1)!= null) {
					System.out.println("2");
					afterEmplacementID = listEmplacement.get(i+1).get(0);
				}
			}
		}

		List<String> listOcupiedLocation = new ArrayList<String>();
		listOcupiedLocation = listEmplacementOccupied(listEmplacement);

		for(int i=0; i< listOcupiedLocation.size(); i++) {
			if(beforeEmplacementID != null) {
				if(listOcupiedLocation.get(i).equals(beforeEmplacementID)) {
					for(int j=0; j<listAllCapteurs.size(); j++) {
						if(listAllCapteurs.get(j).get(3) !=null) {
							if(listAllCapteurs.get(j).get(3).equals(beforeEmplacementID)) {
								beforeTypeCapteur = listAllCapteurs.get(j).get(1);
								break; 
							}
						}
					}
				}
			}
			if(afterEmplacementID != null) {
				if(listOcupiedLocation.get(i).equals(afterEmplacementID)) {
					for(int j=0; j<listAllCapteurs.size(); j++) {
						if(listAllCapteurs.get(j).get(3) !=null) {
							if(listAllCapteurs.get(j).get(3).equals(afterEmplacementID)) {
								afterTypeCapteur = listAllCapteurs.get(j).get(1);
								break; 
							}
						}
					}
				}
			}
		}

		if((afterTypeCapteur == null && beforeTypeCapteur != null && beforeTypeCapteur.equals(typeCapteur)) || (beforeTypeCapteur == null && afterTypeCapteur != null && afterTypeCapteur.equals(typeCapteur)) || (beforeTypeCapteur != null && afterTypeCapteur != null && typeCapteur.equals(beforeTypeCapteur) && typeCapteur.equals(afterTypeCapteur)))
			return false;
		else 
			return true; 

	}

	/**
	 * Method used to create and initialize the information window of an occupied location
	 * @param button
	 */
	private void informationCapteur(JButton button) {

		List<List<String>> listInformationLocation = new ArrayList<List<String>>();
		List<List<String>> listInformationCapteur = new ArrayList<List<String>>();

		for(int i =0; i<listEmplacement.size(); i++) {
			if(listEmplacement.get(i).get(0).equals(getIdEmplacement(button)))
				listInformationLocation.add(listEmplacement.get(i));
		}

		for (int i=0; i<listAllCapteurs.size(); i++) {
			if(listAllCapteurs.get(i).get(3) != null) {
				if(listAllCapteurs.get(i).get(3).equals(getIdEmplacement(button)))
					listInformationCapteur.add(listAllCapteurs.get(i));
			}
		}


		JFrame informationWindow = new JFrame(); 
		informationWindow.setTitle("Informations du capteur");
		informationWindow.setBounds(100, 100, 549, 638);
		informationWindow.setLocationRelativeTo(null);
		informationWindow.getContentPane().setBackground(new Color(0, 204, 255));
		informationWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);             
		informationWindow.setVisible(true);

		lblTitle = new JLabel("Informations concernant le capteur ");
		lblTitle.setBounds(113, 19, 310, 29);
		lblMessage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(lblTitle);

		JLabel infoLblTypeDeCapteur = new JLabel("Type de capteur : ");
		infoLblTypeDeCapteur.setBounds(12, 75, 145, 29);
		infoLblTypeDeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoLblTypeDeCapteur);

		infoTypeCapteur = new JLabel(listInformationCapteur.get(0).get(1));
		infoTypeCapteur.setBounds(234, 75, 254, 29);
		infoTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoTypeCapteur);

		JLabel infoLblStateCapteur = new JLabel("Etat du capteur : ");
		infoLblStateCapteur.setBounds(12, 128, 145, 29);
		infoLblStateCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoLblStateCapteur);

		infoStateCapteur = new JLabel((listInformationCapteur.get(0).get(2).equals("1")) ? "Activé" : "Désactivé");
		infoStateCapteur.setBounds(234, 128, 254, 29);
		infoStateCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoStateCapteur);

		JLabel infoLblStairsCapteur = new JLabel("Etage Capteur :");
		infoLblStairsCapteur.setBounds(12, 187, 145, 29);
		infoLblStairsCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoLblStairsCapteur);

		infoStairsCapteur = new JLabel(listInformationLocation.get(0).get(2));
		infoStairsCapteur.setBounds(234, 187, 254, 29);
		infoStairsCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoStairsCapteur);

		JLabel infoLblRoomCapteur = new JLabel("Pi\u00E8ce capteur : ");
		infoLblRoomCapteur.setBounds(12, 243, 145, 29);
		infoLblRoomCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoLblRoomCapteur);

		infoRoomCapteur = new JLabel(listInformationLocation.get(0).get(1));
		infoRoomCapteur.setBounds(234, 249, 254, 29);
		infoRoomCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoRoomCapteur);

		JLabel lblAddresseMac = new JLabel("Addresse MAC : ");
		lblAddresseMac.setBounds(12, 309, 145, 29);
		lblAddresseMac.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(lblAddresseMac);

		infoAddressMac = new JLabel(listInformationCapteur.get(0).get(4));
		infoAddressMac.setBounds(234, 309, 254, 29);
		infoAddressMac.setFont(new Font("Cambria Math", Font.BOLD, 16));
		informationWindow.getContentPane().add(infoAddressMac);

		//guardrail
		JLabel label_3 = new JLabel();
		label_3.setBounds(12, 363, 145, 29);
		informationWindow.add(label_3);

		JLabel label_4 = new JLabel();
		label_4.setBounds(234, 351, 254, 29);
		informationWindow.add(label_4);

	}



	//-------------------------------------------------------
	// ACTION PERFORMED ON THE ELEMENTS OF THE FRAME
	//-------------------------------------------------------


	public void actionPerformed(ActionEvent event) {

		if(comboStage.getSelectedItem().toString().equals("Etage 1")) {

			for(int i = 0; i< listJButtonsEtage1.size(); i++) {
				if(checkBoxDelete.isSelected() && !isEmplacementFree(listJButtonsEtage1.get(i)))
				{
					listJButtonsEtage1.get(i).setBackground(Color.GRAY);
					if(controller.deleteEmplacementObject(getIdObject(listJButtonsEtage1.get(i)))) {
						gestionListObject();
						objectTable.repaint(); 
					}
					break; 
				}
				if(event.getSource().equals(listJButtonsEtage1.get(i))){
					if((!isEmplacementFree(listJButtonsEtage1.get(i))) && (objectTable.getSelectedRow() == -1)) {
						informationCapteur(listJButtonsEtage1.get(i));
						break; 
					}
					System.out.println("Je suis la : " +checkBoxDelete.isSelected());

					if(objectTable.getSelectedRow() != -1) {
						if(isEmplacementFree(listJButtonsEtage1.get(i))){
							if(isLocationPertinent(listJButtonsEtage1.get(i))) {
								listJButtonsEtage1.get(i).setBackground(Color.GREEN);
								if(controller.updateEmplacementObject(getRowUpdate().get(0), getIdEmplacement(listJButtonsEtage1.get(i)))) {
									gestionListObject();
									objectTable.repaint(); 
								}
								else {
									System.out.println("ERROR DURING UPDATING OBJECT");
								}
								lblMessage.setText("");
								lblMessage.repaint();
							}else {
								lblMessage.setText("Champs non pertinent !");
								lblMessage.repaint();
							}
						}
						else {
							lblMessage.setText("Veuillez sélectionner un emplacement libre");
							lblMessage.repaint();
						}
					}
					else {
						lblMessage.setText("Veuillez sélectionner une ligne dans le tableau.");
						lblMessage.repaint();
					}
				}
			}
		}
		if(comboStage.getSelectedItem().toString().equals("Etage 2")) {
			for(int i = 0; i< listJButtonsEtage2.size(); i++) {
				if(checkBoxDelete.isSelected() && !isEmplacementFree(listJButtonsEtage2.get(i)))
				{
					listJButtonsEtage2.get(i).setBackground(Color.GRAY);
					if(controller.deleteEmplacementObject(getIdObject(listJButtonsEtage2.get(i)))) {
						gestionListObject();
						objectTable.repaint(); 
					}
					break; 
				}
				if(event.getSource().equals(listJButtonsEtage2.get(i))){
					if((!isEmplacementFree(listJButtonsEtage2.get(i))) && (objectTable.getSelectedRow() == -1)) {
						informationCapteur(listJButtonsEtage2.get(i));
						break; 
					}

					if(objectTable.getSelectedRow() != -1) {
						if(isEmplacementFree(listJButtonsEtage2.get(i))){
							if(isLocationPertinent(listJButtonsEtage2.get(i))) {
								listJButtonsEtage2.get(i).setBackground(Color.GREEN);
								if(controller.updateEmplacementObject(getRowUpdate().get(0), getIdEmplacement(listJButtonsEtage2.get(i)))) {
									gestionListObject();
									objectTable.repaint(); 
								}
								else {
									System.out.println("ERROR DURING UPDATING OBJECT");
								}

								lblMessage.setText("");
								lblMessage.repaint();
							}else {
								lblMessage.setText("Champs non pertinent !");
								lblMessage.repaint();
							}
						}
						else {
							lblMessage.setText("Veuillez sélectionner un emplacement disponible.");
							lblMessage.repaint();
						}
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
