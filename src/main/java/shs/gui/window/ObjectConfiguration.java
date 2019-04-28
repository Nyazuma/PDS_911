package shs.gui.window;

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
//	private JComboBox<String> macCapteur; 
//	private JLabel lblMacCapteur; 	
	
	
	// TODO supprimer les lignes inutiles 

	//Initialization of the JComboBox with a model
	private DefaultComboBoxModel<String> modelTypeCapteur;
	private DefaultComboBoxModel<String> modelEtatCapteur; 
	private DefaultComboBoxModel<String> modelMinCapteur;
	private DefaultComboBoxModel<String> modelResidence; 
	private DefaultComboBoxModel<String> modelEtage; 
	private DefaultComboBoxModel<String> modelEmplacement; 

	protected static List<List<String>> listObject; 
	protected static Object[][] data; 
	protected static JTable objectTable;
	protected JScrollPane scrollPane;
	protected JLabel errorSelection; 

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
		labTitre.setBounds(511, 103, 447, 28);
		this.add(labTitre);

		// We get the list of captor types
		modelTypeCapteur = new DefaultComboBoxModel<String>(controller.readReferentiels());	
		typeCapteur = new JComboBox<String>();
		typeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		typeCapteur.setModel(modelTypeCapteur);
		typeCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(1).toString());
		typeCapteur.setBounds(771, 243, 265, 42);
		this.add(typeCapteur);

		
		//TODO Faire apparaitre tableau à gauche 
		
		lblTypeCapteur = new JLabel("Type :");
		lblTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTypeCapteur.setBounds(681, 250, 152, 28);
		this.add(lblTypeCapteur);

//		modelEtatCapteur = new DefaultComboBoxModel<String>(new String[] {"Enable", "Disable"});
//		etatCapteur = new JComboBox<String>();
//		etatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		etatCapteur.setModel(modelEtatCapteur);
//		etatCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(2).toString());
//		etatCapteur.setBounds(371, 322, 265, 42);
//		this.add(etatCapteur);
//
//
//		lblEtatCapteur = new JLabel("Etat du capteur : ");
//		lblEtatCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblEtatCapteur.setBounds(181, 329, 152, 28);
//		this.add(lblEtatCapteur);

		
		
		lblMinCapteur = new JLabel("Min : ");
		lblMinCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMinCapteur.setBounds(681, 329, 152, 28);
		this.add(lblMinCapteur);	
		
		
	//	modelMinCapteur = new DefaultComboBoxModel<String>(controller.readResidences());
	    minCapteur = new JTextField();
		minCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		minCapteur.setModel(modelMinCapteur);
//		minCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		minCapteur.setBounds(771, 400, 265, 42);
		this.add(minCapteur);
		
		lblMaxCapteur = new JLabel("Max : ");
		lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblMaxCapteur.setBounds(681, 395, 152, 28);
		this.add(lblMaxCapteur);	

		
	//	modelMaxCapteur = new DefaultComboBoxModel<String>(controller.readResidences());
		maxCapteur = new JTextField();
		maxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		minCapteur.setModel(modelMinCapteur);
//		minCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(3).toString());
		maxCapteur.setBounds(771, 330, 265, 42);
		this.add(maxCapteur);
		
		
//		
//		lblTemperatureCapteur = new JLabel("Temperature : ");
//		lblTemperatureCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblTemperatureCapteur.setBounds(681, 490, 152, 28);
//		this.add(lblTemperatureCapteur);
//		
//		// TODO Not working yet: 
//		
//		sliderTemperature = new JSlider(JSlider.HORIZONTAL, 100, 100, 100);  
//		sliderTemperature.setMinorTickSpacing(2);  
//		sliderTemperature.setMajorTickSpacing(10);  
//		sliderTemperature.setPaintTicks(true);  
//		sliderTemperature.setPaintLabels(true); 
//		this.add(sliderTemperature);
//		location_t.setPreferredSize(new Dimension(50, 22));
		
//		lblResidence = new JLabel("Résidence :"); 
//		lblResidence.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblResidence.setBounds(181, 403, 152, 28);
//		this.add(lblResidence); 

