package view;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import control.FileController;

public class AboutDialog extends PersonalizedDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton moodleButton;
	
	public AboutDialog(JFrame frame) {
		super(frame, new ImageIcon(AboutDialog.class.getResource("/images/JavaLogo.png")).getImage(), "Sobre");
		File aboutFile = new File("src/textFiles/About.txt");
		String aboutFileContent = FileController.getFileContent(aboutFile);
		this.setTextContent(aboutFileContent);
		
		moodleButton = new JButton("Abrir Moodle");
		
		moodleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openMoodle();
			}
		});
		
		this.buttonPane.add(moodleButton, FlowLayout.LEFT);
	}
	
	private void openMoodle() {
		try {
			Desktop.getDesktop().browse(new URI("https://moodle.ggte.unicamp.br/mod/assign/view.php?id=545659"));
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
}
