
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;


/**
 *
 * @author Hugo Sacilotto
 */
public class ChatPanel extends JPanel {
    
    public static final int RECIEVED = 0, SENT = 1;
    
    // GUI fields
    private JButton btnSend;
    private JTextField txfMessage;
    private JPanel scrollingPanel;
    
    // Your chat name
    private String clientName;
    
    // The name of the client to chat with
    private String chatClientName;
    
    /**
     * Constructor
     * @param clientName
     * @param chatClientName 
     */
    public ChatPanel(String clientName, String chatClientName) {
        
        this.clientName = clientName;
        this.chatClientName = chatClientName;
        
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(g);
        
        add(Tools.title(chatClientName), gbc);
        
        // Main panel
        scrollingPanel = new JPanel();
        scrollingPanel.setLayout(new BoxLayout(scrollingPanel, BoxLayout.Y_AXIS));
        scrollingPanel.setBackground(Tools.DARK_2);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(
            scrollingPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setPreferredSize(new Dimension(600, 500));
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
        
        // Message field
        txfMessage = Tools.inputField();
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(txfMessage, gbc);
        
        // Send button
        btnSend = Tools.button("Send");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        add(btnSend, gbc);
        
        setBackground(Tools.DARK_2);
    }
    
    public JButton getSendButton() {
        return this.btnSend;
    }
    public JTextField getMessageField() {
        return this.txfMessage;
    }
    
    /**
     * Adds a message to the chat panel.
     * @param message
     * @param type 
     */
    public void addMessage(String message, int type) {
        switch(type) {
            case RECIEVED:
                scrollingPanel.add(message(message, chatClientName, FlowLayout.LEADING));
                break;
            case SENT:
                scrollingPanel.add(message(message, clientName, FlowLayout.TRAILING));
                break;
        }
        
        revalidate();
        updateUI();
    }
    
    /**
     * Returns a styled JPanel in the form of a message.
     * @param textMessage
     * @param sender
     * @param flowAlign
     * @return 
     */
    public JPanel message(String textMessage, String sender, int flowAlign) {
        int charLimit = 50;
        int width = 250;

        String msgString = "<html><body style='width: " + width + "px;'>" + textMessage + "</html>";

        JLabel lblMessage = new JLabel(msgString);
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setOpaque(true);
        lblMessage.setBackground(Tools.MEDIUM_2);
        lblMessage.setBorder(new EmptyBorder(8, 15, 8, 15));

        JLabel lblSender = new JLabel(sender);
        lblSender.setForeground(Tools.LIGHT);
        lblSender.setBorder(new EmptyBorder(3, 0, 0, 15));

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(g);

        panel.add(lblMessage, gbc);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblSender, gbc);
        
        JPanel p = new JPanel(new FlowLayout(flowAlign));
        p.setOpaque(false);
        
        p.add(panel);
        
        return p;
    }
}
