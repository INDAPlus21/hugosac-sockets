
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Hugo Sacilotto
 */
public class MainWindow extends JFrame {

    /* Only used to demonstrate in SignUpPanel */
    public static List<User> users = new LinkedList<User>(); 
    
    private User user;

    // Network port
    private final int PORT = 5000;
    
    /**
     * Constructor.
     * @throws IOException
     */
    public MainWindow() throws IOException {
        super("Chat App");
       
        setPreferredSize(new Dimension(800, 770));
        setMinimumSize(new Dimension(680, 650));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
        
        SignInPanel sip = new SignInPanel();
        SignedInPanel sdip = new SignedInPanel();
        
        add(sip);
        add(sdip);
        sdip.setVisible(false);
        
        getContentPane().setBackground(Tools.DARK_1); 
        
        sip.setActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user = sip.act(sdip);
                    if (user != null) {
                        sdip.setUser(user, PORT);
                        
                        // Switch panels
                        sip.setVisible(false);
                        sdip.setVisible(true);
                    }
                } catch (IOException | InterruptedException ex) {}
            }
        });
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) throws IOException {
        MainWindow mw = new MainWindow();
    } 
}
