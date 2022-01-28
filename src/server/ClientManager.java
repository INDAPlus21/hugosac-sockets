
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Hugo Sacilotto
 */
public class ClientManager implements Runnable {
    
    // Private variables
    private Socket clientSocket;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Thread thread;
    private List<String> chatIDs;
    private String clientID;
    private ChatServer chatServer;
    
    /**
     * Constructor.
     * @param socket
     * @param chatServer
     * @throws IOException 
     */
    public ClientManager(Socket socket, ChatServer chatServer) throws IOException {
        this.clientSocket = socket;
        this.streamIn = new DataInputStream(socket.getInputStream());
        this.streamOut = new DataOutputStream(socket.getOutputStream());
        this.chatServer = chatServer;
        
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    /**
     * Sends a message to the client.
     * @param senderClientID
     * @param message
     * @throws IOException 
     */
    public void sendMessage(String senderClientID, String message) throws IOException {
        //Message♠<senderClientID>♠<message>
        streamOut.writeUTF(
            "Message"+ChatServer.DELIMITER+
            senderClientID+ChatServer.DELIMITER+
            message
        );
    }
    
    /**
     * Sends the created ID to the client.
     * @param clientID
     * @throws IOException 
     */
    public void sendID(String clientID) throws IOException {
        this.clientID = clientID;
        streamOut.writeUTF("ID"+ChatServer.DELIMITER+clientID);
    }
    
    /**
     * Sends the client the ID of a newly created client.
     * @param clientID
     * @param username
     * @throws IOException 
     */
    public void sendNewClientID(String clientID, String username) throws IOException {
        streamOut.writeUTF(
            "OtherClientID"+ChatServer.DELIMITER+
            clientID+ChatServer.DELIMITER+
            username
        );
    }

    /**
     * Handles different incoming messages.
     * @param msg
     * @throws IOException
     */
    public void handleIncomingMessage(String msg) throws IOException {
        String[] parts = msg.split(ChatServer.DELIMITER);
        
        String messageType = parts[0];
        
        switch(messageType) {
            case "Message" -> chatServer.sendToChat(msg);
            case "Login" -> chatServer.newLogin(msg);
        }
    }
    
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String msg = streamIn.readUTF();
                handleIncomingMessage(msg);
            } catch(IOException e) {
                break;
            }
        }
        
        try {
            clientSocket.close();
        } catch(IOException e) {}
    }
}
