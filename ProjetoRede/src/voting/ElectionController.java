package voting;

import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import events.ElectionEvent;
import events.ElectionListener;

public class ElectionController {
	private Election election;
	private Map<String, Integer> votes;
	private ArrayList<Vote> voters;
	private ArrayList<ElectionListener> voteListeners;
	
	public ElectionController(Election election) {
		this.election = election;
		votes = new HashMap<>();
		voters = new ArrayList<>();
		voteListeners = new ArrayList<>();
		
		
		for(String option : election.getOptions()) {
			votes.put(option, 0); // STARTING EACH OPTION WITH 0 VOTES
		}
	}
	
	public boolean addVote(Vote vote) {
		if(!voters.contains(vote) && checkCPF(vote.getCpf())) {
			votes.put(vote.getOption(), votes.getOrDefault(vote.getOption(), 0) + 1);
			voters.add(vote);
			notifyVotesChanged();
			return true;
		}
		return false;
	}
	
	public void generateElectionReport() {
		try {
			String fileName = election.getQuestion().replaceAll("[^a-zA-Z]", "") + ".txt";
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			
			if(votes != null) {
				writer.println("*INFORMACAO DA ELEIÇÃO*\n");
				int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
				writer.println("Vencedor: " + getWinner());
				writer.println("Votos totais: " + totalVotes);
				writer.println("Votos por candidato:");
				votes.forEach((option, numVotes) -> writer.println(option + " teve " + numVotes + " votos."));
			}
			
			if(voters != null) {
				writer.println("\n*INFORMAÇÃO DOS VOTANTES*\n");
				for(Vote vote: voters) {
					writer.println(vote.getCpf() + " - " + vote.getOption());
				}
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void notifyVotesChanged() {
		ElectionEvent event = new ElectionEvent(this);
		for(ElectionListener listener : voteListeners) {
			listener.onVotesChanged(event, votes);
		}
	}
	
	private boolean checkCPF(String cpf) {
		boolean validCPF = true;
		for(Vote vote : voters) {
			if(vote.getCpf().equals(cpf)) {
				validCPF = false;
				break;
			}
		}
		return validCPF;
	}
	
	public static Map<String, Integer> orderByVote(Map<String, Integer> votes) {
		ArrayList<Entry<String, Integer>> voteList = new ArrayList<>(votes.entrySet());
		
		voteList.sort(Entry.<String, Integer>comparingByValue().reversed());
		
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		for(Entry<String, Integer> vote : voteList) {
			sortedMap.put(vote.getKey(), vote.getValue());
		}
		
		return sortedMap;
	}
	
	public Map<String, Integer> getVotes() {
		return votes;
	}

	public void addVoteListener(ElectionListener listener) {
		if(!voteListeners.contains(listener)) {
			voteListeners.add(listener);
		}
	}
	
	public Election getElection() {
		return election;
	}
	
	public String getWinner() {
		if(votes != null) {
			return Collections.max(votes.entrySet(), Map.Entry.comparingByValue()).getKey();
		}
		else return null;
	}
}
