
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Hugo Sacilotto
 */
public class SignedInPanel extends JPanel {
    
    private User user;
    private Sidebar sidebar;
    private Client client;
    
    private String currentChatClientID;
    
    private HashMap<String, JLabel> clientLabelMap;
    private HashMap<String, ChatPanel> chatsMap;
    
    private MouseListener m;
    private GridBagLayout g;
    private GridBagConstraints gbc;
    
    private JPanel rightPanel;
    
    private ActionListener sendAction;
    
    /* Constructor */
    public SignedInPanel() {
        
        sidebar = new Sidebar();
        
        clientLabelMap = new HashMap<>();
        chatsMap = new HashMap<>();
        
        g = new GridBagLayout();
        gbc = new GridBagConstraints();
        
        setLayout(g);
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(sidebar = new Sidebar(), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(400, 400));
        rightPanel.setBackground(Tools.DARK_2);
        
        add(rightPanel, gbc);
        
        // Mouse actions
        m = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Tools.MEDIUM_1);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Tools.MEDIUM_2);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                Component b = e.getComponent();
                b.setBackground(Tools.MEDIUM_1);
                
                
                clientLabelMap.entrySet().forEach(c -> {
                    if (c.getValue() == b) {
                        
                        gbc.gridx = 1;
                        gbc.weightx = 1;
                        gbc.fill = GridBagConstraints.BOTH;

                        String clientID = c.getKey();
                        
                        JPanel newPanel = chatsMap.get(clientID);
                        remove(rightPanel);
                        rightPanel = newPanel;
                        add(newPanel, gbc);

                        currentChatClientID = clientID;
                    }
                });
                
                revalidate();
                updateUI();  
            }
        };
        
        sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatsMap.get(currentChatClientID).getMessageField().getText();
                
                if (!message.isBlank()) {
                    // Message will be sent
                    
                    // Clear message field
                    chatsMap.get(currentChatClientID).getMessageField().setText("");
                    writeMessage(message);
                    
                    try {
                        client.sendMessage(currentChatClientID, message);
                    } catch (IOException ex) {}                 
                }
            }
        };
    }
    
    public void writeMessage(String message) {
        ChatPanel p = chatsMap.get(currentChatClientID);
        p.addMessage(message, ChatPanel.SENT);
    }
    
    public void newMessage(String clientID, String message) {
        ChatPanel p = chatsMap.get(clientID);
        p.addMessage(message, ChatPanel.RECIEVED);  
        
    }
    
    /**
     * Adds a new client to chat to.
     * @param clientID
     * @param clientName 
     */
    public void addClient(String clientID, String clientName) {
        
        ChatPanel chatPanel = new ChatPanel(user.getUsername(), clientName);
        chatPanel.getSendButton().addActionListener(sendAction);
        chatPanel.getMessageField().addActionListener(sendAction);
        
        chatsMap.put(clientID, chatPanel);
        
        JLabel label = sideLabel(clientName);
        label.addMouseListener(m);
        clientLabelMap.put(clientID, label);
        
        sidebar.gbc.gridy = clientLabelMap.size() - 1;
        sidebar.nameTag.add(label, sidebar.gbc);
        revalidate();
        updateUI();
    }
    
    /**
     * Sets the user to the logged in user.
     * @param user
     * @throws IOException
     * @throws InterruptedException
     */
    public void setUser(User user, int port) throws IOException, InterruptedException {
        this.user = user;
        this.client = new Client(port, this);
        this.client.login(user);
        sidebar.setUsername(user.getUsername());
    }
 
    /**
     * Returns a styled JLabel which displays the name of a client.
     * @param name
     * @return 
     */
    public JLabel sideLabel(String name) {
        JLabel label = new JLabel(name);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Tools.MEDIUM_2);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        label.setBorder(new EmptyBorder(8, 8, 8, 0));

        return label;
    } 
}


class Sidebar extends JPanel {
    public JPanel nameTag;
    
    private GridBagLayout g;
    public GridBagConstraints gbc;
    
    public Sidebar() {
        setLayout(new BorderLayout());
        
        nameTag = new NameTag();
        g = new GridBagLayout();
        gbc = new GridBagConstraints();
        nameTag.setLayout(g);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 2, 0);
        
        nameTag.setBackground(Color.WHITE);
        
        add(nameTag, BorderLayout.NORTH);
    }
    
    public void setUsername(String username) {
        JPanel namePanel = new JPanel();
        namePanel.add(Tools.smallTitle(username));
        add(namePanel, BorderLayout.SOUTH);
    }
}


class NameTag extends JPanel {
    public NameTag() {
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        setLayout(g);
        setBackground(Color.BLUE);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gbc.insets = new Insets(0, 0, 2, 0);
    }

    public static JLabel label(String s) {
        JLabel label = new JLabel(s);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Tools.MEDIUM_2);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        label.setBorder(new EmptyBorder(8, 8, 8, 0));

        return label;
    }
}
