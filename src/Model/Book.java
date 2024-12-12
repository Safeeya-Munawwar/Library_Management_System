// @author KAN/IT/2022/F/0036
package Model;

import java.util.ArrayList; // Imports ArrayList for dynamic list management
import java.util.List; // Imports List interface for collections

// The Book class represents a book that can be borrowed
public class Book implements Borrowable {
    private final int bookID; // Unique identifier for the book
    private final String title;
    private final String author;
    private final int publishedYear;
    private final double price;
    private String status;
    private final List<Member> borrowedByMembers; // Aggregation relationship -  List to store the members who borrowed the book
    
    // Constructor to initialize a Book object
    public Book(int bookID, String title, String author, int publishedYear, double price, String status) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.price = price;
        this.status = status;
        this.borrowedByMembers = new ArrayList<>(); // Initialize the list for borrowed members
    }

    // Getters for accessing private properties
    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String getStatus() {
        return status;
    }

    // Setter
    @Override
    public void setStatus(String status) {
        this.status = status; // Update status 
    }

   // Method to allow a member to borrow the book
    @Override
    public void borrowBook(Member member) {
        if (!borrowedByMembers.contains(member)) { // Check if member already borrowed
            borrowedByMembers.add(member); // Add member to the list
            setStatus("Issued"); // Update status to issued
        }
    }

    // Method to allow a member to return the book
    @Override
    public void returnBook(Member member) {
        if (borrowedByMembers.contains(member)) { // Check if the member has borrowed the book
            borrowedByMembers.remove(member); // Remove the member from the list of borrowed members
            
            // Update the status if no members have borrowed the book
            if (borrowedByMembers.isEmpty()) {
                setStatus("Available"); // Change status to "Available" if no one is borrowing it
            }
        }
    }

    // Get the list of members who borrowed the book
    public List<Member> getBorrowedByMembers() {
        return borrowedByMembers;
    }

    // Display the details of the book, including borrowed members
    public void displayDetails() {
        System.out.println("Book ID: " + bookID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year Published: " + publishedYear);
        System.out.println("Price: " + price);
        System.out.println("Status: " + status); // Include status
        System.out.println("Borrowed By Members: ");
        for (Member member : borrowedByMembers) {
            System.out.println("- " + member.getName()); // Displaying member names
        }
    }

    // Override toString method for string representation of Book object
    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
