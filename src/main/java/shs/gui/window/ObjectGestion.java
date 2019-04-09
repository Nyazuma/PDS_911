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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import shs.gui.GuiController;

public class ObjectGestion extends JPanel implements ActionListener {

	private GuiController controller;

	protected static JTable objectTable;
	protected JScrollPane scrollPane;
	protected JLabel addTitleLabel;
	protected JButton addButton; 
	protected JButton deleteButton; 
	protected JButton updateButton; 
	protected JComboBox<?> detectorList;
	protected JLabel objectNumberTitleLabel;
	protected JLabel objectNumberLabel; 
	protected JLabel manageObjectLabel;
	protected JLabel detectorTypeTitleLabel;                           
	protected JLabel errorSelection; 


	// Raw Table we get by the controller
	protected static List<List<String>> listObject; 
	// Table used to display our elements after implementation
	protected static Object[][] data; 

	/**
	 * Initialize the contents of the frame.
	 */
	public ObjectGestion(GuiController controller) {

		this.controller = controller;

		setBackground(new Color(95, 158, 160));
		setFont(new Font("Cambria Math", Font.BOLD, 17));
		setLayout(null);

		objectNumberTitleLabel = new JLabel("Nombre d'objects connect\u00E9s : ");
		objectNumberTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectNumberTitleLabel.setBounds(48, 184, 246, 28);
		this.add(objectNumberTitleLabel);

		addTitleLabel = new JLabel("Ajouter un nouvel objet :");
		addTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		addTitleLabel.setBounds(843, 219, 236, 16);
		this.add(addTitleLabel);

		objectNumberLabel = new JLabel("");
		objectNumberLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		objectNumberLabel.setBounds(317, 184, 64, 28);
		objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
		this.add(objectNumberLabel);

		manageObjectLabel = new JLabel("Gestion des nouveaux objets");
		manageObjectLabel.setFont(new Font("Cambria Math", Font.BOLD, 20));
		manageObjectLabel.setBounds(525, 25, 291, 41);
		this.add(manageObjectLabel);
		
		detectorList = new JComboBox();
		detectorList.setFont(new Font("Cambria Math", Font.BOLD, 16));
		// We get the list of Captor types
		detectorList.setModel(new DefaultComboBoxModel(controller.readReferentiels()));
		detectorList.setBounds(969, 279, 309, 33);
		this.add(detectorList);

		detectorTypeTitleLabel = new JLabel("*Type du capteur : ");
		detectorTypeTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		detectorTypeTitleLabel.setBounds(800, 279, 169, 33);
		this.add(detectorTypeTitleLabel);

		listObject = controller.readObjects();
		gestionListObject();
		this.add(scrollPane, BorderLayout.CENTER);

		addButton = new JButton("Ajouter");
		addButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		addButton.setBounds(970, 438, 162, 41);
		addButton.addActionListener(this);
		this.add(addButton);

		deleteButton = new JButton("Supprimer");
		deleteButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		deleteButton.setBounds(81, 671, 162, 41);
		deleteButton.addActionListener(this);
		this.add(deleteButton);

		updateButton = new JButton("Modifier");
		updateButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		updateButton.setBounds(317, 671, 162, 41);
		updateButton.addActionListener(this);
		this.add(updateButton);

		errorSelection = new JLabel("Veuillez sélectionner une ligne dans la table");
		errorSelection.setForeground(new Color(128, 0, 0));
		errorSelection.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		errorSelection.setBounds(120, 755, 307, 16);

	}


	public void gestionListObject() {
		String[] header = {"ID_capteur", "Type du capteur",  "Etat capteur", "Residence", "Zone", "Piece"}; 
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
			listUpdate.add(data[ligne][i].toString()); 
		}
		return listUpdate;
	}


	public void actionPerformed(ActionEvent event) {

		if(event.getSource().equals(addButton)){
			clearlabel();
			listObject = controller.addObject(detectorList.getSelectedItem().toString());
			objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
			this.remove(scrollPane);
			gestionListObject();
			this.add(scrollPane, BorderLayout.CENTER);
			controller.getGui().revalidate();
			controller.getGui().repaint();
			return;
		}

		if(event.getSource().equals(deleteButton)) {
			clearlabel();
			if(objectTable.getSelectedRow()!= -1) {
				int ligne = objectTable.getSelectedRow(); 
				listObject = controller.delete(data[ligne][0].toString());
				objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
				this.remove(scrollPane);
				gestionListObject();
				this.add(scrollPane, BorderLayout.CENTER);
				controller.getGui().revalidate();
				controller.getGui().repaint();
			}
			else {
				this.add(errorSelection); 
				controller.getGui().revalidate();
				controller.getGui().repaint();
			}
			return;
		}

		if(event.getSource().equals(updateButton)) {
			if(objectTable.getSelectedRow()!= -1) {
				controller.getGui().changeWindow(WindowList.OBJECTMODIFICATION);
			}
			else {
				this.add(errorSelection);
				controller.getGui().revalidate();
				controller.getGui().repaint(); 
			}
		}

	}

}
