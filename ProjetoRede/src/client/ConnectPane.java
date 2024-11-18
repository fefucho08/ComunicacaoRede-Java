package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ConnectPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton connectToServerBtn;
	private JLabel connectPageTitle;
	private JTextPane connectPageInstructions;
	
	public ConnectPane() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));
        connectPageTitle = new JLabel("Bem-vindo ao sistema de votações", SwingConstants.CENTER);
        connectPageTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        connectPageTitle.setForeground(new Color(60, 63, 65));
        add(connectPageTitle, BorderLayout.NORTH);
        
        
        String instructions = "Para iniciar o sistema, verificar a eleição corrente e computar seu voto, clique no botão abaixo para se conectar ao servidor.";
        connectPageInstructions = new JTextPane();
        connectPageInstructions.setText(instructions);
        connectPageInstructions.setEditable(false);
        connectPageInstructions.setFocusable(false);
        connectPageInstructions.setOpaque(false);
        connectPageInstructions.setMargin(new Insets(80, 20, 20, 20));
        
        StyledDocument doc = connectPageInstructions.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        connectPageInstructions.setFont(new Font("Arial", Font.PLAIN, 16));
        add(connectPageInstructions, BorderLayout.CENTER);

        connectToServerBtn = new JButton("Conectar ao Servidor");
        connectToServerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        connectToServerBtn.setBackground(new Color(70, 130, 180));
        connectToServerBtn.setForeground(Color.WHITE);
        connectToServerBtn.setFocusPainted(false);
        add(connectToServerBtn, BorderLayout.SOUTH);
	}

	public JButton getConnectToServerBtn() {
		return connectToServerBtn;
	}

	public JLabel getConnectPageTitle() {
		return connectPageTitle;
	}

	public JTextPane getConnectPageInstructions() {
		return connectPageInstructions;
	}
	
}
