package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PersonalizedDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    protected JPanel contentPanel = new JPanel();
    protected JPanel header;
    protected JLabel imageContainer;
    protected JLabel title;
    protected JTextArea textContent;
    protected JScrollPane scrollPane;
    protected JPanel buttonPane;
    protected JButton closeButton;

    
    public PersonalizedDialog(JFrame frame, Image image, String titleText) {
        super(frame);
        setComponents(image, titleText);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(frame);
        setTitle(titleText);
        setVisible(true);
    }

    private void setComponents(Image image, String titleText) {
        setBounds(100, 100, 450, 300);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());

        header = new JPanel();
        header.setLayout(new BorderLayout());
        contentPanel.add(header, BorderLayout.NORTH);
       

        title = new JLabel(titleText.toUpperCase());
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.NORTH);
        
        imageContainer = new JLabel(new ImageIcon(resizeImage(image, 40)));
        imageContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
        header.add(imageContainer, BorderLayout.CENTER);

        textContent = new JTextArea(5, 20);
        textContent.setLineWrap(true);
        textContent.setWrapStyleWord(true);
        textContent.setEditable(false);
        textContent.setFocusable(false);
        textContent.setMargin(new Insets(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(textContent);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        buttonPane = new JPanel();
        closeButton = new JButton("Fechar");
        closeButton.setActionCommand("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPane.add(closeButton);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }
    
    private Image resizeImage(Image originalImage, int maxHeight) {
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        if (originalHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) ((double) originalWidth * (maxHeight / (double) originalHeight));
        }
        
        return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
    
    protected void setTextContent(String content) {
    	textContent.setText(content);
    	textContent.repaint();
    }
}
