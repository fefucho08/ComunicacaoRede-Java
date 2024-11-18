package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VotePane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel questionLabel;
	private JLabel cpfLabel;
	private JTextField cpfField;
	private JScrollPane optionsScroll;
	private ButtonGroup optionsGroup;
	private JButton voteBtn;
	
	public VotePane(String question, ArrayList<String> options) {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(10, 10, 10, 10));

        questionLabel = new JLabel(question);
        questionLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.Y_AXIS));
        
        cpfLabel = new JLabel("Digite seu CPF: ");
        cpfField = new JTextField();
        cpfField.setMaximumSize(new Dimension((int) cpfField.getMaximumSize().getWidth(), (int) cpfField.getMinimumSize().getHeight()));
        inputPane.add(cpfLabel);
        inputPane.add(cpfField);
        
        JLabel voteLabel = new JLabel("Vote em uma das opções abaixo: ");
        inputPane.add(voteLabel);
        optionsGroup = new ButtonGroup();
        
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            optionsGroup.add(radioButton);
            inputPane.add(radioButton);
        }

        optionsScroll = new JScrollPane(inputPane);
        optionsScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(optionsScroll, BorderLayout.CENTER);
        
        voteBtn = new JButton("Votar!");
        voteBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        voteBtn.setBackground(new Color(70, 130, 180));
        voteBtn.setForeground(Color.WHITE);
        voteBtn.setFocusPainted(false);
        add(voteBtn, BorderLayout.SOUTH);
	}
	
	public String getSelectedOption() {
        for (Enumeration<AbstractButton> buttons = optionsGroup.getElements(); buttons.hasMoreElements();) {
            JRadioButton button = (JRadioButton) buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
	}
	
	public JLabel getQuestionLabel() {
		return questionLabel;
	}

	public ButtonGroup getOptionsGroup() {
		return optionsGroup;
	}

	public JButton getVoteBtn() {
		return voteBtn;
	}

	public JTextField getCpfField() {
		return cpfField;
	}
	
}
