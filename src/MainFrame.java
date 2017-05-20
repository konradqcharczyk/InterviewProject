
import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * Main frame of programme 
 * @author Kokos
 *
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Cont container;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	public MainFrame(){
		setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(600, 400));
		container = new Cont();
		setResizable(true);
		setLocationRelativeTo(null);
		add(container);
	}

}

