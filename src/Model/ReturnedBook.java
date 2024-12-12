// @author KAN/IT/2022/F/0036
package Model;

import java.sql.Date; // Importing java.sql.Date

public class ReturnedBook {

    private int recordID;
    private int bookID;
    private String bookName;
    private int memberID;
    private String memberName;
    private String issuedDate;
    private String returnedDate;
    private String returnedBook;

    // Constructor
    public ReturnedBook(int recordID, int bookID, String bookName, int memberID, String memberName, String issuedDate, String returnedDate, String returnedBook) {
        this.recordID = recordID;
        this.bookID = bookID;
        this.bookName = bookName;
        this.memberID = memberID;
        this.memberName = memberName;
        this.issuedDate = issuedDate;
        this.returnedDate = returnedDate;
        this.returnedBook = returnedBook;
    }

    // Getters and Setters
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
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

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }
    
    // Overloaded method to set returned date with Date type
    public void setReturnedDate(Date sqlDate) {
        this.returnedDate = sqlDate.toString(); // Set returned date from SQL Date
    }

    public String getReturnedBook() {
        return returnedBook;
    }

    public void setReturnedBook(String returnedBook) {
        this.returnedBook = returnedBook;
    }

    // Override toString method for meaningful representation
    @Override
    public String toString() {
        return "ReturnedBook{"
                + "bookId=" + bookID
                + ", memberId=" + memberID
                + ", returnDate='" + returnedDate + '\'' // Display return date
                + '}'; // Return string representation
    }  
}
