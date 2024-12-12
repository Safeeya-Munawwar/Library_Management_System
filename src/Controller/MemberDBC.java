// @author KAN/IT/2022/F/0036
package Controller;

import Model.Member; // Import the Member class
import Model.MembershipCard; // Import the MembershipCard class
import java.sql.*; // Import JDBC classes for database connectivity
import java.util.ArrayList; // Imports ArrayList for dynamic list management
import java.util.List; // Imports List interface for collections

public class MemberDBC {

    private Connection connection; // Encapsulated database connection

    // Constructor to establish database connection
    public MemberDBC() {
        String url = "jdbc:mysql://localhost:3306/librarydb";
        String username = "root";
        String password = "";

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, username, password); // Establish DB connection
            if (connection != null) {
                System.out.println("Connected to the database."); // Success message
            }
        } 
        catch (SQLException e) {
            System.out.println("Connection failed! Check your database credentials and existence.");
            e.printStackTrace(); // Print exception details if connection fails
        }
    }

    // Method to retrieve all members from the DB
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>(); // Create a list to store all members
        String sql = "SELECT * FROM NewMembers"; // SQL query to fetch all members

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            // Process the result set
            while (rs.next()) {
                // Extract member details
                int memberId = rs.getInt("memberID");
                String name = rs.getString("name");
                String contactInfo = rs.getString("contactInfo");
                String address = rs.getString("address");
                String cardNumber = rs.getString("cardNumber");
                Date expirationDate = rs.getDate("expirationDate");
                String membershipStatus = rs.getString("membershipStatus");
                        

                // Create a MembershipCard object
                MembershipCard membershipCard = new MembershipCard(cardNumber, expirationDate);
                
                // Create a Member object and add it to the list
                Member member = new Member(memberId, name, contactInfo, address, membershipCard, membershipStatus);
                members.add(member);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return members; // Return the list of members
    }

    // Method to add member
    public boolean addMember(Member member) {
        String insertSQL = "INSERT INTO NewMembers (memberID, name, contactInfo, address, cardNumber, expirationDate, membershipStatus) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Query for insert member details
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, member.getMemberID());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getContactInfo());
            pstmt.setString(4, member.getAddress());
            pstmt.setString(5, member.getMembershipCard().getCardNumber());
            pstmt.setDate(6, new java.sql.Date(member.getExpirationDate().getTime()));
            pstmt.setString(7, "Active"); // Default status
            return pstmt.executeUpdate() > 0; // Returns true if insertion was successful
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return false;
        }
    }

    // Method to check if a member exists in the DB by ID
    public boolean doesMemberExist(int memberID) throws SQLException {
        String sql = "SELECT memberID FROM NewMembers WHERE memberID = ?"; // Query to check if memberID exists
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberID); // Set parameter
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Return true if a member exists
        }
    }

    // Method to check if a card number exists in the DB
    public boolean doesCardNumberExist(String cardNumber) throws SQLException {
        String sql = "SELECT cardNumber FROM NewMembers WHERE cardNumber = ?"; // Query to check if cardNumber exists
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cardNumber); // Set parameter
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Return true if card number exists
        }
    }

    // Method to update an existing member's details
    public boolean updateMember(Member member) {
        String sql = "UPDATE NewMembers SET name = ?, contactInfo = ?, address = ?, cardNumber = ?, expirationDate = ? WHERE memberID = ?"; // Query to update member

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getContactInfo());
            pstmt.setString(3, member.getAddress());
            pstmt.setString(4, member.getMembershipCard().getCardNumber());
            pstmt.setDate(5, (Date) member.getMembershipCard().getExpirationDate());
            pstmt.setInt(6, member.getMemberID());
            int rowsUpdated = pstmt.executeUpdate(); // Execute the update

            return rowsUpdated > 0; // Return true if the update was successful
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return false; // Return false if an error occurs
        }
    }

    // Method to update members status
    public boolean updateMemberStatus(int memberId, String membershipStatus) {
        String query = "UPDATE NewMembers SET membershipStatus = ? WHERE memberID = ?"; // SQL query to update the member's status

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", ""); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, membershipStatus); // Set the status in the query
            pstmt.setInt(2, memberId); // Set the member ID in the query
            int affectedRows = pstmt.executeUpdate(); // Execute the update

            return affectedRows > 0; // Return true if at least one row was updated
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print the exception for debugging
            return false; // Return false if there was an error
        }
    }

    // Method to delete a member (Mark as inactive)
    public void deleteMember(int memberId) {
        String sql = "DELETE FROM NewMembers WHERE memberID = ?"; // Query to delete member
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId); // Set parameter
            statement.executeUpdate(); // Execute the delete
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

    // Method to find a member by ID
    public Member findMemberById(int memberId) {
        Member member = null;
        String sql = "SELECT * FROM NewMembers WHERE memberID = ?"; // Query to find member by ID
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId); // Set parameter
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Extract member details
                String name = resultSet.getString("name");
                String contactInfo = resultSet.getString("contactInfo");
                String address = resultSet.getString("address");
                String cardNumber = resultSet.getString("cardNumber");
                Date expirationDate = resultSet.getDate("expirationDate");
                String membershipStatus = resultSet.getString("membershipStatus");

                // Create a MembershipCard and Member object
                MembershipCard membershipCard = new MembershipCard(cardNumber, expirationDate);
                member = new Member(memberId, name, contactInfo, address, membershipCard, membershipStatus);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return member; // Return the found member
    }

    // Method to search members by ID, name, card number
    public List<Member> searchMembers(String query) {
        List<Member> members = new ArrayList<>(); // Create a list to store search results
        String sql = "SELECT * FROM NewMembers WHERE memberID LIKE ? OR name LIKE ? OR cardNumber LIKE ?"; // Query to search member by id, name, and cardNumber

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the search
            while (resultSet.next()) {
                // Create a MembershipCard and Member object for each result
                MembershipCard membershipCard = new MembershipCard(
                        resultSet.getString("cardNumber"),
                        resultSet.getDate("expirationDate")
                );

                Member member = new Member(
                        resultSet.getInt("memberID"),
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("contactInfo"),
                        resultSet.getString("address"),
                        membershipCard,
                        resultSet.getString("membershipStatus")
                );
                members.add(member); // Add member to the list
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return members; // Return the list of found members
    }

}
