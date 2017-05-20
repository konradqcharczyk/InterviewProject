import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * Program on set directory have to find files with given extension and search thought them and find
 * bytes equal to byes of 1st row and replace it with bytes from 2nd row.
 * @date 20.05.2017
 * @author Kokos
 *
 */
public class QBS {
	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {	
			@Override
			public void run() {
				JFrame frame = new MainFrame();
				
				frame.setTitle("QBS");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});
		
	}
}
