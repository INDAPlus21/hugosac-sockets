
import java.time.LocalDateTime;

/**
 *
 * @author Hugo Sacilotto
 */
public class Message {
    private String message;
    private String userSent;
    private LocalDateTime createdAt; 
    
    public Message(String msg, String userSent) {
        this.message = msg;
        this.userSent = userSent;
    }
}

