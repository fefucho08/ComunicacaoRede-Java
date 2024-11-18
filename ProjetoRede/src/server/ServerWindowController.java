package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import events.ElectionAdapter;
import events.ElectionEvent;
import view.AboutDialog;
import view.HelpDialog;
import voting.Election;
import voting.ElectionController;

public class ServerWindowController extends ElectionAdapter implements ActionListener {
	
	private Server server;
	private ServerWindow window;
	private ElectionSetupPane formPanel;
	private PreviewResultsPane resultsPane;
	private Map<Object, Runnable> actionMap = new HashMap<>();
	
	public ServerWindowController(ServerWindow window) {
		this.window = window;
		formPanel = new ElectionSetupPane();
		window.setCurrentPage(formPanel);
		
		formPanel.getStartElectionButton().addActionListener(this);
		actionMap.put(formPanel.getStartElectionButton(), this::startElection);
		
		window.getProjectMenuBar().getHelpHelp().addActionListener(this);
		actionMap.put(window.getProjectMenuBar().getHelpHelp(), this::showHelpDialog);
		
		window.getProjectMenuBar().getHelpAbout().addActionListener(this);
		actionMap.put(window.getProjectMenuBar().getHelpAbout(), this::showAboutDialog);
		
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeElection();
			}
		});
	}
	
	@Override
	public void onVotesChanged(ElectionEvent e, Map<String, Integer> votes) {
		setResults(votes);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Runnable action = actionMap.get(e.getSource());
		if(action != null) {
			action.run();
		}
	}
	
	public void setResults(Map<String, Integer> results) {
		if(resultsPane != null) {
			resultsPane.changeResults(results);
		}
		System.out.println("Someone voted!");
	}
	
	private void startElection() {
		String question = formPanel.getQuestionField().getText();
		
		ArrayList<String> options = new ArrayList<>();
		formPanel.getOptions().forEach((option) -> {
			if(!option.isBlank()) {
				options.add(option);
			}
		});
		
		if(!question.isBlank() && options.size() >= 2) {
			Election election = new Election(question, options);
			ElectionController controller = new ElectionController(election);
			controller.addVoteListener(this);
			
			resultsPane = new PreviewResultsPane(controller.getElection().getQuestion(), controller.getVotes());
			window.setCurrentPage(resultsPane);
			
			resultsPane.getCloseElectionBtn().addActionListener(this);
			actionMap.put(resultsPane.getCloseElectionBtn(), this::closeElection);
			
			this.server = new Server(controller);
			Thread serverThread = new Thread(server);
			serverThread.start();
		}
		else {
			JOptionPane.showMessageDialog(window, "Preencha as informações!");
		}
	}
	
	private void closeElection() {
		if(server != null) {
			server.shutdown();
			server = null;
		}
		formPanel = new ElectionSetupPane();
		window.setCurrentPage(formPanel);
	}
	
	private void showHelpDialog() {
		new HelpDialog(window, "HelpServer.txt");
	}
	
	private void showAboutDialog() {
		new AboutDialog(window);
	}
}
