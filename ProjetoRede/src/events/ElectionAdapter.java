package events;

import java.util.Map;

import voting.Election;

public class ElectionAdapter implements ElectionListener {

	@Override
	public void onVotesChanged(ElectionEvent e, Map<String, Integer> votes) {
	}

	@Override
	public void onElectionRecieved(ElectionEvent e, Election election) {
	}

	@Override
	public void serverMessageRecieved(ElectionEvent e, String serverMessage) {
	}

}
