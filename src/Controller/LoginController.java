// @author KAN/IT/2022/F/0036
package Controller;

import javax.swing.JOptionPane; // JOptionPane for displaying dialog boxes
import View.Home; // Import the Home view class
import View.MemberView; // Import the MemberView class
import View.Login; // Import the Login view class

public class LoginController {
    private final Login loginView; // Encapsulated view object for the login frame

    // Constructor accepting the login view
    public LoginController(Login loginView) {
        this.loginView = loginView; // Initialize the login view
    }

    // Method to authenticate the user based on provided username and password
    public void authenticate(String username, String password) {
        
        // Check if the entered username and password match admin credentials
        if (username.equals("admin") && password.equals("admin")) 
        {
            loginView.setVisible(false); // Hide the login frame
            new Home(true).setVisible(true); // Open the Home for admin
        } 
        
        // Check if the entered username and password match member credentials
        else if (username.equals("member") && password.equals("member")) 
        {
            loginView.setVisible(false); // Hide the login frame
            new MemberView().setVisible(true); // Open the MemberView for members
        } 
        
        else 
        {
            // Show error message for incorrect credentials
            JOptionPane.showMessageDialog(loginView, "Incorrect Username or Password!");
        }
    }
    
    
}
