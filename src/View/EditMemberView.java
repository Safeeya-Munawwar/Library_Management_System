// @author KAN/IT/2022/F/0036
package View;

import Model.Member; // Import Member class
import Controller.MemberDBC; // Import MemberDBC class
import javax.swing.*; // Import swing components for GUI
import java.sql.Date; // Import date

public class EditMemberView extends javax.swing.JFrame {

    private final MemberDBC memberDBC;
    private final int memberId;
    
    // Constructor that accepts a memberId and initializes components
    public EditMemberView(int memberId) {
        this.memberId = memberId; 
        memberDBC = new MemberDBC(); // Database controller instance
        setTitle("Edit Member"); // Sets window title
        initComponents(); // Initialize GUI components
        loadMemberData(); // Load member details for the given member ID
    }
    
    // Overloaded constructor that includes a reference to MemberManagementView
    public EditMemberView(MemberManagementView memberManagementView, int memberId) {
        this.memberId = memberId;
        memberDBC = new MemberDBC();
        setTitle("Edit Member");
        initComponents();
        loadMemberData();
    }

    // Method to load member data into the form fields
    private void loadMemberData() {
        try {
            Member member = memberDBC.findMemberById(memberId); // Find member by ID
            if (member != null) {
                txtMemberID.setText(String.valueOf(member.getMemberID())); // Sets member ID field
                txtMemberID.setEditable(false); // Make member ID non-editable
                txtMemberName.setText(member.getName());
                txtContactNumber.setText(member.getContactInfo());
                txtAddress.setText(member.getAddress());
                txtMembershipCard.setText(member.getMembershipCard().getCardNumber());
                txtExpirationDate.setDate(new Date(member.getMembershipCard().getExpirationDate().getTime()));
            } 
            else {
                JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose(); // Close window if member not found
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to save the modified member data
    private void saveMember() {
        if (!validateFields()) { // Checks if all fields are filled
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int memberId = Integer.parseInt(txtMemberID.getText().trim());
            String memberName = txtMemberName.getText().trim();
            String contactNumber = txtContactNumber.getText().trim();
            String address = txtAddress.getText().trim();
            String membershipCard = txtMembershipCard.getText().trim();
            Date expirationDate = new Date(txtExpirationDate.getDate().getTime());
            String membershipStatus = null;

            // Creates updated Member object
            Member updatedMember = new Member(memberId, memberName, contactNumber, address, membershipCard, expirationDate, membershipStatus);
            boolean isUpdated = memberDBC.updateMember(updatedMember); // Calls DB update method

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Member updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();  // Close the edit window
            } 
            else {
                JOptionPane.showMessageDialog(this, "Failed to update member.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: expiration date is required.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Validates that all fields are filled out
    private boolean validateFields() {
        return !txtMemberID.getText().trim().isEmpty()
                && !txtMemberName.getText().trim().isEmpty()
                && !txtContactNumber.getText().trim().isEmpty()
                && !txtAddress.getText().trim().isEmpty()
                && !txtMembershipCard.getText().trim().isEmpty()
                && txtExpirationDate.getDate() != null;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        txtMemberName = new javax.swing.JTextField();
        txtContactNumber = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtMembershipCard = new javax.swing.JTextField();
        txtExpirationDate = new com.toedter.calendar.JDateChooser();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 200));
        setUndecorated(true);
        setSize(new java.awt.Dimension(700, 450));

        jPanel1.setBackground(new java.awt.Color(2, 2, 22));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Member ID: ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setText("Member Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("Contact Number:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Address:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Membership Card Number:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 204));
        jLabel6.setText("Expiration Date:");

        txtMemberID.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberID.setForeground(new java.awt.Color(0, 0, 0));

        txtMemberName.setBackground(new java.awt.Color(255, 255, 204));
        txtMemberName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMemberName.setForeground(new java.awt.Color(0, 0, 0));

        txtContactNumber.setBackground(new java.awt.Color(255, 255, 204));
        txtContactNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtContactNumber.setForeground(new java.awt.Color(0, 0, 0));
        txtContactNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactNumberActionPerformed(evt);
            }
        });

        txtAddress.setBackground(new java.awt.Color(255, 255, 204));
        txtAddress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(0, 0, 0));

        txtMembershipCard.setBackground(new java.awt.Color(255, 255, 204));
        txtMembershipCard.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtMembershipCard.setForeground(new java.awt.Color(0, 0, 0));

        txtExpirationDate.setBackground(new java.awt.Color(255, 255, 204));
        txtExpirationDate.setForeground(new java.awt.Color(0, 0, 0));

        btnSave.setBackground(new java.awt.Color(255, 255, 153));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 0, 0));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save-icon--1.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel))
                    .addComponent(txtMemberID)
                    .addComponent(txtMemberName)
                    .addComponent(txtContactNumber)
                    .addComponent(txtAddress)
                    .addComponent(txtMembershipCard)
                    .addComponent(txtExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtMembershipCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtContactNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactNumberActionPerformed

    }//GEN-LAST:event_txtContactNumberActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveMember(); // Calls saveMember method on button click
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose(); // Closes window when Cancel is clicked
    }//GEN-LAST:event_btnCancelActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditMemberView(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtContactNumber;
    private com.toedter.calendar.JDateChooser txtExpirationDate;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtMemberName;
    private javax.swing.JTextField txtMembershipCard;
    // End of variables declaration//GEN-END:variables

}
