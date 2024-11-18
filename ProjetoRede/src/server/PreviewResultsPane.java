package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import voting.ElectionController;

public class PreviewResultsPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel questionLabel;
	private JTable electionPreviewTable;
	private JButton closeElectionBtn;
	
	public PreviewResultsPane(String question, Map<String, Integer> votes) {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));
		questionLabel = new JLabel(question);
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);
        
        electionPreviewTable = new JTable() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
        };
        electionPreviewTable.setDragEnabled(false);
        electionPreviewTable.getTableHeader().setReorderingAllowed(false);
        electionPreviewTable.getTableHeader().setResizingAllowed(false);
        electionPreviewTable.setRowSelectionAllowed(false);
        electionPreviewTable.setColumnSelectionAllowed(false);
        electionPreviewTable.setCellSelectionEnabled(false);
        electionPreviewTable.setFocusable(false);
        changeResults(votes);
        
        JScrollPane tableScroll = new JScrollPane(electionPreviewTable);
        tableScroll.setBorder(new EmptyBorder(40, 10, 40, 10));
        add(tableScroll, BorderLayout.CENTER);
        
        closeElectionBtn = new JButton("Encerrar eleição");
        closeElectionBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        closeElectionBtn.setBackground(new Color(70, 130, 180));
        closeElectionBtn.setForeground(Color.WHITE);
        closeElectionBtn.setFocusPainted(false);
        add(closeElectionBtn, BorderLayout.SOUTH);
	}
	
	public void changeResults(Map<String, Integer> votes) {
		int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
		Map<String, Integer> orderedVotes = ElectionController.orderByVote(votes);
		
		DefaultTableModel model = new DefaultTableModel(new Object[] {"Opção", "Qtd de votos", "Porcentagem"}, 0);
		
		for(Map.Entry<String, Integer> vote : orderedVotes.entrySet()) {
			String option = vote.getKey();
			Integer voteCount = vote.getValue();
			String percentage = totalVotes > 0 ? String.format("%.2f%%", (voteCount * 100.0) / totalVotes) : "0.00%";
			model.addRow(new Object[] {option, voteCount, percentage});
		}
		
		electionPreviewTable.setModel(model);
		
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(70, 130, 180));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < electionPreviewTable.getColumnModel().getColumnCount(); i++) {
        	electionPreviewTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
		electionPreviewTable.revalidate();
		electionPreviewTable.repaint();
	}

	public JLabel getQuestionLabel() {
		return questionLabel;
	}

	public JTable getElectionPreviewTable() {
		return electionPreviewTable;
	}

	public JButton getCloseElectionBtn() {
		return closeElectionBtn;
	}

}
