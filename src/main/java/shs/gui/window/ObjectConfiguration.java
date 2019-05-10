package shs.gui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;

import shs.gui.GuiController;

public class ObjectConfiguration extends JPanel implements ActionListener{

	private GuiController controller;
	protected static Object[][] data; 
	protected static List<List<String>> listObject; 
	private JLabel labTitre; 
	private JComboBox<String> typeCapteur; 
	private JLabel lblTypeCapteur; 
	private JComboBox<String> etatCapteur; 
	private JLabel lblEtatCapteur;
	private JTextField minCapteur; 
	private JLabel lblMinCapteur;
	private JTextField maxCapteur; 
	private JLabel lblMaxCapteur;
	private JCheckBox confirmation; 
	private JButton valider; 
	private JButton returnButton; 
	private JLabel message; 
	private JComboBox<String> emplacementCapteur; 
	private JLabel lblEmplacement; 
	private JComboBox<String> etageCapteur; 
	private JLabel lblEtage; 
	private JComboBox<String> residenceCapteur; 
	private JLabel lblResidence; 
	private JSlider sliderTemperature;
	private JLabel lblTemperatureCapteur; 
	private JLabel objectNumberTitleLabel;
	protected JLabel objectNumberLabel; 
	private JLabel  lblnbNonConfig;
	//	private JComboBox<String> macCapteur; 
	//	private JLabel lblMacCapteur; 	

	protected JLabel errorSelection; 	


	// TODO supprimer les lignes inutiles 

	//Initialization of the JComboBox with a model
	private DefaultComboBoxModel<String> modelTypeCapteur;
	private DefaultComboBoxModel<String> modelEtatCapteur; 
	private DefaultComboBoxModel<String> modelMinCapteur;
	private DefaultComboBoxModel<String> modelResidence; 
	private DefaultComboBoxModel<String> modelEtage; 
	private DefaultComboBoxModel<String> modelEmplacement; 

	//	protected static List<List<String>> listObject; 
	//	protected static Object[][] data; 
	//	protected static JTable objectTable;
	//	protected JScrollPane scrollPane;
	//	protected JLabel errorSelection; 


	private static JTable objectTable; 
	private List<List<String>> listSensorsDetails = new ArrayList<List<String>>(); 
	List<List<String>> listAllCapteurs = new ArrayList<List<String>>(); 
	private JScrollPane scrollPane;

	/**
	 * Initialize the contents of the frame.
	 */
	public ObjectConfiguration(GuiController controller) {

		this.controller = controller; 

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		labTitre = new JLabel("Vous pouvez configurer l'objet que vous avez s\u00E9lectionn\u00E9");
		labTitre.setFont(new Font("Cambria Math", Font.BOLD, 16));
		labTitre.setBounds(511, 103, 500, 28);
		this.add(labTitre);

		// We get the list of captor types
		modelTypeCapteur = new DefaultComboBoxModel<String>(controller.readReferentiels());	
		typeCapteur = new JComboBox<String>();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setModel(modelTypeCapteur);
		typeCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(1).toString());
		typeCapteur.setBounds(791, 243, 265, 42);
		this.add(typeCapteur);


		//TODO Faire apparaitre tableau à gauche 
		lblTypeCapteur = new JLabel("Type :");
		lblTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTypeCapteur.setBounds(701, 250, 152, 28);
		this.add(lblTypeCapteur);

		lblMinCapteur = new JLabel("Min : ");
		lblMinCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMinCapteur.setBounds(701, 329, 152, 28);
		this.add(lblMinCapteur);	


		listSensorsDetails = controller.listSensors();
		configListObject();
		//		makeFieldsAppear();


		//	modelMinCapteur = new DefaultComboBoxModel<String>(controller.readResidences());
		minCapteur = new JTextField();
		minCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		//		minCapteur.setModel(modelMinCapteur);
		//		minCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		minCapteur.setBounds(791, 330, 265, 42);
		this.add(minCapteur);

