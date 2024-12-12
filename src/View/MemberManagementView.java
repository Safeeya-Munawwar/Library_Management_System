// @author KAN/IT/2022/F/0036
package View;

import Controller.DBConnection; // Import DBConnection class
import Controller.MemberDBC; // Import MemberDBC class
import javax.swing.*; // Import swing for GUI components
import javax.swing.table.DefaultTableModel; // Import for table model handling
import java.sql.Connection; // Import for SQL connection handling
import java.sql.DriverManager; // DriverManager to manage the DB connection
import java.sql.PreparedStatement; // Import for executing prepared SQL statements
import java.sql.ResultSet; // Import for handling results from SQL queries
import java.sql.SQLException; // Import for handling SQL exceptions
import java.util.Date; // Import for handling date objects

public class MemberManagementView extends javax.swing.JFrame {

    private Connection connection; // Variable to hold the database connection
    private final MemberDBC memberDBC; // Instance of MemberDBC for database operations
    private final DefaultTableModel tableModel; // Model for the table to display members

    // Constructor for MemberManagementView
    public MemberManagementView() {

        // Establish the database connection using JDBC
        try {
            String url = "jdbc:mysql://localhost:3306/librarydb";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password); // Establish the connection
            System.out.println("Connected to the database."); // Confirmation message
        } 
        catch (SQLException e) {
            // Handle exceptions during connection
            System.out.println("An error occurred while connecting to the database: " + e.getMessage());
        }

        setTitle("Member Management"); // Title for window
        initComponents(); // Initialize UI components

        memberDBC = new MemberDBC(); // Create an instance of MemberDBC for member operations

