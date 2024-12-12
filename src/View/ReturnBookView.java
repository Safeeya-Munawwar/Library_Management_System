// @author KAN/IT/2022/F/0036
package View;

import java.sql.Connection; // Establishing a connection to the DB
import java.sql.PreparedStatement; // Executing queries with parameters
import java.sql.ResultSet; // Used to retrieve data returned by executing a SELECT SQL query
import java.sql.SQLException; // Handles exceptions related to SQL operations
import java.sql.DriverManager; // Manages connections to the database using database URL, username, and password
import java.text.SimpleDateFormat; // Helps format dates into a specific pattern, such as "yyyy-MM-dd"
import javax.swing.JOptionPane; // Displays dialog boxes for messages, warnings, or errors.


public class ReturnBookView extends javax.swing.JFrame {

    // Database connection parameters
    private final String DB_URL = "jdbc:mysql://localhost:3306/librarydb";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
    private Connection connection;

     // Constructor to initialize the components and establish a database connection
    public ReturnBookView() {
        initComponents();
        establishConnection();
        txtIssueDate.setEditable(false); // set non editable
        txtDueDate.setEditable(false); // set non editable
    }

    // Method to establish a database connection to the DB
    private void establishConnection() {
        try {
            // Establish a DB connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the Database.");
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    // Method to search for book and member using ID
    private void searchBookAndMember() {
        String bookID = txtBookID.getText();
        String memberID = txtMemberID.getText();

        // Check if both fields are filled
        if (bookID.isEmpty() || memberID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Book ID and Member ID.");
            return;
        }

        try {
            // SQL query to find the issued book and member
            String query = "SELECT issueDate, dueDate FROM issuebook WHERE bookID = ? AND memberID = ? AND returnBook = 'No'";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(bookID));
                stmt.setInt(2, Integer.parseInt(memberID));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Set issue date and due date in the text fields
                    txtIssueDate.setText(rs.getString("issueDate"));
                    txtDueDate.setText(rs.getString("dueDate"));
                    btnReturn.setEnabled(true); // Enable return button on successful search
                } 
                else {
                    JOptionPane.showMessageDialog(this, "No record found for the given Book ID and Member ID.");
                    btnReturn.setEnabled(false); // Disable return button if no record found
                }
            }
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Book ID and Member ID.");
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving book information: " + e.getMessage());
        }
    }

    // Method to return a book
    private void returnBook() {
        String bookID = txtBookID.getText();
        String memberID = txtMemberID.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Ensure DateReturn is a valid date
        if (DateReturn.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a return date.");
            return;
        }

        String returnDate = sdf.format(DateReturn.getDate());  // Now, DateReturn.getDate() is guaranteed not to be null

        try {
            // Insert returned book record into the ReturnedBooks table
            String insertQuery = "INSERT INTO returnedbooks (bookID, memberID, issuedDate, returnedDate, returnedBook) VALUES (?, ?, ?, ?, 'Yes')";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, Integer.parseInt(bookID));
                insertStmt.setInt(2, Integer.parseInt(memberID));
                insertStmt.setString(3, txtIssueDate.getText());
                insertStmt.setString(4, returnDate);
                insertStmt.executeUpdate(); // Execute the inser query
            }

            // Update IssuedBook table to mark the book as returned - Yes
            String updateQuery = "UPDATE issuebook SET returnBook = 'Yes' WHERE bookID = ? AND memberID = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, Integer.parseInt(bookID));
                updateStmt.setInt(2, Integer.parseInt(memberID));
                updateStmt.executeUpdate(); // Execute the update query
            }

            JOptionPane.showMessageDialog(this, "Book returned successfully!");
            clearFields(); // Clear fields after successful return
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Book ID and Member ID.");
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error returning book: " + e.getMessage());
        }
    }

    // Method to clear all fields
    private void clearFields() {
        txtBookID.setText("");
        txtMemberID.setText("");
        txtIssueDate.setText("");
        txtDueDate.setText("");
        DateReturn.setDate(null); 
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
        btnSearch = new javax.swing.JButton();
        txtIssueDate = new javax.swing.JTextField();
        txtDueDate = new javax.swing.JTextField();
        btnReturn = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        DateReturn = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 200));
        setUndecorated(true);
        setSize(new java.awt.Dimension(700, 450));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Book ID:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Member ID:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Issue Date:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Due Date:");

        txtBookID.setBackground(new java.awt.Color(255, 255, 204));
        txtBookID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookID.setForeground(new java.awt.Color(0, 0, 0));

        txtMemberID.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberID.setForeground(new java.awt.Color(0, 0, 0));

        btnSearch.setBackground(new java.awt.Color(255, 255, 153));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(0, 0, 0));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtIssueDate.setBackground(new java.awt.Color(255, 255, 204));
        txtIssueDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtIssueDate.setForeground(new java.awt.Color(0, 0, 0));

        txtDueDate.setBackground(new java.awt.Color(255, 255, 204));
        txtDueDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtDueDate.setForeground(new java.awt.Color(0, 0, 0));

        btnReturn.setBackground(new java.awt.Color(255, 255, 153));
        btnReturn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReturn.setForeground(new java.awt.Color(0, 0, 0));
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/return book png.png"))); // NOI18N
        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Return Date:");

        DateReturn.setBackground(new java.awt.Color(255, 255, 204));
        DateReturn.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnReturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel))
                    .addComponent(txtBookID)
                    .addComponent(txtMemberID)
                    .addComponent(txtIssueDate)
                    .addComponent(txtDueDate)
                    .addComponent(DateReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIssueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(DateReturn, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReturn)
                    .addComponent(btnCancel))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose(); // Close the window
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchBookAndMember(); // Method to search book and member by their id
    }//GEN-LAST:event_btnSearchActionPerformed


    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        returnBook(); // Method to return book
    }//GEN-LAST:event_btnReturnActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReturnBookView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateReturn;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JTextField txtDueDate;
    private javax.swing.JTextField txtIssueDate;
    private javax.swing.JTextField txtMemberID;
    // End of variables declaration//GEN-END:variables
}