		lblMaxCapteur = new JLabel("Max : ");
		lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMaxCapteur.setBounds(701, 395, 152, 28);
		//	this.add(lblMaxCapteur);	


		//	modelMaxCapteur = new DefaultComboBoxModel<String>(controller.readResidences());
		maxCapteur = new JTextField();
		maxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		//		minCapteur.setModel(modelMinCapteur);
		//		minCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		maxCapteur.setBounds(791, 400, 265, 42);
		//	this.add(maxCapteur);

		confirmation = new JCheckBox("Je confirme vouloir configurer cet objet.");
		confirmation.setBackground(new Color(95, 158, 160));
		confirmation.setFont(new Font("Cambria Math", Font.BOLD, 16));
		confirmation.setBounds(512, 692, 350, 25);
		confirmation.addActionListener(this);
		this.add(confirmation);

		valider = new JButton("Valider");
		valider.setFont(new Font("Cambria Math", Font.BOLD, 16));
		valider.setBounds(555, 747, 165, 42);
		valider.setEnabled(false);
		valider.addActionListener(this);
		this.add(valider);

		message = new JLabel();
		message.setFont(new Font("Cambria Math", Font.BOLD, 16));
		message.setBounds(389, 802, 559, 16);
		message.setEnabled(false);
		this.add(message);

		objectNumberTitleLabel = new JLabel("Non configurés restants : ");
		objectNumberTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectNumberTitleLabel.setBounds(50, 140, 300, 30);
		this.add(objectNumberTitleLabel);

		//		objectNumberLabel = new JLabel("");
		//		objectNumberLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		//		objectNumberLabel.setBounds(300, 145, 300, 30);
		//		objectNumberLabel.setText(Integer.toString(controller.nbObjectNonConfigured())); 
		//		this.add(objectNumberLabel);