        // Initialize the table model with column names
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{
            "Member ID", "Name", "Contact Info", "Address", "Card Number", "Expiration Date", "Membership Status"
        });

        tableMember.setModel(tableModel); // Set the table model
        loadMemberData(); // Load member data into the table
    }

    // Method to add a new member to the database
    public void addMember(String name, String contactInfo, String address, String cardNumber, Date expirationDate, String membershipStatus) throws SQLException {
        int newMemberId = getNextMemberId();
        String insertSQL = "INSERT INTO NewMembers (memberID, name, contactInfo, address, cardNumber, expirationDate, membershipStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, newMemberId);
            pstmt.setString(2, name);
            pstmt.setString(3, contactInfo);
            pstmt.setString(4, address);
            pstmt.setString(5, cardNumber);
            pstmt.setDate(6, new java.sql.Date(expirationDate.getTime()));
            pstmt.setString(7, membershipStatus);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Member added successfully!"); // Success message
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding member: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to get the next available member ID
    private int getNextMemberId() {
        // Fetch the maximum member ID from the database
        try {
            String query = "SELECT MAX(memberID) FROM NewMembers"; // SQL query to get the maximum member ID
            PreparedStatement pstmt = connection.prepareStatement(query); // Prepare the statement
            ResultSet rs = pstmt.executeQuery(); // Execute the query

            if (rs.next()) {
                return rs.getInt(1) + 1; // Increment the maximum ID found
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return 1; // Fallback if there are no members
    }

    // Method to load member data into the table
    private void loadMemberData() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM NewMembers";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0); // Clear existing data

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("memberID"),
                    rs.getString("name"),
                    rs.getString("contactInfo"),
                    rs.getString("address"),
                    rs.getString("cardNumber"),
                    rs.getDate("expirationDate"),
                    rs.getString("membershipStatus")
                });
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load member data: " + e.getMessage());
        }
    }
    
    // Method to show error message when failed to load member datas
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println(message); 
    }

    // Method to search for members based on a search value
    public void searchMember(String searchValue) {
        if (searchValue.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search value.", "Warning", JOptionPane.WARNING_MESSAGE);
            return; // Stop if the search value is empty
        }
        
        try {
            String searchQuery = "SELECT * FROM NewMembers WHERE memberID = ? OR name LIKE ? OR cardNumber LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            Integer memberId = null;

            try {
                memberId = Integer.valueOf(searchValue);
            } 
            catch (NumberFormatException e) {
                // Not an integer, handle it below
            }

            stmt.setObject(1, memberId);
            stmt.setString(2, "%" + searchValue + "%");
            stmt.setString(3, "%" + searchValue + "%");

            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("memberID"),
                    rs.getString("name"),
                    rs.getString("contactInfo"),
                    rs.getString("address"),
                    rs.getString("cardNumber"),
                    rs.getDate("expirationDate"),
                    rs.getString("membershipStatus")
                });
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No members found with: " + searchValue, "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Search failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to edit a selected member
    private void editMember() {
        int selectedRow = tableMember.getSelectedRow(); // Get the selected row index

        if (selectedRow >= 0) {
            int memberId = (int) tableModel.getValueAt(selectedRow, 0); // Get the member ID from the selected row
            EditMemberView editMemberView = new EditMemberView(this, memberId); // Open the edit view
            editMemberView.setVisible(true); // Make the edit view visible

            // Refresh table after closing the edit window
            editMemberView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadMemberData(); // Refresh table after editing
                }
            });
        } 
        else {
            // Show warning if no member is selected for editing
            JOptionPane.showMessageDialog(this, "Please select a member to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Method to delete a member (Mark as Inactive)
    private void deleteMember() {
        int selectedRow = tableMember.getSelectedRow(); // Get the selected row index

        if (selectedRow >= 0) { // Check if a row is selected
            int memberId = (int) tableModel.getValueAt(selectedRow, 0); // Get the Member ID from the selected row

            // Confirm inactivity with a dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member (Mark as Inactive)?", "Confirm Inactivity", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) { // If the user confirms inactivity
                // Update member's status to "Inactive" in the database
                boolean dbUpdated = updateMemberStatusInDatabase(memberId, "Inactive");

                // If database update was successful, update the JTable directly
                if (dbUpdated) {
                    updateMemberStatusInTable(memberId, "Inactive");
                    JOptionPane.showMessageDialog(this, "Member marked as Inactive successfully.");
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Error marking member as Inactive in database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } 
        else {
            JOptionPane.showMessageDialog(this, "Please select a member to mark as Inactive.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Update membership status in the database
    private boolean updateMemberStatusInDatabase(int memberID, String status) {
        try {
            String query = "UPDATE NewMembers SET membershipStatus = ? WHERE memberID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, status); // Set status
            stmt.setInt(2, memberID);  // Set member ID
            return stmt.executeUpdate() > 0; // Return true if successful
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update membership status in the JTable
    private void updateMemberStatusInTable(int memberID, String status) {
        // Loop through all rows in the table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            // Get the MemberID from the first column (assuming MemberID is in column 0)
            int tableMemberID = (int) tableModel.getValueAt(i, 0);

            // Check if the current row matches the given MemberID
            if (tableMemberID == memberID) {
                // Update the membership status in the specified column (assumed to be column 6)
                tableModel.setValueAt(status, i, 6);  // 6th column for membership status

                // Optionally, notify the table that the data has changed
                tableModel.fireTableCellUpdated(i, 6);  // Notify that a specific cell has been updated

                break;  // Exit the loop after finding and updating the member
            }  
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMember = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(180, 180));
        setMaximumSize(new java.awt.Dimension(1099, 573));
        setMinimumSize(new java.awt.Dimension(1099, 573));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1099, 573));
        setSize(new java.awt.Dimension(1099, 573));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Member Details");

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

        tableMember.setBackground(new java.awt.Color(255, 255, 204));
        tableMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableMember.setForeground(new java.awt.Color(0, 0, 0));
        tableMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Member ID", "Member Name", "Contact Number", "Address", "Membership Card Number", "Expiration Date", "Membership Status"
            }
        ));
        jScrollPane1.setViewportView(tableMember);

        btnEdit.setBackground(new java.awt.Color(255, 255, 153));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(0, 0, 0));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-add-book-48.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 255, 153));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-book-philosophy-50.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtSearch.setBackground(new java.awt.Color(255, 255, 204));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(0, 0, 0));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearch)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit))
                .addContainerGap(43, Short.MAX_VALUE))
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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchValue = txtSearch.getText().trim();
        searchMember(searchValue); // Call the searchMember method
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editMember(); // Call the editMember method
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteMember(); // Call the deleteMember method
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false); // Close the frame
    }//GEN-LAST:event_btnCancelActionPerformed

    // Main method to run the Member Management application
    public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MemberManagementView().setVisible(true); // Create and show the MemberManagement
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableMember;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