//		// We get the list of zones
//		modelZone = new DefaultComboBoxModel<String>(controller.readZones());
//		zoneCapteur = new JComboBox<String>(); 
//		zoneCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		zoneCapteur.setModel(modelZone);
//		zoneCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(4).toString());
//		zoneCapteur.setBounds(571, 477, 265, 42);
//		this.add(zoneCapteur); 
//
//		// We get the list of pieces
//		modelPiece = new DefaultComboBoxModel<String>(controller.readPieces());
//		pieceCapteur = new JComboBox<String>(); 
//		pieceCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		pieceCapteur.setModel(modelPiece);
//		pieceCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(5).toString());
//		pieceCapteur.setBounds(571, 556, 265, 42);
//		this.add(pieceCapteur); 

//		lblEtage = new JLabel("Etage :");
//		lblEtage.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblEtage.setBounds(181, 484, 152, 28);
//		this.add(lblEtage); 
//
//
//		modelEtage = new DefaultComboBoxModel<String>(new String[] {"RDC", "1er etage"});
//		etageCapteur = new JComboBox<String>(); 
//		etageCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		etageCapteur.setModel(modelEtage);
//		if(ObjectGestion.getRowUpdate().get(4)!=null)
//			etageCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(4).toString());
//		etageCapteur.setBounds(371, 477, 265, 42);
//		this.add(etageCapteur);

//		lblEmplacement = new JLabel("Emplacement:");
//		lblEmplacement.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblEmplacement.setBounds(181, 563, 152, 28);
//		this.add(lblEmplacement); 
		
//		modelEmplacement = new DefaultComboBoxModel<String>(controller.readEmplacements());
//		emplacementCapteur = new JComboBox<String>(); 
//		emplacementCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		emplacementCapteur.setModel(modelEmplacement);
//		if(ObjectGestion.getRowUpdate().get(5)!=null)
//			emplacementCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(5).toString());
//		emplacementCapteur.setBounds(371, 563, 265, 42);
//		this.add(emplacementCapteur);

		// List of MAC adresses !! Compléter avec la création de l'adresse
		

//		macCapteur = new JComboBox<String>();
//		macCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		macCapteur.setModel(modelMacCapteur);
//		macCapteur.setSelectedItem(ObjectGestion.getRowUpdate().get(4).toString());
//		macCapteur.setBounds(571, 600, 265, 42);
//		this.add(macCapteur);


//		lblMacCapteur = new JLabel("Mac du capteur : ");
//		lblMacCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
//		lblMacCapteur.setBounds(381, 700, 152, 28);
//		this.add(lblMacCapteur);

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

		JLabel label = new JLabel("------------------------------------------------------------------------------------------------------------------------------------------");
		label.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label.setBounds(315, 179, 740, 16);
		this.add(label);

		JLabel label_1 = new JLabel("-------------------------------------------------------------------------------------------------------------------------------------------");
		label_1.setFont(new Font("Cambria Math", Font.BOLD, 16));
		label_1.setBounds(315, 632, 740, 16);
		this.add(label_1);

		returnButton = new JButton("Retour");
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.setBounds(56, 40, 98, 48);
		returnButton.addActionListener(this);
		this.add(returnButton);

	}

	//TODO Not working yet
	public void configurationListObject() {
		String[] header = {"ID_capteur", "Etat capteur",  "Min", "Max", "Mac"}; 
		Integer x = listObject.size(); 
		Integer y;
		if(x>0) { 
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
			objectTable.getColumnModel().getColumn(7).setMinWidth(0);
			objectTable.getColumnModel().getColumn(7).setMaxWidth(0);
			
			
		}
		else {
			// if the result is empty, the table will be empty
			objectTable = new JTable();
		}


		scrollPane = new JScrollPane(objectTable);
		scrollPane.setBounds(12, 225, 727, 413);

	}

	// Remove all the information label we could have displayed at this point
	private void clearlabel() {
		this.remove(errorSelection);

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


	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(returnButton)) {
			controller.getGui().changeWindow(WindowList.OBJECTGESTION);

		}
		if(event.getSource().equals(confirmation)) {
			if(confirmation.isSelected()) 
				valider.setEnabled(true);

			else 
				valider.setEnabled(false);
			valider.repaint();
		}

//		if(event.getSource().equals(valider) && confirmation.isSelected()) {
//	//		boolean test = controller.update(gestionUpdate()); 
//			if(test) {
//				message.setText("L'object a été modifié avec succès !"); 
//				message.setForeground(Color.WHITE);
//				message.setEnabled(true);
//			}
//			else {
//				message.setText("Une erreur est survenue. L'object n'a pas pu être modifié.");
//				message.setForeground(Color.RED);
//				message.setEnabled(true);
//
//			}
//		}


	}

