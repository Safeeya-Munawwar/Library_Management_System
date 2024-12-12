// @author KAN/IT/2022/F/0036
package View;

import java.sql.Connection; // Establishing a connection to the DB
import java.sql.PreparedStatement; // Executing queries with parameters
import java.sql.ResultSet; // Used to retrieve data returned by executing a SELECT SQL query
import java.sql.SQLException; // Handles exceptions related to SQL operations
import java.util.ArrayList; // Imports ArrayList for dynamic list management
import Controller.DBConnection; // Imports DBConnection class
import java.util.Date; // Import date
import javax.swing.table.DefaultTableModel; // For JTable

public class StatisticsView extends javax.swing.JFrame {
    
    // Constructor to initialize components and load datas
    public StatisticsView() {
        initComponents();
        loadIssuedBooks(); // Load data into the Issued Book table
        loadReturnedBooks(); // Load data into the Returned Book table
    }

    // Method to load data into the Issued Book table
    private void loadIssuedBooks() {
        try (Connection conn = DBConnection.getConnection()) {
            
            // Check if connection is established
            if (conn == null) {
                System.err.println("Failed to make connection!");
                return; // Exit if the connection is null
            }
            
            // SQL query to select all data from the IssueBook table
            String query = "SELECT * FROM issuebook";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Get the table model and clear existing data
            DefaultTableModel model = (DefaultTableModel) tableIssuedBook.getModel();
            model.setRowCount(0);  // Clear existing rows
            int rowNumber = 1;

            ArrayList<Object[]> issuedBooksData = new ArrayList<>();
            
            // Loop through the result set and add data to the list
            while (rs.next()) {
                Date dueDate = rs.getDate("dueDate");
                String returnStatus = rs.getString("returnBook");

                // Mark as overdue if past due date and not returned book
                if ("No".equals(returnStatus) && dueDate != null && dueDate.before(new java.sql.Date(System.currentTimeMillis()))) {
                    returnStatus = "Overdue";
                }

                issuedBooksData.add(new Object[]{
                    rowNumber++,
                    rs.getInt("bookID"),
                    rs.getString("bookName"),
                    rs.getInt("memberID"),
                    rs.getString("memberName"),
                    rs.getDate("issueDate"),
                    dueDate,
                    returnStatus
                });
            }
             // Update the table with the got data
            updateIssuedBookTable(issuedBooksData);
        } 
        catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to load data into the Returned Book table
    private void loadReturnedBooks() {
        try (Connection conn = DBConnection.getConnection()) {
            
            // Check if connection is established
            if (conn == null) {
                System.err.println("Failed to make connection!");
                return; // Exit if the connection is null
            }
            
            // SQL query to select all data from the ReturnedBooks table
            String query = "SELECT * FROM returnedbooks";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

             // Get the table model and clear existing data
            DefaultTableModel model = (DefaultTableModel) tableReturnedBooks.getModel();
            model.setRowCount(0);  // Clear existing rows
            int rowNumber = 1;

            ArrayList<Object[]> returnedBooksData = new ArrayList<>();
            
            // Loop through the result set and add data to the list
            while (rs.next()) {
                returnedBooksData.add(new Object[]{
                    rowNumber++,
                    rs.getInt("bookID"),
                    rs.getString("bookName"),
                    rs.getInt("memberID"),
                    rs.getString("memberName"),
                    rs.getDate("issuedDate"),
                    rs.getDate("returnedDate"),
                    rs.getString("returnedBook")
                });
            }
            // Update the table with the got data
            updateReturnedBooksTable(returnedBooksData);
        } 
        catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Method to load data into the Issued Book table with searchText
    private void loadIssuedBooks(String searchText) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query for search functionality
            String query = "SELECT * FROM issuebook WHERE bookID LIKE ? OR bookName LIKE ? OR memberID LIKE ? OR memberName LIKE ? OR issueDate LIKE ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            String likeSearchText = "%" + searchText + "%"; // Add wildcard for LIKE clause
            pstmt.setString(1, likeSearchText);
            pstmt.setString(2, likeSearchText);
            pstmt.setString(3, likeSearchText);
            pstmt.setString(4, likeSearchText);
            pstmt.setString(5, likeSearchText);

            ResultSet rs = pstmt.executeQuery();

            // List to store search results
            ArrayList<Object[]> issuedBooksData = new ArrayList<>();
            while (rs.next()) {
                issuedBooksData.add(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("bookID"),
                    rs.getString("bookName"),
                    rs.getInt("memberID"),
                    rs.getString("memberName"),
                    rs.getDate("issueDate"),
                    rs.getDate("dueDate"),
                    rs.getString("returnBook")
                });
            }

             // Update the table with the search results
            updateIssuedBookTable(issuedBooksData);
        } 
        catch (SQLException e) {
            e.printStackTrace();   // Handle SQL exceptions
        }
    }

     // Method to load data into the Returned Book table with searchText
    private void loadReturnedBooks(String searchText) {
        try (Connection conn = DBConnection.getConnection()) {
              // SQL query for search functionality
            String query = "SELECT * FROM returnedbooks WHERE bookID LIKE ? OR bookName LIKE ? OR memberID LIKE ? OR memberName LIKE ? OR returnedDate LIKE ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            String likeSearchText = "%" + searchText + "%"; // Add wildcard for LIKE clause
            pstmt.setString(1, likeSearchText);
            pstmt.setString(2, likeSearchText);
            pstmt.setString(3, likeSearchText);
            pstmt.setString(4, likeSearchText);
            pstmt.setString(5, likeSearchText);

            ResultSet rs = pstmt.executeQuery();

             // List to store search results
            ArrayList<Object[]> returnedBooksData = new ArrayList<>();
            
            while (rs.next()) {
                returnedBooksData.add(new Object[]{
                    rs.getInt("recordID"),
                    rs.getInt("bookID"),
                    rs.getString("bookName"),
                    rs.getInt("memberID"),
                    rs.getString("memberName"),
                    rs.getDate("issuedDate"),
                    rs.getDate("returnedDate"),
                    rs.getString("returnedBook")
                });
            }

            // Update the table with the search results
            updateReturnedBooksTable(returnedBooksData);
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

     // Method to update the Issued Book table with new data
    private void updateIssuedBookTable(ArrayList<Object[]> data) {
        DefaultTableModel model = (DefaultTableModel) tableIssuedBook.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Add new rows to the table
        for (Object[] row : data) {
            model.addRow(row); // Add new rows
        }
    }

    // Method to update the Returned Book table with new data
    private void updateReturnedBooksTable(ArrayList<Object[]> data) {
        DefaultTableModel model = (DefaultTableModel) tableReturnedBooks.getModel();
        model.setRowCount(0); // Clear existing rows
        
         // Add new rows to the table
        for (Object[] row : data) {
            model.addRow(row); // Add new rows
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableIssuedBook = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableReturnedBooks = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Book ID", "Book Name", "Member ID", "Member Name", "Issue Date", "Due Date", "Return Book"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(180, 140));
        setMaximumSize(new java.awt.Dimension(1104, 631));
        setMinimumSize(new java.awt.Dimension(1104, 631));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1104, 631));

        jPanel1.setBackground(new java.awt.Color(0, 0, 29));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Issued Book Details");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(459, 67, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Search:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(672, 24, -1, -1));

        txtSearch.setBackground(new java.awt.Color(255, 255, 204));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(746, 19, 209, -1));

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
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(967, 19, -1, -1));

        tableIssuedBook.setBackground(new java.awt.Color(255, 255, 204));
        tableIssuedBook.setForeground(new java.awt.Color(0, 0, 0));
        tableIssuedBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Number", "Book ID", "Book Name", "Member ID", "Member Name", "Issued Date", "Due Date", "Return Book"
            }
        ));
        jScrollPane1.setViewportView(tableIssuedBook);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 98, 1066, 214));

        tableReturnedBooks.setBackground(new java.awt.Color(255, 255, 204));
        tableReturnedBooks.setForeground(new java.awt.Color(0, 0, 0));
        tableReturnedBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Number", "Book ID", "Book Name", "Member ID", "Member Name", "Issued Date", "Returned Date", "Return Book"
            }
        ));
        jScrollPane3.setViewportView(tableReturnedBooks);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 355, 1066, 214));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Returned Book Details");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 324, -1, -1));

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
        jPanel1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(954, 581, 128, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose(); // Close the window
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchText = txtSearch.getText().trim();

        // Load issued books based on search criteria
        loadIssuedBooks(searchText);

        // Load returned books based on search criteria
        loadReturnedBooks(searchText);
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StatisticsView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tableIssuedBook;
    private javax.swing.JTable tableReturnedBooks;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
