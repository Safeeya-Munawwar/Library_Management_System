// @author KAN/IT/2022/F/0036
package Controller;

import java.sql.*; // Import JDBC classes for database connectivity
import java.util.ArrayList; // Imports ArrayList for dynamic list management
import java.util.List; // Imports List interface for collections
import Model.Book; // Book class for book representation

public class BookDBC {
    private Connection connection; // Connection object to manage DB connectivity

    // Default constructor initializes the DB
    public BookDBC() {
        String url = "jdbc:mysql://localhost:3306/librarydb";
        String username = "root";
        String password = "";

        // Try to establish the connection
        try {
            connection = DriverManager.getConnection(url, username, password); // Establish DB connection
            if (connection != null) {
                System.out.println("Connected to the database."); // Success message
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print exception details if connection fails
        }
    }

    // Overloaded constructor that accepts an existing connection
    public BookDBC(Connection connection) {
        this.connection = connection; // Use the provided connection instead of creating a new one
    }

    // Method to add a new book to the DB
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO NewBooks (bookID, title, author, yearPublished, price, status) VALUES (?, ?, ?, ?, ?, ?)"; // SQL query for inserting a book
        
        // Prepare SQL statement to insert new book into the database
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            statement.setInt(1, book.getBookID());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPublishedYear());
            statement.setDouble(5, book.getPrice());
            statement.setString(6, book.getStatus());
            statement.executeUpdate(); // Execute the insert operation to add the book to the DB
        }
    }

    // Method to check if a bookID already exists in the database
    public boolean doesBookExist(int bookID) throws SQLException {
        String sql = "SELECT bookID FROM NewBooks WHERE bookID = ?"; // Query to check if book ID exists
        
        // Prepare SQL statement to check if bookID exists in the database
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookID); // Set the book ID parameter
            ResultSet resultSet = statement.executeQuery(); // Execute the query
            return resultSet.next();  // Returns true if bookID exists
        }
    }

    // Method to update an existing book's information
    public void updateBook(Book book) {
        String query = "UPDATE NewBooks SET title = ?, author = ?, yearPublished = ?, price = ?, status = ? WHERE bookID = ?"; // SQL update query

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters for the prepared statement
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishedYear());
            statement.setDouble(4, book.getPrice());
            statement.setString(5, book.getStatus());
            statement.setInt(6, book.getBookID()); // Specify which book to update          
            statement.executeUpdate(); // Execute the update operation                     
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the update fails
        }
    }

    // Method to update the status of a book
    public boolean updateBookStatus(int bookId, String status) {
        String query = "UPDATE NewBooks SET status = ? WHERE bookID = ?"; // SQL query for update the book's status

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", ""); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status); // Set the new status for the book
            pstmt.setInt(2, bookId); // Set the book ID in the query
            int affectedRows = pstmt.executeUpdate(); // Execute the update

            return affectedRows > 0; // Return true if at least one row was updated (Successful)
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print the exception if query fails
            return false; // Return false if update fails
        }
    }

    // Method to delete a book from DB (Mark as unavailable)
    public void deleteBook(int bookId) {
        try {
            String query = "DELETE FROM NewBooks WHERE bookID = ?"; // SQL query to delete a book by ID
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bookId); // Set the book ID parameter
            ps.executeUpdate(); // Execute the delete operation
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the delete fails
        }
    }

    // Method to search for books based on title - Overloaded method
    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = new ArrayList<>(); // Create a list to store search results
        String sql = "SELECT * FROM newbooks WHERE title LIKE ?"; // SQL query for searching by title

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + title + "%"); // For title search
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the search query
            while (resultSet.next()) { // Process each result
                Book book = new Book(
                        resultSet.getInt("bookID"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("yearPublished"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status")
                );
                books.add(book); // Add found book to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the search fails
        }
        return books; // Return the list of found books
    }

    // Method to search for books based on title and author - Overloaded method
    public List<Book> searchBooksByTitleAndAuthor(String title, String author) {
        List<Book> books = new ArrayList<>(); // Create a list to store search results
        String sql = "SELECT * FROM newbooks WHERE title LIKE ? AND author LIKE ?"; // SQL query for searching by title and author

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + title + "%"); // For title search
            preparedStatement.setString(2, "%" + author + "%"); // For author search
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the search query
            while (resultSet.next()) { // Process each result
                Book book = new Book(
                        resultSet.getInt("bookID"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("yearPublished"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status")
                );
                books.add(book); // Add found book to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the search fails
        }
        return books; // Return the list of found books
    }
    
    // Method to search for books based on title, author, or book ID - Overloaded method
    public List<Book> searchBooksByQuery(String query) {
        List<Book> books = new ArrayList<>(); // Create a list for search results 
        String sql = "SELECT * FROM newbooks WHERE title LIKE ? OR author LIKE ? OR bookID = ?"; //Query for searching by title, author, and bookID

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%"); // For title search
            preparedStatement.setString(2, "%" + query + "%"); // For author search

            // Attempt to parse the query as an integer for bookID
            try {
                int bookId = Integer.parseInt(query);
                preparedStatement.setInt(3, bookId); // For book ID search
            } catch (NumberFormatException e) {
                preparedStatement.setInt(3, -1); // Invalid book ID will not match any ID
            }

            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the search query
            while (resultSet.next()) { // Process each result
                Book book = new Book(
                        resultSet.getInt("bookID"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("yearPublished"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status")
                );
                books.add(book); // Add found book to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the search fails
        }
        return books; // Return the list of found books
    }

    // Method to retrieve all books from the DB
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>(); // List to hold all books
        String sql = "SELECT * FROM NewBooks"; // SQL query to retrieve all books

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) { // Process each result
                int bookID = resultSet.getInt("bookID");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int publishedYear = resultSet.getInt("yearPublished");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                // Create a new Book object and add it to the list
                Book book = new Book(bookID, title, author, publishedYear, price, status);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception details if retrieving all books fails
        }
        return books; // Return the list of all books
    }

    // Method to find a book by its ID
    public Book findBookById(int bookId) {
        Book book = null; // Initialize book to null
        String sql = "SELECT * FROM NewBooks WHERE bookID = ?"; // Query to find book by ID

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId); // Set the book ID parameter
            ResultSet resultSet = statement.executeQuery(); // Execute the query

            // Check if a result was returned
            if (resultSet.next()) {
                // Create a new Book object using the result set data
                int id = resultSet.getInt("bookID");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int publishedYear = resultSet.getInt("yearPublished");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                book = new Book(id, title, author, publishedYear, price, status); // Create a new Book object
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception details if the book retrieval fails
        }
        return book; // Return the found book or null if not found
    }

}
