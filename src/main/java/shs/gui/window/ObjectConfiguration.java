package shs.gui.window;
import javax.swing.text.*;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
/*from   w  w w.jav  a2s .  co  m*/
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.awt.*;
import java.util.*;
  

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	protected JTextField minCapteur; 
	protected JLabel lblMinCapteur;
	protected JTextField maxCapteur; 
	protected JLabel lblMaxCapteur;
	private JCheckBox confirmation; 
	private JButton valider; 
	private JButton refresh; 
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
	//private JLabel  lblnbNonConfig;	

	protected JLabel errorSelection; 	
    private JFormattedTextField formattedDate;
    protected JTextField normalDate;
    private JButton buttonDate; 
	private JDateChooser dateFrom;
	private JDateChooser dateTo;
	private JFormattedTextField minDate;
	private JFormattedTextField maxDate;

	//Initialization of the JComboBox with a model
	private DefaultComboBoxModel<String> modelTypeCapteur;
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

 
		lblTypeCapteur = new JLabel("Type :");
		lblTypeCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
		lblTypeCapteur.setBounds(701, 250, 152, 28);
		this.add(lblTypeCapteur);

	   	    
		listSensorsDetails = controller.listSensors();
		configListObject();
		makeFieldsAppear();

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
		refresh = new JButton("Refresh");
		refresh.setFont(new Font("Cambria Math", Font.BOLD, 16));
		refresh.setBounds(755, 747, 165, 42);
		refresh.setEnabled(false);
		refresh.addActionListener(this);
		this.add(refresh);
		
		message = new JLabel();
		message.setFont(new Font("Cambria Math", Font.BOLD, 16));
		message.setBounds(389, 802, 559, 16);
		message.setEnabled(false);
		this.add(message);

		objectNumberTitleLabel = new JLabel("Non configurés restants : ");
		objectNumberTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectNumberTitleLabel.setBounds(50, 140, 300, 30);
		this.add(objectNumberTitleLabel);

		returnButton = new JButton("Retour");
		returnButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		returnButton.setBounds(56, 40, 98, 48);
		returnButton.addActionListener(this);
		this.add(returnButton);
		
	    Date datedebut = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
	    String dateString = formatter.format(datedebut);
	    minDate = new JFormattedTextField(createFormatter("##:##:##"));
	    minDate.setColumns(20);
	    minDate.setText(dateString);
	    minDate.setBounds(791, 330, 96, 22);
	    
	    
	    Date datefin = new Date();
	    SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
	    String dateString2 = formatter.format(datefin);
	    maxDate = new JFormattedTextField(createFormatter("##:##:##"));
	    maxDate.setColumns(20);
	    maxDate.setText(dateString);
	    maxDate.setBounds(789, 401, 96, 22);
	    

	  //this.setLayout(new BorderLayout());
	//   this.add(new JLabel("Entrer une date sous le format HH:MM:SS ");
	 //   this.add(formatText);

	}
	
	


	
	

		  private MaskFormatter createFormatter(String s) {
		    MaskFormatter formatter = null;
		    try {
		      formatter = new MaskFormatter(s);
		    } catch (java.text.ParseException exc) {
		      System.err.println("Mauvais format de date: " + exc.getMessage());
		    }
		    return formatter;
		  }

		

	/**
	 * This method make fields appear considering the type of captor selected
	 */

		public void makeFieldsAppear() {	
			
			minCapteur = new JTextField();
			minCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
			minCapteur.setBounds(791, 330, 265, 42);
			
			maxCapteur = new JTextField();
			maxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
			maxCapteur.setBounds(791, 400, 265, 42);

			for(List<String> line : listSensorsDetails) {
			if(ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur hygrométrique")){
				
				lblMaxCapteur = new JLabel("Seuil: ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 395, 152, 28);	
				this.add(lblMaxCapteur);	
				this.add(maxCapteur);
		
				
			}
            if(ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de fumée")){
				
				lblMaxCapteur = new JLabel("Seuil: ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 395, 152, 28);	
				this.add(lblMaxCapteur);	
				this.add(maxCapteur);
		
				
			}
			
			if(ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur ouverture")){
				lblMaxCapteur = new JLabel("Debut : ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 329, 152, 28);		
				lblMinCapteur = new JLabel("Fin : ");
				lblMinCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMinCapteur.setBounds(701, 395, 152, 28);
				
			    Date datedebut = new Date();
			    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
			    String dateString = formatter.format(datedebut);
			    minDate = new JFormattedTextField(createFormatter("##:##:##"));
			    minDate.setColumns(20);
			    minDate.setText(dateString);
			    minDate.setBounds(791, 330, 96, 22);
			    
			    this.add(minDate);
			    Date datefin = new Date();
			    SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
			    String dateString2 = formatter.format(datefin);
			    maxDate = new JFormattedTextField(createFormatter("##:##:##"));
			    maxDate.setColumns(20);
			    maxDate.setText(dateString);
			    maxDate.setBounds(789, 401, 96, 22);
				
				
				 this.add(maxDate);
				
				this.add(lblMaxCapteur);	
				this.add(lblMinCapteur);
	
				
			}

			if(ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de température")){
				lblMaxCapteur = new JLabel("Max : ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 395, 152, 28);	
				lblMinCapteur = new JLabel("Min : ");
				lblMinCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMinCapteur.setBounds(701, 329, 152, 28);

				this.add(lblMaxCapteur);	
				this.add(maxCapteur);
				this.add(lblMinCapteur);
				this.add(minCapteur);
				
			}

			if(ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de présence")){
				lblMaxCapteur = new JLabel("Debut : ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 329, 152, 28);		
				lblMinCapteur = new JLabel("Fin : ");
				lblMinCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMinCapteur.setBounds(701, 395, 152, 28);
				
			    Date datedebut = new Date();
			    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
			    String dateString = formatter.format(datedebut);
			    minDate = new JFormattedTextField(createFormatter("##:##:##"));
			    minDate.setColumns(20);
			    minDate.setText(dateString);
			    minDate.setBounds(791, 330, 96, 22);
			    
			    this.add(minDate);
			    Date datefin = new Date();
			    SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
			    String dateString2 = formatter.format(datefin);
			    maxDate = new JFormattedTextField(createFormatter("##:##:##"));
			    maxDate.setColumns(20);
			    maxDate.setText(dateString);
			    maxDate.setBounds(789, 401, 96, 22);
				
				
				 this.add(maxDate);
				
				this.add(lblMaxCapteur);	
				this.add(lblMinCapteur);
				
			}
			if((ObjectGestion.getRowUpdate().get(1).toString().equals("Bracelet RFID")) || ((ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur appel")))){
				lblMaxCapteur = new JLabel("Niveau: ");
				lblMaxCapteur.setFont(new Font("Cambria Math", Font.BOLD, 16));
				lblMaxCapteur.setBounds(701, 395, 152, 28);		
				
				this.add(lblMaxCapteur);	
				this.add(maxCapteur);
				
			}

		}}
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
			
				x++;
			}
		}
	//	System.out.println("Nombre d'objets dans la liste "+ x);
		if(x>0) { 
			// If the result is not empty, we can fill our table
			Integer y = listSensorsDetails.get(0).size();
			data = new Object[x][y]; 
			Integer lineNumber = 0;
			Integer columnNumber = 0;
			for(List<String> line : listSensorsDetails) {
				if(!(line.get(3)==null && !line.get(1).equals("Bracelet RFID"))) {
					continue;



				}


				for(String column : line) {
					if (line.get(1).equals("Capteur hygrométrique")) {
						if((columnNumber == 5 ) || (columnNumber == 7 ) || (columnNumber == 8 ) || (columnNumber == 9 ) || (columnNumber == 10 ) || (columnNumber == 11 ) || (columnNumber == 12 ) || (columnNumber == 13 ) || (columnNumber == 14 )) {
		
							data[lineNumber][columnNumber]="   -  ";;
							columnNumber++;
							continue;
							
						}
					}

					
					data[lineNumber][columnNumber]=column;
					columnNumber++;


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
		objectTable.getColumnModel().getColumn(5).setMinWidth(0);
		objectTable.getColumnModel().getColumn(5).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(6).setMinWidth(0);
		objectTable.getColumnModel().getColumn(6).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(7).setMinWidth(0);
		objectTable.getColumnModel().getColumn(7).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(8).setMinWidth(0);
		objectTable.getColumnModel().getColumn(8).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(9).setMinWidth(0);
		objectTable.getColumnModel().getColumn(9).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(10).setMinWidth(0);
		objectTable.getColumnModel().getColumn(10).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(11).setMinWidth(0);
		objectTable.getColumnModel().getColumn(11).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(12).setMinWidth(0);
		objectTable.getColumnModel().getColumn(12).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(13).setMinWidth(0);
		objectTable.getColumnModel().getColumn(13).setMaxWidth(0);
		objectTable.getColumnModel().getColumn(14).setMinWidth(0);
		objectTable.getColumnModel().getColumn(14).setMaxWidth(0);

		




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
		listValues.add(ObjectConfiguration.getRowUpdate().get(0).toString());
		if (!minCapteur.getText().equals("")) {
		listValues.add(minCapteur.getText().toString()); 
		}
		if (!maxCapteur.getText().equals("")) {
		listValues.add(maxCapteur.getText().toString()); 
		}

	else {
		listValues.add(null);
	} 
		return listValues; 

	}

	public void actionPerformed(ActionEvent event) {


		if(event.getSource().equals(returnButton)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.OBJECTGESTION);
			return;
		}


		if(event.getSource().equals(confirmation)) {
			if(confirmation.isSelected()) 
				valider.setEnabled(true);

			else 
			valider.setEnabled(false);
			valider.repaint();

		}
		
		if(event.getSource().equals(valider)) {	
		
			refresh.setEnabled(true);
			refresh.repaint();
			}
		

		if(event.getSource().equals(refresh)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.OBJECTCONFIGURATION);
			return;
		
		}

		
		// TODO Regler pb de validation sans rien entré dans les champs 
		// TODO Rajouter condition: si aucun sans rfid mit, afficher message d'erreur 
		if(event.getSource().equals(valider) && confirmation.isSelected()) {
			
			if ( !(Integer.parseInt(minCapteur.getText()) < Integer.parseInt(maxCapteur.getText()))) {
				message.setText("Votre Maximum doit être supérieur au Minimum");
				message.setForeground(Color.RED);
				message.setEnabled(true);

			}
			else if ( !(Integer.parseInt(minDate.getText()) < Integer.parseInt(maxDate.getText()))) {
				message.setText("Votre Date de debut et de fin ne sont pas dans l'ordre chronologique ");
				message.setForeground(Color.RED);
				message.setEnabled(true);
				

			}else if((ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur hygrométrique") && !maxCapteur.getText().equals("")) || (ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de fumée") && !maxCapteur.getText().equals(""))|| (ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de température") && !maxCapteur.getText().equals("")) || (ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur appel") && !maxCapteur.getText().equals("")) || (ObjectGestion.getRowUpdate().get(1).toString().equals("Bracelet RFID") && !maxCapteur.getText().equals("")) || (ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de température") && !minCapteur.getText().equals("") && !maxCapteur.getText().equals("")) && (Integer.parseInt(minCapteur.getText()) < Integer.parseInt(maxCapteur.getText())) || (ObjectGestion.getRowUpdate().get(1).toString().equals("Capteur de présence") && !minDate.getText().equals("") && !maxDate.getText().equals(""))) {
					message.setText("L'object a été configuré avec succès !"); 
					
					
					String type = ObjectGestion.getRowUpdate().get(1).toString();
					Integer id = Integer.parseInt(ObjectGestion.getRowUpdate().get(0).toString());
					controller.updateNonConfig(type, id, minCapteur, maxCapteur, minDate, maxDate);
					message.setForeground(Color.WHITE);
					
					
				}
			else{
				message.setText("Une erreur est survenue. L'object n'a pas pu être configuré.");
				message.setForeground(Color.RED);
				message.setEnabled(true);	
			}
		}

	}}
