// @author KAN/IT/2022/F/0036
package Model;

public class User {

    private String username;
    private String password;
    private String role; // Either "Admin" or "Member"

    // Constructors
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Constructor that accepts only a username
    public User(String username) {
        this.username = username;
    }

    // Setters and Getters
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    // Method to check if the user is an admin
    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(role);
    }
    
    // Override toString method for meaningful representation
    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + '}';
    }
}
