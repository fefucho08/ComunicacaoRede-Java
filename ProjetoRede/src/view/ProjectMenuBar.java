package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ProjectMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu helpMenu;
	private JMenuItem helpHelp;
	private JMenuItem helpAbout;
	
	public ProjectMenuBar() {
		
		helpMenu = new JMenu("Ajuda");
		this.add(helpMenu);
		
		helpHelp = new JMenuItem("Ajuda");
		helpMenu.add(helpHelp);
		
		helpAbout = new JMenuItem("Sobre");
		helpMenu.add(helpAbout);
		
		repaint();
	}

	public JMenu getHelpMenu() {
		return helpMenu;
	}

	public JMenuItem getHelpHelp() {
		return helpHelp;
	}

	public JMenuItem getHelpAbout() {
		return helpAbout;
	}
	
	
	
}
