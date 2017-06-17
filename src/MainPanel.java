

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main panel where all the buttons and text filed are
 * @author Kokos
 *
 */
public class MainPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	private Changer changer;
	private JButton chooseButton, startButton;
	private JFileChooser fc;
	private JPanel fcPanel, startPanel, checkBoxPanel;
	private JTextField fcText, extensionTF, firstBytesTF, secondBytesTF;
	private JCheckBox checkBox, checkBoxReq;
	private File startDirectory;
	private boolean error = false;
	private int[] results;
	
	public MainPanel(Changer changer){
		setLayout(new GridLayout(5, 1, 10, 20));
		addFileChooser();
		addFileChooserPanel();
		addExtensionTF();
		addFirstBytesTF();
		addSecondBytesTF();
		addStartPanel();
		this.changer = changer;
	}
	
	private void addFileChooserPanel(){
		fcPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		addFileChooserText();
		addChooseButton();
		add(fcPanel);
	}
	
	private void addFileChooserText(){
		fcText = new JTextField();
		fc.setMaximumSize(new Dimension(30, 20));
		fcText.setToolTipText("Podaj œcie¿kê dostêpu.");
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
				    startDirectory = fc.getSelectedFile();
				    fcText.setText(startDirectory.getPath());
				    fcText.setForeground(Color.BLACK);
				    fcText.setToolTipText("Podaj œcie¿kê dostêpu.");
				}
			}
	
		});
		chooseButton.setBackground(Color.DARK_GRAY);
		chooseButton.setForeground(Color.LIGHT_GRAY);
		chooseButton.setBorderPainted(false);
		chooseButton.setFocusPainted(false);
		chooseButton.setRolloverEnabled(false);
		chooseButton.setFont(new Font("Helvetica",1,20));
		fcPanel.add(chooseButton);
	}
	private void addExtensionTF(){
		extensionTF = new JTextField("Podaj rozszerzenie");
		extensionTF.setToolTipText("Podaj rozszerzenie plików w których ma nast¹piæ zamiana(np. txt, jpg).");
		add(extensionTF);
	}
	
	private void addFirstBytesTF(){
		firstBytesTF = new JTextField("Pierwszy ci¹g bajtów");
		firstBytesTF.setToolTipText("Podaj ci¹g bajtów, które maj¹ byæ zmienione(szesnastkowo)");
		add(firstBytesTF);
	}
	
	private void addSecondBytesTF(){
		secondBytesTF = new JTextField("Drugi ci¹g bajtów");
		secondBytesTF.setToolTipText("Podaj ci¹g bajtów, w który ma byæ zamieniony szukany ci¹g(szesnastkowo)");
		add(secondBytesTF);
	}
	private void addCheckBoxPanel(){
	    checkBoxPanel = new JPanel();
	    addCheckBoxCopy();
	    addCheckBoxReq();
	    startPanel.add(checkBoxPanel);
	}
	
	private void addCheckBoxCopy(){
	    checkBox = new JCheckBox("Twórz kopiê", true);
	    checkBox.setToolTipText("Tworzone bêd¹ pliki o nazwie pliku Ÿród³owego z dodanym Copy");
	    checkBoxPanel.add(checkBox);
	}

   private void addCheckBoxReq(){
        checkBoxReq = new JCheckBox("Szukaj w podfolderach", true);
        checkBoxReq.setToolTipText("Pliki z podanym rozszerzeniem bêd¹ szukane równie¿ w podfolderach");
        checkBoxPanel.add(checkBoxReq);
    }
	
	private void addStartButton(){

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
			    error = false;
    		    isPathSet();
    		    checkExtension();
    		    checkBytes(1);
    		    checkBytes(2);
    		    if(!error){
                    if(showAlert() == 0){
                        try {
                            results = changer.findAndChange(startDirectory, extensionTF.getText(), firstBytesTF.getText(), secondBytesTF.getText(), checkBox.isSelected(), checkBoxReq.isSelected());
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(new JFrame(),
                                    e1.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        showSucces();
                    }
    		    }
    		}
		});
		startButton.setBackground(Color.DARK_GRAY);
		startButton.setForeground(Color.LIGHT_GRAY);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setRolloverEnabled(false);
		startButton.setFont(new Font("Helvetica",1,20));
		startPanel.add(startButton);
	}
	private void showSucces(){
	    int files = results[0];
	    int changes = results[1];
	    
	    JOptionPane.showMessageDialog(new JFrame(),
	            "Znalezionych plików: " + files + "\nDokonanych zmian: " + changes);
	}
	
	private int showAlert(){
        Object[] options = {"Tak", "Anuluj"};
        return  JOptionPane.showOptionDialog(new JFrame(),
                "Podczas tej operacji pliki mog¹ zostaæ zniszczone "
                + "\n\tJesteœ pewien?",
                "Ostrze¿enie",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
	}
	
	private boolean isPathSet(){
	    File file = new File(fcText.getText());
	    if (!file.isDirectory() || !file.exists())
	    {
	        fcText.setForeground(Color.RED);
	        fcText.setToolTipText("<html><b><font color = red>Folder nie istnieje!</font></b></html>");
	        error = true;
	        return false;  
	    }
        fcText.setForeground(Color.BLACK);
        fcText.setToolTipText("Podaj œcie¿kê dostêpu"); 
	     return true;
	}
	private void addStartPanel(){
		startPanel = new JPanel(new GridLayout(1, 2));
		addCheckBoxPanel();
		addStartButton();
		add(startPanel);
	}
	
	private boolean checkExtension(){
	    String temp = extensionTF.getText();
	    if(temp.isEmpty()){
	        error = true;
	        return false;
	    }
	    temp = temp.replaceAll("\\s+","");
	    char[] chars = temp.toCharArray();
	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            extensionTF.setForeground(Color.RED);
	            extensionTF.setToolTipText("<html><b><font color = red>Rozszerzenie mo¿e zawieraæ tylko litery</font></b></html>");
	            error = true;
	            return false;
	        }
	    }
	    extensionTF.setForeground(Color.BLACK);
	    return true;
	}
	
	private boolean checkBytes(int i){
	    JTextField bytesTF;
	    switch(i){
	    case 1:
	        bytesTF = firstBytesTF;
	        break;
	    case 2:
	        bytesTF = secondBytesTF;
	        break;
	    default:
	        bytesTF = null;
	        break;
	    }
        char[] from = bytesTF.getText().replaceAll("\\s+","").toUpperCase().toCharArray();
        if(from.length == 0){
            bytesTF.setForeground(Color.RED);
            bytesTF.setText("Uzupelnij pole");
            error = true;
            return false;
        }
        
        for(char c : from){
            if(c < '0' || c > '9')
            {
                if(c < 'A' || c > 'F'){
                    bytesTF.setForeground(Color.RED);
                    bytesTF.setToolTipText("<html><b><font color = red>Pole to musi zawieraæ cyfry i znaki [A-F]</font></b></html>");
                    error = true;
                    return false;
                }
            }
        }
        if(from.length % 2 != 0){
            bytesTF.setForeground(Color.RED);
            bytesTF.setToolTipText("<html><b><font color = red>Iloœæ znaków musi byæ parzysta</font></b></html>");
           error = true;
           return false;
        }
        bytesTF.setToolTipText("Podaj ci¹g bajtów, które maj¹ byæ zmienione(szesnastkowo)");
        bytesTF.setForeground(Color.BLACK);
        return true;
	}
	
}
