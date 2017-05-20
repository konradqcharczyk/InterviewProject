import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Contener in main frame.
 * @author Kokos
 *
 */
public class Cont extends JPanel{

	private static final long serialVersionUID = 1L;

	public Cont(){
		 setPreferredSize(new Dimension(300, 300));
		 setMinimumSize(new Dimension(300, 300));
	     setLayout(new BorderLayout(30,30));
	     add(new JPanel(), BorderLayout.NORTH);
	     add(new JPanel(), BorderLayout.SOUTH);
	     add(new JPanel(), BorderLayout.EAST);
	     add(new JPanel(), BorderLayout.WEST);
	     MainPanel mainPanel = new MainPanel();
	     add(mainPanel, BorderLayout.CENTER);
	}
}
