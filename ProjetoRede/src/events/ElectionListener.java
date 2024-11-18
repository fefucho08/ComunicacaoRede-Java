package events;

import java.util.Map;

import voting.Election;

public interface ElectionListener {
	public void onVotesChanged(ElectionEvent e, Map<String, Integer> votes);
	public void onElectionRecieved(ElectionEvent e, Election election);
	public void serverMessageRecieved(ElectionEvent e, String serverMessage);
}
