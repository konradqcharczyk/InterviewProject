import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Contener in main frame.
 * @author Kokos
 *
 */
public class Cont extends JPanel{

	private static final long serialVersionUID = 1L;

	public Cont(){
	     Changer changer = new Changer();
		 setPreferredSize(new Dimension(300, 300));
		 setMinimumSize(new Dimension(300, 300));
	     setLayout(new BorderLayout(30,30));

	     addNameLabel();
	     add(new JPanel(), BorderLayout.SOUTH);
	     add(new JPanel(), BorderLayout.EAST);
	     add(new JPanel(), BorderLayout.WEST);
	     MainPanel mainPanel = new MainPanel(changer);
	     add(mainPanel, BorderLayout.CENTER);
	}
	private void addNameLabel(){
	    JLabel name = new JLabel("Zamiana bajtów", SwingConstants.CENTER);
	    name.setOpaque(true);
	    name.setForeground(Color.GRAY);
	    name.setFont(new Font("Helvetica",1,40));
        add(name, BorderLayout.NORTH);
	}
}
