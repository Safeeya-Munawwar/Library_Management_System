// @author KAN/IT/2022/F/0036
package Model;

import java.util.Date; // Import date

// Member class that extends User to represent a library member
public class Member extends User {

    private int memberID;
    private String name;
    private String contactInfo;
    private String address;
    private MembershipCard membershipCard; // Composition relationship with MembershipCard
    private String membershipStatus;

    // Constructors 
    public Member(int memberID, String username, String name, String contactInfo, String address, MembershipCard membershipCard, String membershipStatus) {
        super(username); // Pass username to User
        this.memberID = memberID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
        this.membershipCard = membershipCard;
        this.membershipStatus = membershipStatus;
        updateMembershipStatus(false); // Update status upon creation (not deleted)
    }

    public Member(int memberId, String name, String contactInfo, String address, MembershipCard membershipCard, String membershipStatus) {
        this(memberId, null, name, contactInfo, address, membershipCard, membershipStatus);
    }

    public Member(int memberId, String memberName, String contactNumber, String address, String membershipCardId, java.sql.Date expirationDate, String membershipStatus) {
        this(memberId, null, memberName, contactNumber, address, new MembershipCard(membershipCardId, expirationDate), membershipStatus); // Call the main constructor
    }

    public Member(int memberId, String defaultUsername, String name, String contactNumber, String address, MembershipCard membershipCard, java.sql.Date sqlExpirationDate) {
        super(defaultUsername); // Call the User constructor with the default username
        this.memberID = memberId;
        this.name = name;
        this.contactInfo = contactNumber;
        this.address = address;
        this.membershipCard = membershipCard; // This can be directly passed in
        updateMembershipStatus(false); // Update status upon creation (not deleted)
    }

    // Getters for all fields
    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    // Get expiration date from the MembershipCard
    public Date getExpirationDate() {
        return membershipCard != null ? membershipCard.getExpirationDate() : null;
    }
    
    public String getMembershipStatus() {
        return membershipStatus;
    }

    // Update membership status based on the expiration date or deletion
    public final void updateMembershipStatus(boolean isDeleted) {
        if (isDeleted) {
            // If the member is deleted, set status to "Inactive"
            this.membershipStatus = "Inactive";
        } else {
            Date expirationDate = getExpirationDate();

            if (expirationDate != null) {
                if (expirationDate.before(new Date())) {
                    this.membershipStatus = "Expired";
                } else {
                    this.membershipStatus = "Active";
                }
            } else {
                this.membershipStatus = "No Card!";
            }
        }
    }

    // Display member details
    public void displayDetails() {
        System.out.println("Username: " + getUsername());
        System.out.println("Member ID: " + getMemberID());
        
        if (membershipCard != null) {
            System.out.println("Membership Card Number: " + membershipCard.getCardNumber());
            System.out.println("Expiration Date: " + membershipCard.getExpirationDate());
        } else {
            System.out.println("No membership card associated.");
        }

        System.out.println("Name: " + getName());
        System.out.println("Contact Info: " + getContactInfo());
        System.out.println("Address: " + getAddress());
        System.out.println("Membership Status: " + getMembershipStatus());
    }

    // Override toString method for string representation of Member object
    @Override
    public String toString() {
        return "Member{" +
                "memberID=" + memberID +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                ", membershipCard=" + (membershipCard != null ? membershipCard.getCardNumber() : "No Card") +
                ", expirationDate=" + getExpirationDate() +
                '}';
    }
}
