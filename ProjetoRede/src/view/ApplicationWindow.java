package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ApplicationWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel currentPage;
	private JPanel footer;
	private JLabel footerLabel;
	private ProjectMenuBar menuBar;
	
	/**
	 * Create the frame.
	 */
	public ApplicationWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ApplicationWindow.class.getResource("/images/LogoFT.png")));
        setBounds(100, 100, 500, 400);
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.5),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.5));
        
        contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		footer = new JPanel();
		footer.setBackground(Color.LIGHT_GRAY);
		contentPane.add(footer, BorderLayout.SOUTH);
		
		footerLabel = new JLabel();
		footer.add(footerLabel);
		
		menuBar = new ProjectMenuBar();
		setJMenuBar(menuBar);
		setContentPane(contentPane);
	}
	
	private void changePageStatus() {
		footerLabel.setText(currentPage.getClass().getSimpleName());
		footerLabel.repaint();
	}
	
	public void setCurrentPage(JPanel page) {
		if(currentPage != null) {
			contentPane.remove(currentPage);
		}
		currentPage = page;
		contentPane.add(page, BorderLayout.CENTER);
		changePageStatus();
		contentPane.revalidate();
		contentPane.repaint();
	}

	public ProjectMenuBar getProjectMenuBar() {
		return menuBar;
	}
}
