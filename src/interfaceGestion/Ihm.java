package interfaceGestion;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ihm {

	protected JFrame frame = new JFrame(); 
	
	Ihm(JPanel panel){
		frame.setTitle("Interface de gestion "); 
		frame.setSize(1300, 800); 
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setVisible(true); 
	}
	

}
