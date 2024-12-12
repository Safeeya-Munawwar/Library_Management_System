// @author KAN/IT/2022/F/0036
package Model;

// Interface for borrowable items
public interface Borrowable {
    void borrowBook (Member member); // Method to borrow the item
    void returnBook (Member member); // Method to return the item
    String getStatus(); // Get the current status of the item
    void setStatus(String status); // Set the current status of the item
}
