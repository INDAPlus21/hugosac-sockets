
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Hugo Sacilotto
 */
public class Tools {
    
    // Colors
    public static final Color DARK_1   = new Color(13, 27, 42);
    public static final Color DARK_2   = new Color(27, 38, 59);
    public static final Color DARK_3   = new Color(37, 51, 74);
    public static final Color MEDIUM_1 = new Color(46, 64, 89);
    public static final Color MEDIUM_2 = new Color(65, 90, 119);
    public static final Color MEDIUM_3 = new Color(119, 141, 169);
    public static final Color LIGHT    = new Color(224, 225, 221, 180);
    public static final Color ERROR    = new Color(182, 0, 0);
    public static final Color INPUT    = DARK_3;
    
    // Standard input field size
    public static final Dimension INPUT_FIELD_SIZE = new Dimension(300, 38);
    
    // Border for input fields
    public static final Border INPUT_FIELD_BORDER = BorderFactory.createCompoundBorder(
            null, BorderFactory.createEmptyBorder(0, 10, 0, 0)
    );
    
    /* Class is not to be instantiated */
    private Tools() { }
    
    /**
     * Returns a styled JLabel in the form of a title.
     * @param title
     * @return a JLabel
     */
    public static JLabel title(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        return label;
    }
    
    /**
     * Returns a styled JLabel in the form of a small title.
     * @param title
     * @return a JLabel
     */
    public static JLabel smallTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        label.setForeground(Color.BLUE);
        return label;
    }
    
    /**
     * @return a styled JTextField
     */
    public static JTextField inputField() {
        JTextField field = new JTextField();
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        field.setCaretColor(Color.WHITE);
        field.setPreferredSize(INPUT_FIELD_SIZE);
        field.setBackground(DARK_3);
        field.setForeground(Color.WHITE);
        field.setBorder(INPUT_FIELD_BORDER);
        
        return field;
    }
    
    /**
     * @param text
     * @return a styled JLabel to be a heading for input fields
     */
    public static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        
        return label;
    }
    
    /**
     * @return a styled JLabel in the form of an error message
     */
    public static JLabel errorLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label.setForeground(LIGHT);
        
        return label;
    }
    
    /**
     * @param text
     * @return a styled JButton
     */
    public static JButton button(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setMargin(new Insets(7, 30, 7, 30));
        button.setFocusPainted(false);
        button.setBackground(MEDIUM_3);
        button.setForeground(DARK_1);
        
        return button;
    }
}

