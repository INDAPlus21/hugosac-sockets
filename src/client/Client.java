
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Hugo Sacilotto
 */
public class Client implements Runnable {
    
    private final String DELIMITER = "\u2660";

    // Private variables
    private Socket socket;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Thread thread;
    private String clientID;
    private SignedInPanel signedInPanel;
    
    private String username;

    /**
     * Constructor
     * @param port
     * @param signedInPanel
     * @param username
     * @throws IOException 
     */
    public Client(int port, SignedInPanel signedInPanel, String username) throws IOException {
        this.socket = new Socket("localhost", port);
        this.streamIn = new DataInputStream(socket.getInputStream());
        this.streamOut = new DataOutputStream(socket.getOutputStream());
        this.signedInPanel = signedInPanel;
        this.username = username;
        
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public Client(int port, SignedInPanel signedInPanel) throws IOException {
        this.socket = new Socket("localhost", port);
        this.streamIn = new DataInputStream(socket.getInputStream());
        this.streamOut = new DataOutputStream(socket.getOutputStream());
        this.signedInPanel = signedInPanel;

        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void login(User user) throws IOException {
        this.username = user.getUsername();
        sendNotifyLogin(username);
    }
    
    private void sendNotifyLogin(String username) throws IOException {
        //Login♠<clientID>♠<username>
        streamOut.writeUTF(
            "Login"+DELIMITER+
            clientID+DELIMITER+
            username
        );
    }
    
    /**
     * Sends a message to the client manager.
     * @param receiverClientID
     * @param message
     * @throws IOException 
     */
    public void sendMessage(String receiverClientID, String message) throws IOException {
        //Message♠<senderID>♠<receiverID>♠<message>
        streamOut.writeUTF(
            "Message"+DELIMITER+clientID+DELIMITER+
            receiverClientID+DELIMITER+message
        );
    }
    
    /**
     * Handles different incoming messages.
     * @param msg 
     */
    public void handleIncomingMessage(String msg) throws IOException {
        String[] parts = msg.split(DELIMITER);
        String typeOfMessage = parts[0];
        
        switch (typeOfMessage) {
            case "ID":
                this.clientID = parts[1];
                break;
            case "OtherClientID":
                String id = parts[1];
                String otherClientsUsername = parts[2];
                signedInPanel.addClient(id, otherClientsUsername);
                break;
            case "Message":
                // Received message
                String senderClientID = parts[1];
                String message = parts[2];
                signedInPanel.newMessage(senderClientID, message);
                break;
        }
    }
    
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String msg = streamIn.readUTF();
                handleIncomingMessage(msg);
            } catch (IOException e) {}
        }
    }
}
