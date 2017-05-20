

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main panel where all the buttons and text filed are
 * @author Kokos
 *
 */
public class MainPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	private JButton chooseButton, startButton;
	private JFileChooser fc;
	private JPanel fcPanel, startPanel;
	private JTextField fcText, extensionTF, firstBytesTF, secondBytesTF;
	private File startDirection;
	
	public MainPanel(){
		setLayout(new GridLayout(5, 1, 10, 20));
		addFileChooser();
		addFileChooserPanel();
		addExtensionTF();
		addFirstBytesTF();
		addSecondBytesTF();
		addStartPanel();
	}
	
	/**
	 * print out files with extension in this directory
	 */
	private void test(){
		File[] matchingFiles = startDirection.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(extensionTF.getText());
			}
		});
		
		for(File c : matchingFiles)
			System.out.println(c);
	}
	
	private void test2() throws IOException{
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try{
			in = new FileInputStream("C:\\Users\\Kokos\\Desktop\\3215424.jpg");
			out = new FileOutputStream("C:\\Users\\Kokos\\Desktop\\3215424dsadsa.jpg");
			int c;
	
			while( (c = in.read()) != -1 )
					out.write(c);
		}
		finally {
            if (in != null) 
                in.close();
            if (out != null)
                out.close();
		}
		
	}
	
	private void addFileChooserPanel(){
		fcPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		addFileChooserText();
		addChooseButton();
		add(fcPanel);
	}
	
	private void addFileChooserText(){
		fcText = new JTextField();
		fcPanel.add(fcText);
	}
	private void addFileChooser(){
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	private void addChooseButton(){
		chooseButton = new JButton("Wybierz folder");
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(new JFrame("Wybierz"));
				if (returnVal == JFileChooser.APPROVE_OPTION){ 
				    startDirection = fc.getSelectedFile();
				    fcText.setText(startDirection.getPath());
				}
			}
	
		});
		fcPanel.add(chooseButton);
	}
	private void addExtensionTF(){
		extensionTF = new JTextField("Podaj rozszerzenie");
		add(extensionTF);
	}
	
	private void addFirstBytesTF(){
		firstBytesTF = new JTextField("Pierwszy ci¹g bajtów");
		add(firstBytesTF);
	}
	
	private void addSecondBytesTF(){
		secondBytesTF = new JTextField("Drugi ci¹g bajtów");
		add(secondBytesTF);
	}
	
	private void addEmptyLabel(){
		startPanel.add(new JLabel(""));
	}
	
	private void addStartButton(){
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				test();
				try {
					test2();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		startPanel.add(startButton);
	}
	
	private void addStartPanel(){
		startPanel = new JPanel(new GridLayout(1, 2));
		addEmptyLabel();
		addStartButton();
		add(startPanel);
	}
}