//	
//
//
//		public void gestionListObject() {
//			String[] header = {"ID_capteur", "Type du capteur",  "Etat capteur", "Residence", "Etage", "Emplacement","Mac", "ID_Emplacement"}; 
//			Integer x = listObject.size(); 
//			Integer y;
//			if(x>0) { 
//				// If the result is not empty, we could fill our table
//				y = listObject.get(0).size();
//				data = new Object[x][y]; 
//				Integer i = 0;
//				Integer j = 0;
//				for(List<String> line : listObject) {
//					for(String column : line) {
//						if(j!=2)
//							data[i][j]=column;
//						else {
//							// Status
//							if(column.equals("1")) 
//								data[i][2] = "Enable"; 
//							else 
//								data[i][2] = "Disable"; 
//						}
//						j++;
//					}
//					j=0;
//					i++;
//				}
//
//				objectTable = new JTable(data, header);
//				// We cancel the user selection of a table case.
//				objectTable.setDefaultEditor(Object.class, null);
//				//Hide ID column
//				objectTable.getColumnModel().getColumn(0).setMinWidth(0);
//				objectTable.getColumnModel().getColumn(0).setMaxWidth(0);
//				objectTable.getColumnModel().getColumn(7).setMinWidth(0);
//				objectTable.getColumnModel().getColumn(7).setMaxWidth(0);
//				
//				
//			}
//			else {
//				// if the result is empty, the table will be empty
//				objectTable = new JTable();
//			}
//
//
//			scrollPane = new JScrollPane(objectTable);
//			scrollPane.setBounds(12, 225, 727, 413);
//
//		}
//
//		// Remove all the information label we could have displayed at this point
//		private void clearlabel() {
//			this.remove(errorSelection);
//
//		}
//
//		/**
//		 * Method use to get the values of the selected row. It will be use especially in the ObjectModification class
//		 * @return
//		 */
//		protected static List<String> getRowUpdate(){
//			int ligne = objectTable.getSelectedRow(); 
//			List<String> listUpdate = new ArrayList<String>(); 
//			for(int i = 0; i<data[ligne].length; i++) {
//				if(data[ligne][i]!=null)
//					listUpdate.add(data[ligne][i].toString()); 
//				else
//					listUpdate.add(null);
//			}
//			return listUpdate;
//		}
//
//
//		public void actionPerformed(ActionEvent event) {
//
//			if(event.getSource().equals(returnButton)) {
//				this.controller.getGui().setBounds(100, 100, 1400, 900);
//				controller.getGui().changeWindow(WindowList.MENU);
//				return;
//			}
//			
//			if(event.getSource().equals(addButton)){
//				clearlabel();
//				listObject = controller.addObject(detectorList.getSelectedItem().toString());
//				objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
//				this.remove(scrollPane);
//				gestionListObject();
//				this.add(scrollPane, BorderLayout.CENTER);
//				controller.getGui().revalidate();
//				controller.getGui().repaint();
//				return;
//			}
//
//			if(event.getSource().equals(deleteButton)) {
//				clearlabel();
//				if(objectTable.getSelectedRow()!= -1) {
//					int ligne = objectTable.getSelectedRow(); 
//					listObject = controller.delete(data[ligne][0].toString());
//					objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
//					this.remove(scrollPane);
//					gestionListObject();
//					this.add(scrollPane, BorderLayout.CENTER);
//					controller.getGui().revalidate();
//					controller.getGui().repaint();
//				}
//				else {
//					this.add(errorSelection); 
//					controller.getGui().revalidate();
//					controller.getGui().repaint();
//				}
//				return;
//			}
//
//			if(event.getSource().equals(updateButton)) {
//				if(objectTable.getSelectedRow()!= -1) {
//					controller.getGui().changeWindow(WindowList.OBJECTMODIFICATION);
//				}
//				else {
//					this.add(errorSelection);
//					controller.getGui().revalidate();
//					controller.getGui().repaint(); 
//				}
//			}
//			
//			if(event.getSource().equals(configButton)) {
//				if(objectTable.getSelectedRow()!= -1) {
//					controller.getGui().changeWindow(WindowList.OBJECTCONFIGURATION);
//				}
//				else {
//					this.add(errorSelection);
//					controller.getGui().revalidate();
//					controller.getGui().repaint(); 
//				}
//			}
//
//		}
//
//	}

}


