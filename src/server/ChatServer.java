
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JFrame;

/**
 *
 * @author Hugo Sacilotto
 */
public class ChatServer extends JFrame implements Runnable {
    
    public static final String DELIMITER = "\u2660";
    
    // Network port
    private final int PORT = 5000;

    // Used as client ID
    private static int numberOfClients = 0;
    
    private ServerSocket serverSocket;
    private Thread thread;

    // Hash maps for users
    private HashMap<String, ClientManager> clientManagerMap;
    private HashMap<String, User> userMap;
    
    /**
     * Constructor.
     * @throws java.io.IOException
     */
    public ChatServer() throws IOException {
        super("Chat Server");
        this.serverSocket = new ServerSocket(PORT);
        this.clientManagerMap = new HashMap<>();
        this.userMap = new HashMap<>();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Server has shut down");
            }
        });
        
        // Window settings
        setPreferredSize(new Dimension(300, 300));
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Start server
        this.thread = new Thread(this);
        System.out.println("Server is running...");
    }
    
    public void newLogin(String msg) throws IOException {
        //Login♠<clientID>♠<username>
        String[] parts = msg.split(DELIMITER);
        String clientID = parts[1];
        String username = parts[2];
        
        // Add new client to the user hash map
        ClientManager cm = clientManagerMap.get(clientID);
        userMap.put(clientID, new User(username));
        System.out.println(username+" has joined!");
        
        // Send the new client's ID to the other clients
        sendNewClientIDToClients(clientID, username, cm);
    }
    
    /**
     * When a new client joins, its ID is sent to all other clients.
     * The other client's ID's are sent to the new client as well.
     * @param clientID
     * @param newClient
     * @throws IOException 
     */
    private void sendNewClientIDToClients(String clientID, String username, ClientManager newClient) throws IOException {
        // Loop through clients
        for (Entry<String, ClientManager> cm : clientManagerMap.entrySet()) {
            if (cm.getValue() != newClient) { // Don't send to yourself
                // Send the new client ID to another client
                cm.getValue().sendNewClientID(clientID, username);
                
                // Send the other client IDs to the new client
                String currentUsername = userMap.get(cm.getKey()).getUsername();
                newClient.sendNewClientID(cm.getKey(), currentUsername);   
            }
        }
    }    
    
    /**
     * Selects the appropriate client manager based on ID to send a message
     * to its client.
     * @param msg
     * @throws IOException 
     */
    public void sendToChat(String msg) throws IOException {     
        // Message♠<senderID>♠<receiverID>♠<message>
        
        String[] parts = msg.split(DELIMITER);
        
        String senderClientID = parts[1];
        String receivingClientID = parts[2];
        String message = parts[3];
        
        ClientManager cm = clientManagerMap.get(receivingClientID);
        cm.sendMessage(senderClientID, message);
    }
    
    /**
     * Handles a new client connection.
     * @param clientSocket
     * @throws IOException 
     */
    private void handleClientSocket(Socket clientSocket) throws IOException {
        ClientManager cm = new ClientManager(clientSocket, this);
        clientManagerMap.put(""+numberOfClients, cm);
        cm.sendID(""+numberOfClients);        
        numberOfClients++;
    }
    
    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                Socket s = serverSocket.accept();
                handleClientSocket(s);
            } catch (IOException e) {}
        }
    }
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ChatServer s = new ChatServer();
        s.thread.start();
    }
}
