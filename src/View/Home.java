// @author KAN/IT/2022/F/0036
package View;

import java.awt.event.ActionEvent; // Handles events triggered by user actions, like button clicks
import java.awt.event.ActionListener; // Defines what happens when an action event occurs, such as a button being clicked

public class Home extends javax.swing.JFrame {

    private StatisticsView statisticsView; // Instance of StatisticsView for statistics functionality
    
    // Constructor for Home class
    public Home(boolean isAdmin) {

        setTitle("Library Management System - Home"); // Set the title of the JFrame

        statisticsView = new StatisticsView(); // Create an instance of StatisticsView

        initComponents(); // Call the method to initialize UI components

        // Only allow admin to access member management, book management, issue, return, and statistics
        btnAddMember.setVisible(isAdmin);
        btnMemberManagement.setVisible(isAdmin);
        btnAddBook.setVisible(isAdmin);
        btnBookManagement.setVisible(isAdmin);
        btnIssueBook.setVisible(isAdmin);
        btnReturnBook.setVisible(isAdmin);
        btnStatistics.setVisible(isAdmin);
    
        // Add action listeners for each button
        
        btnAddMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Add Member screen
                new AddMemberView().setVisible(true);
            }
        });

        btnMemberManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Member Management screen
                new MemberManagementView().setVisible(true);
            }
        });

        btnAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Add Book screen
                new AddBookView().setVisible(true);
            }
        });

        btnBookManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Book Management screen
                new BookManagementView().setVisible(true);
            }
        });

        btnIssueBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Issue Book screen, passing the StatisticsView instance
                new IssueBookView(statisticsView).setVisible(true);
            }
        });

        btnReturnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Return Book screen
                new ReturnBookView().setVisible(true);
            }
        });

        btnStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct to Statistics screen
                new StatisticsView().setVisible(true);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Log out and return to the login screen
                setVisible(false);
                dispose();  // Close the Home window
                new Login().setVisible(true);  // Return to login
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMemberManagement = new javax.swing.JButton();
        btnBookManagement = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnAddMember = new javax.swing.JButton();
        btnAddBook = new javax.swing.JButton();
        btnIssueBook = new javax.swing.JButton();
        btnReturnBook = new javax.swing.JButton();
        btnStatistics = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(60, 10));
        setMaximumSize(new java.awt.Dimension(1330, 758));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1330, 758));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMemberManagement.setBackground(new java.awt.Color(255, 255, 153));
        btnMemberManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMemberManagement.setForeground(new java.awt.Color(0, 0, 0));
        btnMemberManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-student-male-50.png"))); // NOI18N
        btnMemberManagement.setText("Member Management");
        btnMemberManagement.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnMemberManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 118, -1, -1));

        btnBookManagement.setBackground(new java.awt.Color(255, 255, 153));
        btnBookManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBookManagement.setForeground(new java.awt.Color(0, 0, 0));
        btnBookManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-books-50.png"))); // NOI18N
        btnBookManagement.setText("Book Management");
        btnBookManagement.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnBookManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 299, 278, -1));

        btnLogout.setBackground(new java.awt.Color(255, 255, 153));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 0, 0));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-exit-sign-50.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 675, 278, -1));

        btnAddMember.setBackground(new java.awt.Color(255, 255, 153));
        btnAddMember.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddMember.setForeground(new java.awt.Color(0, 0, 0));
        btnAddMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-add-user-male-50.png"))); // NOI18N
        btnAddMember.setText("Add Member");
        btnAddMember.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnAddMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 28, 278, -1));

        btnAddBook.setBackground(new java.awt.Color(255, 255, 153));
        btnAddBook.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddBook.setForeground(new java.awt.Color(0, 0, 0));
        btnAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-add-book-48.png"))); // NOI18N
        btnAddBook.setText("Add Book");
        btnAddBook.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnAddBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 210, 278, -1));

        btnIssueBook.setBackground(new java.awt.Color(255, 255, 153));
        btnIssueBook.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIssueBook.setForeground(new java.awt.Color(0, 0, 0));
        btnIssueBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issue.png"))); // NOI18N
        btnIssueBook.setText("Issue Book");
        btnIssueBook.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnIssueBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 400, 278, -1));

        btnReturnBook.setBackground(new java.awt.Color(255, 255, 153));
        btnReturnBook.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReturnBook.setForeground(new java.awt.Color(0, 0, 0));
        btnReturnBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-return-book-50.png"))); // NOI18N
        btnReturnBook.setText("Return Book");
        btnReturnBook.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnReturnBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 495, 278, -1));

        btnStatistics.setBackground(new java.awt.Color(255, 255, 153));
        btnStatistics.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnStatistics.setForeground(new java.awt.Color(0, 0, 0));
        btnStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-view-50.png"))); // NOI18N
        btnStatistics.setText("Statistics");
        btnStatistics.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        getContentPane().add(btnStatistics, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 585, 278, -1));

        btnCancel.setBackground(new java.awt.Color(255, 255, 153));
        btnCancel.setForeground(new java.awt.Color(0, 0, 0));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/red-x-mark-transparent-background-3.png"))); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 10, 50, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Library_Book_532388_1366x768.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -2, 1350, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose(); //Cancel the Home screen
    }//GEN-LAST:event_btnCancelActionPerformed

    public static void main(String args[]) {
        // For testing purposes, launch the Home screen as an Admin
        new Home(true).setVisible(true);  // Pass true for admin access
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnAddMember;
    private javax.swing.JButton btnBookManagement;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnIssueBook;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMemberManagement;
    private javax.swing.JButton btnReturnBook;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
