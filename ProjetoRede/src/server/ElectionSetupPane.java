package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ElectionSetupPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton startElectionButton;
	private JPanel formPane;
	private JPanel optionsPane;
	private JTextField questionField;
	private ArrayList<JTextField> optionsFields;
	private int totalOptions = 4;
	
	public ElectionSetupPane() {
		optionsFields = new ArrayList<>();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		setLayout(new BorderLayout(0, 0));
		
		formPane = new JPanel();
		formPane.setLayout(new BoxLayout(formPane, BoxLayout.Y_AXIS));
		formPane.setBorder(new EmptyBorder(10, 30, 10, 30));
		
		JLabel questionLabel = new JLabel("Pergunta da eleição: ");
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		questionLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
		questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		questionLabel.setForeground(new Color(60, 63, 65));
		questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPane.add(questionLabel);
		
		questionField = new JTextField();
		questionField.setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), (int) getMinimumSize().getHeight()));
		formPane.add(questionField);
		questionField.setColumns(10);
		
		optionsPane = new JPanel();
		optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.Y_AXIS));
		JLabel instructionsLabel = new JLabel("Insira as opções de voto abaixo: ");
		instructionsLabel.setBorder(new EmptyBorder(20, 5, 5, 5));
		instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPane.add(instructionsLabel);
		
		for(int i = 1; i <= 4; i++) {
			addOption(i);
		}
		
		JScrollPane optionsScroll = new JScrollPane(optionsPane);
		optionsScroll.setBorder(new EmptyBorder(5, 5, 5, 5));
		formPane.add(optionsScroll);
		
		JButton addOptionButton = new JButton("Adiciona Opção");
		addOptionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addOptionButton.setBackground(new Color(70, 130, 180));
		addOptionButton.setForeground(Color.WHITE);
		addOptionButton.setFocusPainted(false);
		addOptionButton.setMinimumSize(new Dimension((int) getMaximumSize().getWidth(), (int) getMinimumSize().getHeight()));
		addOptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);		
		
		formPane.add(addOptionButton);
		this.add(formPane);
		
		this.startElectionButton = new JButton("Começar Eleição!");
		this.add(startElectionButton, BorderLayout.SOUTH);
		startElectionButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		startElectionButton.setBackground(new Color(70, 130, 180));
		startElectionButton.setForeground(Color.WHITE);
        startElectionButton.setFocusPainted(false);
        
		addOptionButton.addActionListener((e) -> {
			totalOptions++;
			addOption(totalOptions);
		});
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getStartElectionButton() {
		return startElectionButton;
	}

	public JPanel getFormPane() {
		return formPane;
	}

	public JTextField getQuestionField() {
		return questionField;
	}
	
	public int getTotalOptions() {
		return totalOptions;
	}

	public void setTotalOptions(int totalOptions) {
		this.totalOptions = totalOptions;
	}

	public ArrayList<String> getOptions(){
		ArrayList<String> options = new ArrayList<>();
		for(JTextField optionField : optionsFields) {
			options.add(optionField.getText());
		}
		return options;
	}
	
	public void addOption(int optionNumber) {
		JLabel optionLabel = new JLabel("Opção " + optionNumber);
		optionLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
		optionsPane.add(optionLabel);
		JTextField optionField = new JTextField();
		optionField.setColumns(10);
		optionField.setMaximumSize(new Dimension((int) optionField.getMaximumSize().getWidth(), (int) optionField.getMinimumSize().getHeight()));
		optionsFields.add(optionField);
		optionsPane.add(optionField);
		optionsPane.repaint();
		optionsPane.revalidate();
	}
}
