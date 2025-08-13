# Library Management System (LMS)

## Overview
The **Library Management System** is a Java-based application designed for efficient library operations. Built using **NetBeans**, **Java Swing**, and **MySQL**, it supports role-based access, allowing **Admins** to manage books and members, while **Members** can search and view books. The system adheres to the **MVC architecture**, emphasizing modularity, object-oriented programming (OOP) principles, and robust error handling.

---

## Features

### 1. **Login System**
- **Role-Based Access:**
  - Admin: Full access to manage members and books.
  - Member: Limited access to view and search for books.
- **Login Interface:**
  - Fields for username and password.
  - Validation with error handling for invalid credentials.

### 2. **Member Management** (Admin Only)
- Add, Update, and Delete members.
- View detailed information for each member:
  - Attributes: `memberID`, `name`, `contactInfo`.
- **MembershipCard Composition**:
  - Each member is associated with a `MembershipCard` that includes `cardNumber` and `expirationDate`.

### 3. **Book Management** (Admin Only)
- Add, Update, and Delete books.
- View detailed information for each book:
  - Attributes: `bookID`, `title`, `author`, `yearPublished`.
- **Search Books** using method overloading:
  - `searchBooks(String title)`.
  - `searchBooks(String title, String author)`.

### 4. **Borrowing and Aggregation**
- Members can borrow books, establishing an aggregation relationship:
  - Books are associated with one or more members without tightly coupling the two classes.

### 5. **Exception Handling**
- Handles scenarios such as:
  - Invalid login attempts.
  - Empty fields during member or book entry.
  - Unauthorized access to Admin features.

---

## Architecture
The system follows the **Model-View-Controller (MVC)** design pattern:

### 1. **Model Layer**
- Represents the application's data and business logic.
- Includes classes:
  - `Member`
  - `MembershipCard`
  - `Book`

### 2. **View Layer**
- Manages the graphical user interface (GUI).
- Includes screens for:
  - Login
  - Member Management
  - Book Management

### 3. **Controller Layer**
- Handles interactions between the Model and View layers.
- Processes user input and updates the View accordingly.

---

## Object-Oriented Programming Concepts
- **Encapsulation:** Private fields with public getters and setters.
- **Inheritance:**
  - A `User` base class for both `Admin` and `Member`.
- **Polymorphism:**
  - Overriding `displayDetails()` in both `Member` and `Book` classes.
- **Abstraction:**
  - Common behaviors defined in an abstract class or interface.
- **Aggregation:**
  - Relationship between `Book` and `Member` (borrowed books).
- **Composition:**
  - `MembershipCard` class within `Member` class.

---

## Graphical User Interface (GUI)
- **Login Screen:** Simple authentication interface.
- **Main Menu:** Displays features based on the user's role.
- **Member Management Screens:**
  - Add, update, delete, and view members.
- **Book Management Screens:**
  - Add, update, delete, search, and view books.
- **Details Display:** Uses the `displayDetails()` method to show member and book details.

---

## Documentation
### UML Diagrams
1. **Use Case Diagram:** Actions available to Admin and Member roles.
2. **Class Diagram:** Relationships between `Member`, `MembershipCard`, and `Book`.

### ER Diagram
- Illustrates the relationships between:
  - `Members`
  - `Books`
  - `BorrowedBooks`

### User Manual
- Includes screenshots of the GUI with detailed instructions for Admin and Member roles.

---

## Project Report
```bash
https://github.com/Shafiya-Munawwar0036/Library_Management_System/blob/main/Library%20Management%20System%20Project%20Report.pdf
```

---

## Installation and Setup
1. Clone the repository:
   ```bash
   https://github.com/Shafiya-Munawwar0036/Library_Management_System.git
   ```
2. Open the project in **NetBeans**.
3. Configure the database in **MySQL**:
   - Import the provided `library.sql` file.
4. Run the project from NetBeans.
5. Login using the following credentials:
   - **Admin:**
     - Username: `admin`
     - Password: `admin`
   - **Member:**
     - Username: `member`
     - Password: `member`

---

## Author
**Safeeya Munawwar**  
<p>
  <a href="https://www.linkedin.com/in/safeeya-munawwar" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
  </a>
  <a href="https://github.com/Safeeya-Munawwar" target="_blank">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
  </a>
  <a href="mailto:shafiyasha0036@gmail.com" target="_blank">
    <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Email"/>
  </a>
  <a href="https://safeeya-munawwar-personal-portfolio.vercel.app/" target="_blank">
    <img src="https://img.shields.io/badge/Portfolio-0A66C2?style=for-the-badge&logo=firefox&logoColor=white" alt="Portfolio"/>
  </a>
</p>

---

© 2024 LMS | Built with ❤️ using Java, MySQL
