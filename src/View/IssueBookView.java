// @author KAN/IT/2022/F/0036
package View;

import java.time.LocalDate; // Import Date
import java.time.ZoneId; // Allows specifying a time zone for date and time operations
import java.sql.*; // Import JDBC classes for database connectivity
import Controller.DBConnection; // Import DBConnection class
import javax.swing.JOptionPane; // Provides dialog boxes for displaying messages
import Model.IssuedBook; // Import IssuedBook class

public class IssueBookView extends javax.swing.JFrame {

    // SQL Query to insert an issued book record
    private static final String INSERT_ISSUED_BOOK_SQL
            = "INSERT INTO IssueBook (bookID, memberID, issueDate, dueDate, returnBook) VALUES (?, ?, ?, ?, ?)";
    private static final String ERROR_TITLE = "Database Error";
    
    // Add SQL queries to check existence
    private static final String CHECK_BOOK_EXISTS_SQL = "SELECT COUNT(*) FROM NewBooks WHERE bookID = ?";
    private static final String CHECK_MEMBER_EXISTS_SQL = "SELECT COUNT(*) FROM NewMembers WHERE memberID = ?";

    // Constructor for initializing components and setting up the IssueBookView
    public IssueBookView(StatisticsView statistic) {
        initComponents(); // Initialize components
    }

    // Method to issue a book to a member
    public void issueBook() {
        // Validate fields to ensure all required data is entered
        if (!validateFields()) {
            return; // If validation fails, exit the method
        }
        
        // Retrieve bookID, memberID, issue date, and due date from fields
        int bookID = Integer.parseInt(txtBookID.getText());
        int memberID = Integer.parseInt(txtMemberID.getText());
        Date issueDate = new Date(DateIssue.getDate().getTime());
        Date dueDate = new Date(DateDue.getDate().getTime());
        
        // Check if the book exists
        if (!doesBookExist(bookID)) {
            showMessage("Invalid Book ID. Please enter a valid Book ID.", "Input Error");
            return;
        }

        // Check if the member exists
        if (!doesMemberExist(memberID)) {
            showMessage("Invalid Member ID. Please enter a valid Member ID.", "Input Error");
            return;
        }

        // Check if the book is already issued
        if (isBookIssued(bookID)) {
            showMessage("This book is already issued and cannot be issued again.", "Issue Error");
            return;
        }

        // Insert issued book details into the database
        try (Connection connection = DBConnection.getConnection(); PreparedStatement pstmt = connection.prepareStatement(INSERT_ISSUED_BOOK_SQL)) {

            // Set values for the prepared statement
            pstmt.setInt(1, bookID);
            pstmt.setInt(2, memberID);
            pstmt.setDate(3, issueDate);
            pstmt.setDate(4, dueDate);
            pstmt.setString(5, "No"); // Default value for return status

            int rowsAffected = pstmt.executeUpdate();
            
            // Confirm if the book was successfully issued
            if (rowsAffected > 0) {
                showMessage("Book issued successfully!", "Success");
                clearFields();
            } 
            else {
                showMessage("Failed to issue the book. Please try again.", ERROR_TITLE);
            }
        } 
        catch (SQLException e) {
            showMessage("Error issuing the book: " + e.getMessage(), ERROR_TITLE);
        }
    }

    // Method to validate user input in fields
    private boolean validateFields() {
        // Check if any field is empty
        if (txtBookID.getText().isEmpty() || txtMemberID.getText().isEmpty() || DateIssue.getDate() == null || DateDue.getDate() == null) {
            showMessage("All fields must be filled in.", "Input Error");
            return false;
        }

        // Validate that the due date is after the issue date
        LocalDate issueDate = DateIssue.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dueDate = DateDue.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        if (dueDate.isBefore(issueDate)) {
            showMessage("Due date must be after the issue date.", "Input Error");
            return false;
        }
        return true;
    }
    
    // Method to check if a book exists
    private boolean doesBookExist(int bookID) {
        try (Connection connection = DBConnection.getConnection(); 
             PreparedStatement pstmt = connection.prepareStatement(CHECK_BOOK_EXISTS_SQL)) {
            pstmt.setInt(1, bookID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if book exists
            }
        } 
        catch (SQLException e) {
            showMessage("Error checking book existence: " + e.getMessage(), ERROR_TITLE);
        }
        return false; // Default to false if error occurs
    }

