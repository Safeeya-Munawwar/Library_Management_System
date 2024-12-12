// @author KAN/IT/2022/F/0036
package View;

import Model.Book; // Import the Book model class
import Controller.BookDBC; // Import the BookDBC class
import Model.Member; // Import Member class
import java.util.ArrayList; // Imports ArrayList for dynamic list management
import javax.swing.*; // Import Swing components for GUI
import javax.swing.table.DefaultTableModel; // Import DefaultTableModel for JTable
import java.util.List; // Import List for handling collections of Book objects

public class BookManagementView extends javax.swing.JFrame {

    private final BookDBC bookDBC; // Declare an instance of BookDBC for database operations
    private final DefaultTableModel tableModel; // Table model for the JTable

    public BookManagementView() {
        setTitle("Book Management"); //Title for window

        initComponents(); // Initialize UI components

        bookDBC = new BookDBC(); // Create an instance of BookDBC
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{
            "Book ID", "Book Name", "Author", "Published Year", "Price", "Status"
        });
        tableBook.setModel(tableModel); // Set the table model
        loadBookData(); // Load book data into the table
    }
    
    // Get the list of books from the database
    public List<Book> getBookList() {
        return bookDBC.getAllBooks(); // Return the list of all books
    }

    // Load books into the table
    private void loadBookData() {
        tableModel.setRowCount(0); // Clear existing data
        List<Book> books = bookDBC.getAllBooks(); // Retrieve all books from the database
        for (Book book : books) { // Iterate through the list of books
            tableModel.addRow(new Object[]{
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedYear(),
                book.getPrice(),
                book.getStatus()
            }); // Add each book's data as a new row in the table
        }
    }

    // Search books based on input
    private void searchBooks() {
        String query = txtSearch.getText().trim(); // Get the search query from the text field
        tableModel.setRowCount(0); // Clear existing data

        List<Book> books = new ArrayList<>();

        // Check if the query contains a comma to indicate searching by title and author
        String[] parts = query.split(",", 2);
        if (parts.length == 2) {
            books = bookDBC.searchBooksByTitleAndAuthor(parts[0].trim(), parts[1].trim()); // Search by title and author
        } else {
            books = bookDBC.searchBooksByQuery(query); // Search by title, author, or book ID
        }

        for (Book book : books) { // Iterate through the search results
            tableModel.addRow(new Object[]{
                book.getBookID(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedYear(),
                book.getPrice(),
                book.getStatus()
            }); // Add the search results as new rows in the table
        }
    }

    // Edit selected book
    private void editBook() {
        int selectedRow = tableBook.getSelectedRow(); // Get the selected row index
        
        if (selectedRow >= 0) { // Check if a row is selected
            int bookId = (int) tableModel.getValueAt(selectedRow, 0); // Get the Book ID from the selected row
            EditBookView editBookView = new EditBookView(bookId); // Create an instance of EditBookView for editing

            // Add a WindowAdapter to listen for the window close event
            editBookView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadBookData(); // Refresh the table data after the edit window is closed
                }
            });

            editBookView.setVisible(true); // Show the edit window
        } 
        else {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Warning", JOptionPane.WARNING_MESSAGE); // Show a warning dialog if no book is selected
        }
    }

    // Method to delete a book - Mark selected book as unavailable
    private void deleteBook() {
        int selectedRow = tableBook.getSelectedRow(); // Get the selected row index

        if (selectedRow >= 0) { // Check if a row is selected
            int bookId = (int) tableModel.getValueAt(selectedRow, 0); // Get the Book ID from the selected row
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to mark this book as unavailable?", "Confirm Unavailability", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) { // Confirm unavailability
                // Call the update status method in BookDBC
                boolean updated = bookDBC.updateBookStatus(bookId, "Unavailable"); // Update the book's status in the database

                if (updated) { // Check if the update was successful
                    loadBookData(); // Refresh the table after updating
                    JOptionPane.showMessageDialog(this, "Book marked as unavailable successfully."); // Show success message
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Error marking book as unavailable. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                }
            }
        } 
        else {
            // Show a warning dialog if no book is selected
            JOptionPane.showMessageDialog(this, "Please select a book to mark as unavailable.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to borrow book for member
    public void borrowBookForMember(Book book, Member member) {
        book.borrowBook(member); // Uses the method in Book to add the member to borrowedByMembers
    }

    // Method to return book for member
    public void returnBookForMember(Book book, Member member) {
        book.returnBook(member); // Uses the method in Book to remove the member from borrowedByMembers
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBook = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(180, 180));
        setMinimumSize(new java.awt.Dimension(1038, 580));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1038, 580));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Book Details");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 20, 280, -1));

        txtSearch.setBackground(new java.awt.Color(255, 255, 204));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 25, 180, -1));

        tableBook.setBackground(new java.awt.Color(255, 255, 204));
        tableBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableBook.setForeground(new java.awt.Color(0, 0, 0));
        tableBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Book ID", "Book Name", "Author Number", "Book Price", "Published Year", "Status"
            }
        ));
        jScrollPane1.setViewportView(tableBook);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 1020, 394));

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
        getContentPane().add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(911, 25, -1, -1));

        btnEdit.setBackground(new java.awt.Color(255, 255, 153));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(0, 0, 0));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-student-male-50.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        getContentPane().add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 498, 144, -1));

        btnDelete.setBackground(new java.awt.Color(255, 255, 153));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-add-user-male-50.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 498, -1, -1));

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
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(896, 498, -1, 61));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editBook(); //Call the editBook method
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteBook(); //Call the deleteBook method
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false); //Close the window
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchBooks(); //Call the searchBook method
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookManagementView().setVisible(true); // Create and show the BookManagementView
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
    private javax.swing.JTable tableBook;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
