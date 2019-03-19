package shs.gui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shs.gui.GuiController;

public class ObjectGestion extends JPanel implements ActionListener {

	private GuiController controller;

	protected JTable objectTable;
	protected JLabel addTitleLabel;
	protected JButton addButton; 
	protected JComboBox<?> detectorList;
	protected JLabel objectNumberTitleLabel;
	protected JLabel objectNumberLabel; 
	protected JLabel manageObjectLabel;
	protected JLabel detectorTypeTitleLabel;


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
		detectorList.setModel(new DefaultComboBoxModel(new String[] {"Capteur de temp\u00E9rature", "Capteur de fum\u00E9e", "Capteur ouverture ", "Capteur hygrom\u00E9trique", "Capteur de pr\u00E9sence", "Capteur appel ", "Bracelet"}));
		detectorList.setBounds(969, 279, 309, 33);
		this.add(detectorList);

		detectorTypeTitleLabel = new JLabel("*Type du capteur : ");
		detectorTypeTitleLabel.setFont(new Font("Cambria Math", Font.BOLD, 16));
		detectorTypeTitleLabel.setBounds(800, 279, 169, 33);
		this.add(detectorTypeTitleLabel);


		String[] header = {"ID_capteur", "Type du capteur",  "Etat capteur"}; 
		List<List<String>> listObject = controller.listObjet();
		Integer x = listObject.size();
		Integer y;
		if(x<=0) { 
			// If the result is not empty, we could fill our table
			y = listObject.get(0).size();
			Object[][] data = new Object[x][y]; 
			Integer i =0;
			Integer j = 0;
			for(List<String> line : listObject) {
				for(String column : line) {
					data[i][j]=column;
					j++;
				}
				i++;
			}

			while(!listObject.isEmpty()) {
				List<String> tamponList = listObject.get(1); 
				while(!tamponList.isEmpty()) {


					tamponList.remove(0); 
				}
				listObject.remove(0); 
			}
			objectTable = new JTable(data, header);
		}
		else {
			// if the result is empty, the table will be empty
			objectTable = new JTable();
		}
				
		objectTable.setBounds(12, 225, 727, 413);

		this.add(objectTable.getTableHeader(), BorderLayout.NORTH);
		this.add(objectTable, BorderLayout.CENTER); 

		addButton = new JButton("Ajouter");
		addButton.setFont(new Font("Cambria Math", Font.BOLD, 16));
		addButton.setBounds(970, 438, 162, 41);
		addButton.addActionListener(this);
		this.add(addButton);
	}

	public void actionPerformed(ActionEvent event) {

		if(event.getSource().equals(addButton)){
			controller.addObject(detectorList.getSelectedItem().toString());
			objectNumberLabel.setText(Integer.toString(controller.nbObject())); 
		}

	}
}
