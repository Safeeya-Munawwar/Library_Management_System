// @author KAN/IT/2022/F/0036
package View;

import Model.Member; //Import Member class
import Model.MembershipCard; // Import Membership class
import Controller.MemberDBC; // Import MemberDBC
import javax.swing.*; // Import swing components for GUI
import java.util.Date; // Import Date
import java.sql.Connection; // Connection interface for DB connectivity
import java.sql.DriverManager; // DriverManager to manage the DB connection
import java.sql.ResultSet; // ResultSet interface used to store the results of a query executed against the DB
import java.sql.SQLException; // SQLException for handling SQL errors
import java.sql.Statement; // Import the Statement interface used to execute static SQL queries against the DB.

public class AddMemberView extends JFrame {

    private Connection connection; // Connection object to manage DB connectivity

    // Constructor
    public AddMemberView() {
        try {
            String url = "jdbc:mysql://localhost:3306/librarydb";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password); // Establish connection
            System.out.println("Connected to the database.");
        } 
        catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database: " + e.getMessage());
        }

        setTitle("Add New Member"); //Title for window
        initComponents(); // Initialize UI components
    }

    // Method to add a new member to the database
    private void addMember() {
        try {
            // Parsing and getting input values from text fields
            int memberID = Integer.parseInt(txtMemberID.getText().trim());
            String name = txtMemberName.getText().trim();
            String contactNumber = txtContactNumber.getText().trim();
            String address = txtAddress.getText().trim();
            String cardNumber = txtCardNumber.getText().trim();
            Date date = txtExpiryDate.getDate(); // Get date from date picker
            
            // Input validation - checking for empty fields
            if (name.isEmpty() || contactNumber.isEmpty() || address.isEmpty() || cardNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all member details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the expiration date is valid
            if (date == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid expiration date.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit if date is not valid
            }

            // Create MembershipCard and Member objects
            java.sql.Date sqlExpirationDate = new java.sql.Date(date.getTime()); // Convert to SQL date
            MembershipCard membershipCard = new MembershipCard(cardNumber, sqlExpirationDate);
            Member member = new Member(memberID, "defaultUsername", name, contactNumber, address, membershipCard, sqlExpirationDate);

            // Controller to check if memberID or cardNumber already exist
            MemberDBC memberDBC = new MemberDBC();
            if (memberDBC.doesMemberExist(memberID)) {
                JOptionPane.showMessageDialog(this, "Member ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (memberDBC.doesCardNumberExist(cardNumber)) {
                JOptionPane.showMessageDialog(this, "Card Number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add member to the database
            memberDBC.addMember(member);

            // Log member details
            System.out.println("Member added successfully: " + member);

            JOptionPane.showMessageDialog(this, "Member added successfully!");
            clearFields();
            dispose(); // Close the window
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) {
            e.printStackTrace(); // Print exception details if connection fails
            JOptionPane.showMessageDialog(this, "An error occurred while adding the member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to retrieve and display the details of all members stored 
    public void displayMembers() {
        try {
            String query = "SELECT memberID, name, contactInfo, address, cardNumber, expirationDate FROM NewMembers"; // SQL query
            Statement stmt = connection.createStatement(); // Create a Statement object to execute the query
            ResultSet rs = stmt.executeQuery(query);  // Execute the query and store results in a ResultSet object

             // Iterate through each row in the ResultSet
            while (rs.next()) {
                // Retrieve member details
                int memberId = rs.getInt("memberID");
                String name = rs.getString("name");
                String contactInfo = rs.getString("contactInfo");
                String address = rs.getString("address");
                String cardNumber = rs.getString("cardNumber");
                Date expirationDate = rs.getDate("expirationDate");

                // Print member details to console
                System.out.println(memberId + "\t" + name + "\t" + contactInfo + "\t" + address + "\t" + cardNumber + "\t" + expirationDate);
            }
        } 
        catch (SQLException e) {
            System.out.println("An error occurred while retrieving members: " + e.getMessage());
        }
    }
    
    // Method to clear all fields
    public void clearFields(){
        txtMemberID.setText("");
        txtMemberName.setText("");
        txtContactNumber.setText("");
        txtAddress.setText("");
        txtCardNumber.setText("");
        txtExpiryDate.setDate(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        txtMemberName = new javax.swing.JTextField();
        txtContactNumber = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtCardNumber = new javax.swing.JTextField();
        txtExpiryDate = new com.toedter.calendar.JDateChooser();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 200));
        setMaximumSize(new java.awt.Dimension(700, 450));
        setMinimumSize(new java.awt.Dimension(700, 450));
        setUndecorated(true);
        setSize(new java.awt.Dimension(700, 450));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Member ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 59, 245, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Member Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 112, 245, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Contact Number:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 165, 245, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Address:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 218, 245, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Membership Card Number:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 271, 245, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 204));
        jLabel6.setText("Expiration Date:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 319, 245, -1));

        txtMemberID.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberID.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtMemberID, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 54, 282, -1));

        txtMemberName.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberName.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtMemberName, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 107, 282, -1));

        txtContactNumber.setBackground(new java.awt.Color(255, 255, 204));
        txtContactNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtContactNumber.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtContactNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 160, 282, -1));

        txtAddress.setBackground(new java.awt.Color(255, 255, 204));
        txtAddress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 213, 282, -1));

        txtCardNumber.setBackground(new java.awt.Color(255, 255, 204));
        txtCardNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtCardNumber.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtCardNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 266, 282, -1));

        txtExpiryDate.setBackground(new java.awt.Color(255, 255, 204));
        getContentPane().add(txtExpiryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 315, 282, 40));

        btnSave.setBackground(new java.awt.Color(255, 255, 153));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 0, 0));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save-icon--1.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, -1, -1));

        btnCancel.setBackground(new java.awt.Color(255, 255, 153));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(0, 0, 0));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/red-x-mark-transparent-background-3.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, -1, -1));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        addMember();  // Call the add member method
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false); // Close window
    }//GEN-LAST:event_btnCancelActionPerformed
    
    
    // Main method to run the application
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddMemberView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCardNumber;
    private javax.swing.JTextField txtContactNumber;
    private com.toedter.calendar.JDateChooser txtExpiryDate;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtMemberName;
    // End of variables declaration//GEN-END:variables
}
