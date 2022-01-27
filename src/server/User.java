
/**
 *
 * @author Hugo Sacilotto
 */
public class User implements Comparable<User> {
    //Fields
    private String username;
    private String password;
    private String userID;
    
    /**
     * Constructor
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUserID() {
        return this.userID;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setUserID(String id) {
        this.userID = id;
    }
    
    @Override
    public String toString() {
        return username+", "+password;
    }
    
    @Override
    public int compareTo(User u) {
        int i = this.username.compareTo(u.username);
        
        if (i == 0) {
            i = this.password.compareTo(u.password);
        }
        
        return i;
    }
}
