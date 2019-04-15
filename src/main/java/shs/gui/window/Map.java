package shs.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import shs.gui.GuiController;

public class Map extends JPanel implements ActionListener{
	
	private BufferedImage image; 
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
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(12, 15, 90, 44);
		btnRetour.setFont(new Font("Cambria Math", Font.BOLD, 16));
		btnRetour.addActionListener(this);
		this.add(btnRetour);
		
		table = new JTable();
		table.setBounds(12, 99, 438, 527);
		table.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(table);
		
		comboStage = new JComboBox();
		comboStage.setModel(new DefaultComboBoxModel(new String[] {"Etage 1", "Etage 2"}));
		comboStage.setBounds(108, 661, 204, 44);
		comboStage.setFont(new Font("Cambria Math", Font.BOLD, 16));
		this.add(comboStage);
		
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnRetour)) {
			this.controller.getGui().setBounds(100, 100, 1400, 900);
			controller.getGui().changeWindow(WindowList.MENU);
		}
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
	}
}
