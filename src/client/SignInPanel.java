
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Hugo Sacilotto
 */
public class SignInPanel extends JPanel {

    private JLabel lblUsernameError;
    private JLabel lblPasswordError;
    private JTextField txfUsername;
    private JPasswordField txfPassword;
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

        // Password field
        JLabel lblPassword = Tools.fieldLabel("Password");
        constraints(
            lblPassword, 0, 4, 2, 
            GridBagConstraints.WEST, 
            new Insets(30, widthSpacing, labelHeightDiff, 0)
        );
        txfPassword = Tools.passwordField();
        constraints(
            txfPassword, 0, 5, 2, 
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

        // Bottom labels
        JLabel lblNoAccount = new JLabel("Don't have an account?");
        lblNoAccount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        lblNoAccount.setForeground(Tools.MEDIUM_3);
        constraints(
            lblNoAccount, 0, 8, 1, 
            GridBagConstraints.EAST, 
            new Insets(0, 120, 30, 0)
        );
        lblSignUp = new JLabel("Sign Up");
        lblSignUp.setForeground(Color.WHITE);
        lblSignUp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        constraints(
            lblSignUp, 1, 8, 1, 
            GridBagConstraints.WEST, 
            new Insets(0, 10, 30, 0)
        );
        
        // Error labels
        lblUsernameError = Tools.errorLabel();
        constraints(
            lblUsernameError, 0, 3, 2, 
            GridBagConstraints.WEST, 
            new Insets(10, widthSpacing, 0, 0)
        );
        lblPasswordError = Tools.errorLabel();
        constraints(
            lblPasswordError, 0, 6, 2, 
            GridBagConstraints.WEST, 
            new Insets(10, widthSpacing, 0, 0)
        );
    }
    
    public JLabel getSignUpLabel() {
        return this.lblSignUp;
    }

    /**
     * Adds actionlisteners to affected components.
     * 
     * @param a 
     */
    public void setActionListeners(ActionListener a) {
        btnSignIn.addActionListener(a);
        txfUsername.addActionListener(a);
        txfPassword.addActionListener(a);
    }
    
    /**
     * Checks to authenticate the login action.
     * 
     * @param sdip
     * @return
     * @throws IOException 
     */
    public User act(SignedInPanel sdip) throws IOException {
        lblPasswordError.setForeground(Tools.LIGHT);

        String username = txfUsername.getText();
        char[] firstChars = txfPassword.getPassword();
        String password = typedPassword(firstChars);

        boolean goOn = true;

        if (username.isBlank()) {
            goOn = false;
            lblUsernameError.setText("Please fill out field");
        } else {
            lblUsernameError.setText(" ");
        }

        if (password == null) {
            goOn = false;
            lblPasswordError.setText("Please fill out field");
        } else {
            lblPasswordError.setText(" ");
        }

        if (!goOn) {
            return null;
        }

        User u = new User(username, password);
        if (u == null) {
            lblPasswordError.setText("Invalid username or password");
            return null;
        }

        lblPasswordError.setForeground(Color.WHITE);
        lblPasswordError.setText("Successfully logged in");
        clearFields();
        
        return u;
    }
    
    /* Clears all text and password fields */
    private void clearFields() {
        txfUsername.setText("");
        txfPassword.setText("");
    }
    
    /* Clears fields and labels */
    public void clear() {
        clearFields();
        lblUsernameError.setText(" ");
        lblPasswordError.setText(" ");
    }
    
    /**
     * Returns a string from the password in a char array.
     * 
     * @param chars
     * @return 
     */
    private String typedPassword(char[] chars) {
        if (chars.length > 0) {
            return new String(chars);            
        }        
        return null;
    }
    
    /**
     * Sets Grid bag constraints.
     * 
     * @param c
     * @param x
     * @param y
     * @param width
     * @param anchor
     * @param insets 
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

