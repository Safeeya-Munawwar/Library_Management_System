// @author KAN/IT/2022/F/0036
package View;

import Model.Book; //Import Book class
import Controller.BookDBC; //Import BookDBC class
import javax.swing.*; // Import swing components for GUI

public class EditBookView extends javax.swing.JFrame {

    private final BookDBC bookDBC; // Instance of BookDBC to interact with the database
    private final int bookId; // Book ID to be edited
    
    // Constructor for EditBookView
    public EditBookView(int bookId) {
        this.bookId = bookId; // Initialize the bookId
        bookDBC = new BookDBC(); // Create a new instance of BookDBC

        setTitle("Edit Book"); //Title for the JFrame
        initComponents(); // Initialize components
        loadBookData(); // Load book details for the given book ID
    }
    
    // Method to load book data from the database
    private void loadBookData() {
        Book book = bookDBC.findBookById(bookId); // Find the book by ID
        
        if (book != null) {
            // Set the text fields with the book details
            txtBookID.setText(String.valueOf(book.getBookID()));
            txtBookID.setEditable(false);
            txtBookName.setText(book.getTitle());
            txtAuthorName.setText(book.getAuthor());
            txtPrice.setText(String.valueOf(book.getPrice()));
            txtPublishedYear.setYear(book.getPublishedYear());
        } 
        else {
            // Show an error message if the book is not found
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); //Close the window
        }
    }

    // Save the edited book to the database
    private void saveBook() {
        try {
            int bookID = Integer.parseInt(txtBookID.getText().trim());
            String bookName = txtBookName.getText().trim();
            String authorName = txtAuthorName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int publishedYear = txtPublishedYear.getYear();  // Get the selected year from JYearChooser
            String status = "Available"; // Hardcoded for now 

            // Create the Book object
            Book book = new Book(bookID, bookName, authorName, publishedYear, price, status);

            // Update the book in the database
            bookDBC.updateBook(book);

            // Show a success message
            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            dispose();  // Close the edit window
        } 
        catch (NumberFormatException e) {
            // Show an error message if input values are invalid
            JOptionPane.showMessageDialog(this, "Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging 
            JOptionPane.showMessageDialog(this, "An error occurred while updating the book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPublishedYear = new com.toedter.calendar.JYearChooser();
        jLabel5 = new javax.swing.JLabel();
        txtBookID = new javax.swing.JTextField();
        txtBookName = new javax.swing.JTextField();
        txtAuthorName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 200));
        setUndecorated(true);
        setSize(new java.awt.Dimension(700, 450));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Book ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 77, 156, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Book Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 130, 156, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Author Name:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 183, 156, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Price:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 236, 156, -1));

        txtPublishedYear.setBackground(new java.awt.Color(255, 255, 204));
        getContentPane().add(txtPublishedYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 284, 275, 35));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Published Year:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 284, 156, -1));

        txtBookID.setBackground(new java.awt.Color(255, 255, 204));
        txtBookID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookID.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtBookID, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 72, 275, -1));

        txtBookName.setBackground(new java.awt.Color(255, 255, 204));
        txtBookName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtBookName.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtBookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 125, 275, -1));

        txtAuthorName.setBackground(new java.awt.Color(255, 255, 204));
        txtAuthorName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAuthorName.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtAuthorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 178, 275, -1));

        txtPrice.setBackground(new java.awt.Color(255, 255, 204));
        txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 231, 275, -1));

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
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 337, -1, -1));

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
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 337, -1, -1));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveBook(); // Call the method to save the book details
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false); //Close the window
    }//GEN-LAST:event_btnCancelActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditBookView(1).setVisible(true); // Replace 1 with the actual book ID you want to edit
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
