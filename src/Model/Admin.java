// @author KAN/IT/2022/F/0036
package Model;

import Controller.MemberDBC; // Import MemberDBC class for managing members in the database
import Controller.BookDBC; // Import BookDBC class for managing books in the database
import java.sql.SQLException; // SQL exception class for handling database errors

// Admin class extends User class
public class Admin extends User {

    // Constructor that initializes Admin with username and password
    public Admin(String username, String password) {
        super(username, password); // Call to the parent (User) constructor with username and password
    }

    // Admin methods for managing members

    // Add a new member to the database
    public void addMember(Member member, MemberDBC memberDBC) throws SQLException {
        memberDBC.addMember(member); // Delegate to MemberDBC to add a member to the database
    }

    // Update an existing member's details in the database
    public void updateMember(Member member, MemberDBC memberDBC) {
        memberDBC.updateMember(member); // Delegate to MemberDBC to update member information
    }

    // Delete a member from the database by their member ID
    public void deleteMember(int memberId, MemberDBC memberDBC) {
        memberDBC.deleteMember(memberId); // Delegate to MemberDBC to delete a member by their ID
    }

    // Admin methods for managing books

    // Add a new book to the database
    public void addBook(Book book) {
        BookDBC bookDBC = new BookDBC(); // Create an instance of BookDBC to interact with the books database
        try {
            bookDBC.addBook(book); // Delegate to BookDBC to add a book to the database
        } 
        catch (SQLException e) {  
            // Handle any SQL exceptions that may occur during the book addition process
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // Update an existing book's details in the database
    public void updateBook(Book book, BookDBC bookDBC) {
        bookDBC.updateBook(book); // Delegate to BookDBC to update the book information in the database
    }

    // Delete a book from the database by its book ID
    public void deleteBook(int bookId, BookDBC bookDBC) {
        bookDBC.deleteBook(bookId); // Delegate to BookDBC to delete a book by its ID
    }
}
