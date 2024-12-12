// @author KAN/IT/2022/F/0036
package View;

import Model.Book; // Import book class
import Controller.BookDBC; // Import BookDBC class
import java.sql.SQLException; // SQLException for handling SQL errors
import javax.swing.*; // Import the Swing library for creating GUI components

public class AddBookView extends javax.swing.JFrame {

    public AddBookView() {
        setTitle("Add New Book");
        initComponents(); // Initialize UI components
    }

    // Method to add a new book to the DB
    private void addBook() {
        try {
            // Parsing and getting input values from text fields
            int bookID = Integer.parseInt(txtBookID.getText().trim());
            String bookName = txtBookName.getText().trim();
            String authorName = txtAuthorName.getText().trim();
            int publishedYear = txtPublishedYear.getYear();  // JYearChooser used here
            double price = Double.parseDouble(txtPrice.getText().trim());  // Parse price as a double
            String status = "Available";  // Default status

            // Input validation - checking for empty fields
            if (bookName.isEmpty() || authorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all book details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Creating a new Book object (Encapsulation)
            Book book = new Book(bookID, bookName, authorName, publishedYear, price, status);

            // Using BookDBC for database interaction
            BookDBC bookDBC = new BookDBC();
            
            // Checks if the book ID already exists in the database
            if (bookDBC.doesBookExist(bookID)) {
                JOptionPane.showMessageDialog(this, "Book ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the new book to the database
            bookDBC.addBook(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearFields();
            dispose();  // Close the window after adding
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Book ID and Price.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print exception details if connection fails
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) {
            e.printStackTrace(); // Print exception details if connection fails
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to clear all fields
    public void clearFields(){
        txtBookID.setText("");
        txtBookName.setText("");
        txtAuthorName.setText("");
        txtPrice.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBookID = new javax.swing.JTextField();
        txtBookName = new javax.swing.JTextField();
        txtAuthorName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtPublishedYear = new com.toedter.calendar.JYearChooser();
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

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Book ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 77, 155, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 204));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Book Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 130, 155, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 204));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Author Name:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 183, 155, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Price:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 236, 155, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 204));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Published Year:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 284, 155, -1));

        txtBookID.setBackground(new java.awt.Color(255, 255, 204));
        txtBookID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookID.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtBookID, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 72, 300, -1));

        txtBookName.setBackground(new java.awt.Color(255, 255, 204));
        txtBookName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookName.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtBookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 125, 300, -1));

        txtAuthorName.setBackground(new java.awt.Color(255, 255, 204));
        txtAuthorName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAuthorName.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtAuthorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 178, 300, -1));

        txtPrice.setBackground(new java.awt.Color(255, 255, 204));
        txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 231, 300, -1));

        txtPublishedYear.setBackground(new java.awt.Color(255, 255, 204));
        getContentPane().add(txtPublishedYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 284, 300, 35));

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
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(301, 350, 114, -1));

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
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 350, -1, -1));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        addBook(); // Call addBook method when Save button is clicked
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false); // Close the window when Cancel button is clicked
    }//GEN-LAST:event_btnCancelActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddBookView().setVisible(true); // Make the AddBookView visible
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAuthorName;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JTextField txtBookName;
    private javax.swing.JTextField txtPrice;
    private com.toedter.calendar.JYearChooser txtPublishedYear;
    // End of variables declaration//GEN-END:variables
}
