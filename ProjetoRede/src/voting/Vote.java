package voting;

import java.io.Serializable;

public class Vote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpf;
	private String option;
	
	public Vote(String cpf, String option) {
		this.cpf = cpf;
		this.option = option;
	}
	
	public String getCpf() {
		return cpf;
	}
	public String getOption() {
		return option;
	}
	
	
}
