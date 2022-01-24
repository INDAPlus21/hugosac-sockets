
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    
    private Client client;
    private User user;
    
    public MainWindow() throws IOException {
        super("Chat App");
       
        setPreferredSize(new Dimension(800, 770));
        setMinimumSize(new Dimension(680, 650));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
        
        SignInPanel sip = new SignInPanel();
        SignUpPanel sup = new SignUpPanel();
        SignedInPanel sdip = new SignedInPanel();
        
        this.client = new Client(5000, sdip);
        
        add(sip);
        add(sup);
        add(sdip);
        sup.setVisible(false);
        sdip.setVisible(false);
        
        getContentPane().setBackground(Tools.DARK_1);
        
        ActionListener signInAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user = sip.act(sdip);
                    if (user != null) {
                        client.login(user);
                        sdip.setUser(user, client);
                        
                        // Switch panels
                        sip.setVisible(false);
                        sdip.setVisible(true);
                    }
                } catch (IOException ex) {}
            }
        };
        
        ActionListener signUpAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sup.act(sdip);
                } catch (IOException ex) {}
            }   
        };
        
        sip.setActionListeners(signInAction);
        sup.setActionListeners(signUpAction);
        
        sip.getSignUpLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sip.getSignUpLabel().setForeground(Tools.MEDIUM_2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sip.getSignUpLabel().setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {               
                sip.setVisible(false);
                sip.clear();
                sup.setVisible(true);
            }
        }); 
        
        sup.getSignInLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sup.getSignInLabel().setForeground(Tools.MEDIUM_2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sup.getSignInLabel().setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                sup.setVisible(false);
                sup.clear();
                sip.setVisible(true);
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
