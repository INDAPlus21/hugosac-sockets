
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
public class SignUpPanel extends JPanel {
    
    private JLabel lblUsernameError;
    private JLabel lblPasswordError;
    private JLabel lblConfirmPasswordError;
    
    private JTextField txfUsername;
    private JPasswordField txfPassword;
    private JPasswordField txfConfirmPassword;
    
    private JButton btnSignUp;
    private JLabel lblSignIn;
    
    private GridBagConstraints gbc;
    
    private final String PASSWORD_EXPLANATION = "<html>Password needs to be at "
                + "least 8 characters long and<br>contain at least "
                + "one uppercase letter and a special character</html>";
    
    /* Constructor */
    public SignUpPanel() {
        GridBagLayout g = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(g);
        setBackground(Tools.DARK_2);
        
        // Variables
        int widthSpacing = 85;
        int labelHeightDiff = 3;
        
        // Title
        JLabel lblTitle = Tools.title("Sign up");
        constraints(
            lblTitle, 0, 0, 2, 
            GridBagConstraints.CENTER, 
            new Insets(25, 0, 50, 0)
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
        
        // Confirm Password field
        JLabel lblConfirmPassword = Tools.fieldLabel("Confirm password");
        constraints(
            lblConfirmPassword, 0, 7, 2,
            GridBagConstraints.WEST, 
            new Insets(30, widthSpacing, labelHeightDiff, 0)
        );       
        txfConfirmPassword = Tools.passwordField();
        constraints(
            txfConfirmPassword, 0, 8, 2,
            GridBagConstraints.CENTER, 
            new Insets(0, widthSpacing, 0, widthSpacing)
        );
        
        // Sign in button
        btnSignUp = Tools.button("Sign up");
        constraints(
            btnSignUp, 0, 10, 2, 
            GridBagConstraints.CENTER, 
            new Insets(30, 0, 50, 0)
        );
        
        // Bottom labels
        JLabel lblAccount = new JLabel("Have an account?");
        lblAccount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        lblAccount.setForeground(Tools.MEDIUM_3);
        constraints(
            lblAccount, 0, 11, 1, 
            GridBagConstraints.EAST, 
            new Insets(0, widthSpacing + 65, 20, 0)
        );
        lblSignIn = new JLabel("Sign in");
        lblSignIn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        lblSignIn.setForeground(Color.WHITE);
        lblSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        constraints(
            lblSignIn, 1, 11, 1, 
            GridBagConstraints.WEST, 
            new Insets(0, 10, 20, 0)
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
        lblConfirmPasswordError = Tools.errorLabel();
        constraints(
            lblConfirmPasswordError, 0, 9, 2, 
            GridBagConstraints.WEST, 
            new Insets(10, widthSpacing, 0, 0)
        );
    }
    
    public JLabel getSignInLabel() {
        return this.lblSignIn;
    }
    
    /**
     * Adds actionlisteners to affected components.
     * 
     * @param a 
     */
    public void setActionListeners(ActionListener a) {
        btnSignUp.addActionListener(a);
        txfUsername.addActionListener(a);
        txfPassword.addActionListener(a);
        txfConfirmPassword.addActionListener(a);
    }
    
    /**
     * Is invoked after the sign up button is pressed. Checks that fields are
     * properly filled out as well as that the password matches the 
     * security requirements.
     * 
     * @param sdip
     * @throws IOException 
     */
    public void act(SignedInPanel sdip) throws IOException {
        lblConfirmPasswordError.setForeground(Tools.LIGHT);

        String username = txfUsername.getText();
        char[] firstChars = txfPassword.getPassword();
        String firstPassword = typedPassword(firstChars);

        char[] secondChars = txfConfirmPassword.getPassword();
        String secondPassword = typedPassword(secondChars);

        boolean over = false;

        if (username.isBlank()) {
            lblUsernameError.setText("Please fill out field");
            over = true;
        } else {
            lblUsernameError.setText(" ");
        }

        if (firstPassword == null) {
            lblPasswordError.setText("Please fill out field");
            over = true;
        } else {
            lblPasswordError.setText(" ");
        }

        if (secondPassword == null) {
            lblConfirmPasswordError.setText("Please fill out field");
            over = true;
        } else {
            lblConfirmPasswordError.setText(" ");
        }
        updateUI();

        if (over) return;

        if (userExists(username)) {
            lblConfirmPasswordError.setText("Username is already used");
            return;
        }

        if (!firstPassword.equals(secondPassword)) {
            lblConfirmPasswordError.setText("Passwords do not match");
            updateUI();
            return;
        }

        if (!isValidPassword(firstPassword)) {
            lblConfirmPasswordError.setText(PASSWORD_EXPLANATION);
            updateUI();
            return;
        }

        lblConfirmPasswordError.setForeground(Color.WHITE);
        lblConfirmPasswordError.setText("Account successfully created!");
        clearFields();

    }
    
    /**
     * Checks if the user already exists. The list from the MainWindow class
     * is used for demonstrational purposes only.
     * 
     * @param username
     * @return 
     */
    private boolean userExists(String username) {
        return MainWindow.users.stream().anyMatch(u -> username.equals(u.getUsername()));
    }
    
    /* Clears all text and password fields */
    private void clearFields() {
        txfUsername.setText("");
        txfPassword.setText("");
        txfConfirmPassword.setText("");
    }
    
    /* Clears fields and labels */
    public void clear() {
        clearFields();
        lblUsernameError.setText(" ");
        lblPasswordError.setText(" ");
        lblConfirmPasswordError.setText(" ");
    }
    
    /**
     * Checks for password validity
     * @param password
     * @return 
     */
    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W|\\_])(?=\\S+$).{8,}$";
        return password.matches(pattern);
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
     * Method for setting grid bag constraints.
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
}