		returnButton = new JButton("Retour");
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.setBounds(56, 40, 98, 48);
		returnButton.addActionListener(this);
		this.add(returnButton);

	}



	/**
	 * This method make fields appear considering the type of captor selected
	 */

	//	public void makeFieldsAppear() {
	//		System.out.println("Testhella");
	//		for(List<String> line : listSensorsDetails) {
	//		if(line.get(1).equals("Capteur hygrométrique")){
	//			this.add(lblMaxCapteur);
	//			this.add(maxCapteur);
	//			
	//			
	//		}
	//		}
	//	}
	/**
	 * This method is used to display the JTable of objects
	 */

	public void configListObject() {
		String[] header = {"ID_Capteur", "Type_Capteur", "Etat_Capteur", "ID_Emplacement", "Mac_Capteur", "NiveauAlerte_CapteurAppel", "Seuil_CapteurFumee", 
				"Seuil_CapteurHygro", "Debut_CapteurOuverture", "Fin_CapteurOuverture", "Debut_CapteurPresence", "Fin_CapteurPresence", 
				"NiveauAlerte_CapteurRFID", "Min_CapteurTemperature", "Max_CapteurTemperature"};
		Integer x = 0;
		//		String nbNC = "";
		for(List<String> line : listSensorsDetails) {
			if(line.get(3)==null && !line.get(1).equals("Bracelet RFID")) {

				// TODO Jlabel pour faire afficher x comme etant le nb d'objets co
				//			     nbNC =	String.valueOf(x);
				//				lblnbNonConfig = new JLabel(nbNC);
				//				lblnbNonConfig.setFont(new Font("Cambria Math", Font.BOLD, 16));
				//				lblnbNonConfig.setBounds(300, 145, 300, 30);
				//				this.add(lblnbNonConfig);
				//				
				x++;
			}
		}
		System.out.println("Nombre d'objets dans la liste "+ x);
		if(x>0) { 
			// If the result is not empty, we can fill our table
			Integer y = listSensorsDetails.get(0).size();
			data = new Object[x][y]; 
			Integer lineNumber = 0;
			Integer columnNumber = 0;
			for(List<String> line : listSensorsDetails) {
				if(!(line.get(3)==null && !line.get(1).equals("Bracelet RFID"))) {
					System.out.println("numero de colonne "+ columnNumber);
					continue;


					// TODO Put "-" to the columns we don't need for each type  5 6 8 9 10 11 12 13 14
				}


				for(String column : line) {
					if (line.get(1).equals("Capteur hygrométrique")) {
						if((columnNumber == 5 ) || (columnNumber == 6 )) {
							System.out.println(lineNumber +"  "+ columnNumber);
							data[lineNumber][columnNumber]="-";;
							columnNumber++;
							continue;
						}
					}
					data[lineNumber][columnNumber]=column;
					columnNumber++;


				}
				for(int i =0; i<=14; i++) {
					System.out.println(" test "+  data[lineNumber][i]);

				}

				columnNumber=0;
				lineNumber++;
			}
			objectTable = new JTable(data, header);

		}
		else {
			// if the result is empty, the table will be empty
			objectTable = new JTable();
		}

		// We cancel the user selection of a table case.
		objectTable.setDefaultEditor(Object.class, null);
		//Hide columns that I don't need

		objectTable.getColumnModel().getColumn(2).setMinWidth(0);
		objectTable.getColumnModel().getColumn(2).setMaxWidth(0);			
		objectTable.getColumnModel().getColumn(3).setMinWidth(0);
		objectTable.getColumnModel().getColumn(3).setMaxWidth(0);	 
		objectTable.getColumnModel().getColumn(8).setMinWidth(0);
		objectTable.getColumnModel().getColumn(8).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(9).setMinWidth(0);
		objectTable.getColumnModel().getColumn(9).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(10).setMinWidth(0);
		objectTable.getColumnModel().getColumn(10).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(11).setMinWidth(0);
		objectTable.getColumnModel().getColumn(11).setMaxWidth(0);





		if(scrollPane!=null)
			remove(scrollPane);

		scrollPane = new JScrollPane(objectTable);
		scrollPane.setBounds(50, 225, 638, 427);
		add(scrollPane, BorderLayout.CENTER);		
	}


	/**
	 * Method use to get the values of the selected row. It will be use especially in the ObjectModification class
	 * @return
	 */
	protected static List<String> getRowUpdate(){
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
	private List<String> gestionUpdateConfig() {
		List<String> listValues = new ArrayList<String>(); 
		listValues.add(ObjectGestion.getRowUpdate().get(0).toString());
		listValues.add(typeCapteur.getSelectedItem().toString()); 
		if(etatCapteur.getSelectedItem().toString().contentEquals("Enable"))
			listValues.add("1"); 
		else 
			listValues.add("0"); 
		listValues.add(residenceCapteur.getSelectedItem().toString());
		//		listValues.add(emplacementCapteur.getSelectedItem().toString()); 
		listValues.add(etageCapteur.getSelectedItem().toString()); 
		//		listValues.add(macCapteur.getSelectedItem().toString()); 
		return listValues; 

	}

	public void actionPerformed(ActionEvent event) {

		if(event.getSource().equals(returnButton)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
			return;
		}

		//		if(event.getSource().equals(valider)){
		//			clearlabel();
		//			listObject = controller.addObject(detectorList.getSelectedItem().toString());
		//			objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
		//			this.remove(scrollPane);
		//			gestionListObject();
		//			this.add(scrollPane, BorderLayout.CENTER);
		//			controller.getGui().revalidate();
		//			controller.getGui().repaint();
		//			return;
		//		}

		if(event.getSource().equals(confirmation)) {
			if(confirmation.isSelected()) 
				valider.setEnabled(true);

			else 
				valider.setEnabled(false);
			valider.repaint();
		}

		if(event.getSource().equals(valider) && confirmation.isSelected()) {
			boolean test = controller.update(gestionUpdateConfig()); 
			if(test) {
				message.setText("L'object a été configuré avec succès !"); 
				message.setForeground(Color.WHITE);
				message.setEnabled(true);
			}
			else {
				message.setText("Une erreur est survenue. L'object n'a pas pu être configuré.");
				message.setForeground(Color.RED);
				message.setEnabled(true);

			}
		}

	}}
