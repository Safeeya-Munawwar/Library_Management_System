// @author KAN/IT/2022/F/0036
package Model;

import java.util.Date; // Importing date

public class MembershipCard {

    private final String cardNumber;
    private final Date expirationDate;

    // Constructor
    public MembershipCard(String cardNumber, Date expirationDate) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    // Getters
    public String getCardNumber() {
        return cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    // Override toString method to return card number as a string
    @Override
    public String toString() {
        return cardNumber; // Return the card number as a string
    }
}
