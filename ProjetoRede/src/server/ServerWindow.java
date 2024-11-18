package server;

import java.awt.EventQueue;

import view.ApplicationWindow;

public class ServerWindow extends ApplicationWindow {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow frame = new ServerWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerWindow() {
		super();
		setTitle("Servidor de Eleição");
		new ServerWindowController(ServerWindow.this);
	}
}