    // Method to check if a member exists
    private boolean doesMemberExist(int memberID) {
        try (Connection connection = DBConnection.getConnection(); 
             PreparedStatement pstmt = connection.prepareStatement(CHECK_MEMBER_EXISTS_SQL)) {
            pstmt.setInt(1, memberID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if member exists
            }
        } 
        catch (SQLException e) {
            showMessage("Error checking member existence: " + e.getMessage(), ERROR_TITLE);
        }
        return false; // Default to false if error occurs
    }

    // Method to check if a book is already issued
    private boolean isBookIssued(int bookID) {
        String query = "SELECT COUNT(*) FROM IssueBook WHERE bookID = ? AND returnBook = 'No'";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if book is issued
            }
        } 
        catch (SQLException e) {
            showMessage("Error checking issued book: " + e.getMessage(), ERROR_TITLE);
        }
        return false; // Default to false if error occurs
    }

    // Method to display messages in a dialog box
    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to clear input fields
    private void clearFields() {
        txtBookID.setText("");
        txtMemberID.setText("");
        DateIssue.setDate(null);
        DateDue.setDate(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBookID = new javax.swing.JTextField();
        txtMemberID = new javax.swing.JTextField();
        DateIssue = new com.toedter.calendar.JDateChooser();
        DateDue = new com.toedter.calendar.JDateChooser();
        btnIssue = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 200));
        setUndecorated(true);
        setSize(new java.awt.Dimension(700, 450));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Book ID:");

        jLabel2.setBackground(new java.awt.Color(255, 255, 204));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Member ID:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 204));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Issue Date:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Due Date:");

        txtBookID.setBackground(new java.awt.Color(255, 255, 204));
        txtBookID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookID.setForeground(new java.awt.Color(0, 0, 0));

        txtMemberID.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberID.setForeground(new java.awt.Color(0, 0, 0));

        DateIssue.setBackground(new java.awt.Color(255, 255, 204));
        DateIssue.setForeground(new java.awt.Color(0, 0, 0));

        DateDue.setBackground(new java.awt.Color(255, 255, 204));
        DateDue.setForeground(new java.awt.Color(0, 0, 0));

        btnIssue.setBackground(new java.awt.Color(255, 255, 153));
        btnIssue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIssue.setForeground(new java.awt.Color(0, 0, 0));
        btnIssue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issue book.png"))); // NOI18N
        btnIssue.setText("Issue");
        btnIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBookID)
                    .addComponent(txtMemberID)
                    .addComponent(DateIssue, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(DateDue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(117, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnCancel)
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(DateIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(DateDue, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnIssue))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose(); // Close the Issue Book view
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIssueActionPerformed
        // Try to parse input fields
        try {
            int bookID = Integer.parseInt(txtBookID.getText());
            int memberID = Integer.parseInt(txtMemberID.getText());

            // Validate fields before proceeding
            if (!validateFields()) {
                return; // Exit if validation fails
            }

            // Check if the book is already issued
            if (isBookIssued(bookID)) {
                showMessage("This book has already been issued.", "Issue Error");
                return; // Exit if the book is already issued
            }

            // Get issue date and due date
            LocalDate issueDate = DateIssue.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dueDate = DateDue.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Create an IssuedBook object
            IssuedBook issuedBook = new IssuedBook(bookID, memberID, issueDate, dueDate);

            // Issue the book
            issueBook();

            // Clear input fields after successful issuance
            clearFields();

        } 
        catch (NumberFormatException e) {
            showMessage("Please enter valid numeric values for Book ID and Member ID.", "Input Error");
        } 
        catch (Exception e) {
            showMessage("An unexpected error occurred: " + e.getMessage(), ERROR_TITLE);
        }
    }//GEN-LAST:event_btnIssueActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                StatisticsView statistic = new StatisticsView(); // Create an instance of StatisticsView
                new IssueBookView(statistic).setVisible(true); // Pass it to the IssueBookView constructor
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateDue;
    private com.toedter.calendar.JDateChooser DateIssue;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnIssue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JTextField txtMemberID;
    // End of variables declaration//GEN-END:variables
}
