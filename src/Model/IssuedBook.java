// @author KAN/IT/2022/F/0036
package Model;

import java.time.LocalDate; // Importing LocalDate

public class IssuedBook {
    private int id;
    private int bookID;
    private String bookName;
    private int memberID;
    private String memberName;
    private LocalDate issueDate; // Changed to LocalDate
    private LocalDate dueDate; // Changed to LocalDate
    private String returnBook;

    // Constructors
    public IssuedBook(int id, int bookID, String bookName, int memberID, String memberName, LocalDate issueDate, LocalDate dueDate, String returnBook) {
        this.id = id;
        this.bookID = bookID;
        this.bookName = bookName;
        this.memberID = memberID;
        this.memberName = memberName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnBook = returnBook;
    }

    public IssuedBook(int bookID, int memberID, LocalDate issueDate, LocalDate dueDate) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnBook() {
        return returnBook;
    }

    public void setReturnBook(String returnBook) {
        this.returnBook = returnBook;
    }

    // Override toString method for string representation of IssuedBook object
    @Override
    public String toString() {
        return "IssuedBook{"
                + "recordID=" + id
                + ", bookID=" + bookID
                + ", bookName='" + bookName + '\''
                + ", memberID=" + memberID
                + ", memberName='" + memberName + '\''
                + ", issueDate=" + issueDate
                + ", dueDate=" + dueDate
                + ", returnedBook='" + returnBook + '\''
                + '}';
    }
}
