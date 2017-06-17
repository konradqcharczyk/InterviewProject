
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;


/**
 * Main frame of program 
 * @author Kokos
 *
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Cont container;
	private static final int WIDTH = 650;
	private static final int HEIGHT = 400;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem aboutProgram, contact;
	
	public MainFrame(){
		setSize(WIDTH, HEIGHT);
		ImageIcon icon = new ImageIcon("asstets\\Icon_kk.png");
		setIconImage(icon.getImage());
		setMinimumSize(new Dimension(650, 400));
		container = new Cont();
		setResizable(true);
		setLocationRelativeTo(null);
		addMenu();
		add(container);
	}
	
	   private void addMenu(){
        menuBar = new JMenuBar();
        menu = new JMenu("Pomoc");

        aboutProgram = new JMenuItem("O programie");
        aboutProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutProgramFrame();
                
            }
        });
        menu.add(aboutProgram);
        
        menu.addSeparator();

        contact = new JMenuItem("Kontakt");
        contact.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                contactFrame();
                
            }
        });
        menu.add(contact);
        
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        }
	   
	   private void aboutProgramFrame(){
           JFrame awindow = new JFrame();
           awindow.setSize(380,170);
           awindow.setTitle("O programie");
           String text = "Program ma za zadanie odnalezienie w podanym przez u�ytkowika \nfolderze(i ew. podfolderach) plik�w ze wskazanym rozszerzeniem.\n"
                   + "Nast�pnie w odnalezionych plikach wyszukiwany jest ci�g bajt�w\ni zamieniony na drugi podany ci�g."
                   + " Przeszukiwany folder musi\nistnie�, rozszerzenie musi zawiera� tylko znaki(bez kropki\nna pocz�tku) a ciagi bajt�w"
                   + " zapisane w systemie szesnastkowym\n( [0-9] oraz [A-F] )."
                   + " Program mo�e tworzy� zmienione kopie b�d�\nzamienia� oryginalne pliki.";
           JTextArea textArea = new JTextArea(text, 4, 4);
           textArea.setLineWrap(true);
           awindow.setResizable(false);
           awindow.setLocationRelativeTo(null);
           awindow.add(textArea);
           awindow.setDefaultCloseOperation(1);
           awindow.setVisible(true);
	   }
	   
       private void contactFrame(){
           JFrame awindow = new JFrame();
           awindow.setSize(250,120);
           awindow.setTitle("Kontakt");
           String text = "Stworzone przez: Konrad Kucharczyk\n"
                   + "Adres e-mail: konradqcharczyk@gmail.com";
           JTextArea textArea = new JTextArea(text, 2, 2);
           textArea.setLineWrap(true);
           awindow.setResizable(false);
           awindow.setLocationRelativeTo(null);
           awindow.add(textArea);
           awindow.setDefaultCloseOperation(1);
           awindow.setVisible(true);
       }

}

