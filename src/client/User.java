
/**
 *
 * @author Hugo Sacilotto
 */
public class User implements Comparable<User> {
    // Fields
    private String username;
    private String userID;
    
    /**
     * Constructor
     * @param username
     */
    public User(String username) {
        this.username = username;
    }
    
    public String getUserID() {
        return this.userID;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUserID(String id) {
        this.userID = id;
    }
    
    @Override
    public String toString() {
        return this.username+", "+"ID: " + this.userID;
    }
    
    @Override
    public int compareTo(User u) {
        return this.username.compareTo(u.username);
    }
}
