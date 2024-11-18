package voting;

import java.io.Serializable;
import java.util.*;

public class Election implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private ArrayList<String> options;
	
	
	public Election(String question, ArrayList<String> options) {
		this.question = question;
		this.options = options;
		
		
	}
	
	
	
	public String getQuestion() {
		return question;
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}
	
}
