package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import events.ElectionAdapter;
import events.ElectionEvent;
import view.AboutDialog;
import view.HelpDialog;
import voting.Election;

public class ClientWindowController extends ElectionAdapter implements ActionListener {
	
	private ConnectPane connectPane;
	private VotePane votePane;
	private ClientWindow window;
	private Client client;
	private Map<Object, Runnable> actionMap = new HashMap<>();
	
	public ClientWindowController(ClientWindow window) {
		this.window = window;
		connectPane = new ConnectPane();
		window.setCurrentPage(connectPane);
		
		connectPane.getConnectToServerBtn().addActionListener(this);
		actionMap.put(connectPane.getConnectToServerBtn(), this::connectToServer);
		
		window.getProjectMenuBar().getHelpHelp().addActionListener(this);
		actionMap.put(window.getProjectMenuBar().getHelpHelp(), this::showHelpDialog);
		
		window.getProjectMenuBar().getHelpAbout().addActionListener(this);
		actionMap.put(window.getProjectMenuBar().getHelpAbout(), this::showAboutDialog);
		
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(client != null) {
					if(client.isConnected()) {
						client.shutdown();
					}
				}
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Runnable action = actionMap.get(e.getSource());
		if(action != null) {
			action.run();
		}
	}
	
	@Override
	public void onElectionRecieved(ElectionEvent e, Election election) {
		setElectionContent(election);
	}
	
	@Override
	public void serverMessageRecieved(ElectionEvent e, String serverMessage) {
		JOptionPane.showMessageDialog(window, serverMessage);
		window.setCurrentPage(connectPane);
	}
	
	private void setElectionContent(Election election) {
		votePane = new VotePane(election.getQuestion(), election.getOptions());
		votePane.getVoteBtn().addActionListener(this);
		actionMap.put(votePane.getVoteBtn(), this::vote);
		
		window.setCurrentPage(votePane);
	}
	
	private void connectToServer() {
		client = new Client();
		client.addElectionListener(this);
		Thread clientThread = new Thread(client);
		clientThread.start();
	}
	
	private void showHelpDialog() {
		new HelpDialog(window, "HelpClient.txt");
	}
	
	private void showAboutDialog() {
		new AboutDialog(window);
	}
	
	private void vote() {
		String cpf = votePane.getCpfField().getText();
		String option = votePane.getSelectedOption();
		if(option != null) {
			if(isCpfValid(cpf)) {
				client.sendVote(cpf, option);
			}
			else {
				JOptionPane.showMessageDialog(window, "Insira um CPF válido!");
			}
		}
		else {
			JOptionPane.showMessageDialog(window, "Vote em alguma das opções!");
		}
	}
	
	private boolean isCpfValid(String cpf) {
		if(cpf.isBlank()) {
			return false;
		}
		
		if(cpf.length() != 11) {
			return false;
		}
		
		for(int i = 0; i < cpf.length(); i++) {
			if(!Character.isDigit(cpf.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
}
