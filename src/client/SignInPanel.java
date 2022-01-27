
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Hugo Sacilotto
 */
public class SignInPanel extends JPanel {

    private JLabel lblUsernameError;
    private JTextField txfUsername;
    private JButton btnSignIn;
    private JLabel lblSignUp;

    private GridBagConstraints gbc;

    /* Constructor */
    public SignInPanel() {
        GridBagLayout g = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(g);
        setBackground(Tools.DARK_2);

        // Variables
        int widthSpacing = 85;
        int labelHeightDiff = 3;

        // Title
        JLabel lblTitle = Tools.title("Sign in");
        constraints(
            lblTitle, 0, 0, 2,
            GridBagConstraints.CENTER,
            new Insets(25, 0, 60, 0)
        );

        // Username field
        JLabel lblUsername = Tools.fieldLabel("Username");
        constraints(
            lblUsername, 0, 1, 2, 
            GridBagConstraints.WEST, 
            new Insets(0, widthSpacing, labelHeightDiff, 0)
        );
        txfUsername = Tools.inputField();
        constraints(
            txfUsername, 0, 2, 2,
            GridBagConstraints.CENTER,
            new Insets(0, widthSpacing, 0, widthSpacing)
        );

        // Sign in button
        btnSignIn = Tools.button("Sign in");
        constraints(
            btnSignIn, 0, 7, 2, 
            GridBagConstraints.CENTER,
            new Insets(30, 0, 60, 0)
        );
        
        // Error labels
        lblUsernameError = Tools.errorLabel();
        constraints(
            lblUsernameError, 0, 3, 2, 
            GridBagConstraints.WEST, 
            new Insets(10, widthSpacing, 0, 0)
        );
    }
    
    public JLabel getSignUpLabel() {
        return this.lblSignUp;
    }

    /**
     * Adds actionlisteners to affected components.
     * @param a 
     */
    public void setActionListeners(ActionListener a) {
        btnSignIn.addActionListener(a);
        txfUsername.addActionListener(a);
    }
    
    /**
     * Checks to authenticate the login action.
     * @param sdip
     * @return
     * @throws IOException 
     */
    public User act(SignedInPanel sdip) throws IOException {
        String username = txfUsername.getText();

        boolean goOn = true;

        if (username.isBlank()) {
            goOn = false;
            lblUsernameError.setText("Please fill out field");
        } else {
            lblUsernameError.setText(" ");
        }

        if (!goOn) {
            return null;
        }

        User u = new User(username);
        txfUsername.setText("");
        
        return u;
    }
    
    /* Clears fields and labels */
    public void clear() {
        txfUsername.setText("");
        lblUsernameError.setText(" ");
    }
    
    /**
     * Sets Grid bag constraints.
     * @param c is the component
     * @param x is the gridx value
     * @param y is the gridy value
     * @param width is the gridwidth
     * @param anchor anchor is the anchor point
     * @param insets are the insets
     */
    private void constraints(JComponent c, int x, int y, int width, int anchor, Insets insets) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        gbc.insets = insets;
        add(c, gbc);
    }

    public void toggleVisible() {
        setVisible(!isVisible());
    }

    public JButton getButton() {
        return this.btnSignIn;
    }
}

