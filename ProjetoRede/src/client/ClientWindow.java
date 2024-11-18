package client;

import java.awt.EventQueue;

import view.ApplicationWindow;

public class ClientWindow extends ApplicationWindow {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ClientWindow() {
		super();
		setTitle("Sistema de votação");
        new ClientWindowController(this);
	}
}
