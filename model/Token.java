package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a single token in the queue.
 * This class bundles a Person (Patient or Customer) with their token number.
 * It is Serializable so it can be saved to the file.
 */
public class Token implements java.io.Serializable {
    
    private int tokenNumber;
    private Person person;
    private Date issueTime; // Use java.util.Date as it's Serializable

    /**
     * Constructor for a new Token.
     * @param tokenNumber The token's number.
     * @param person The person associated with the token.
     */
    public Token(int tokenNumber, Person person) {
        this.tokenNumber = tokenNumber;
        this.person = person;
        this.issueTime = new Date(); // Set issue time to now
    }

    // --- Standard Getters ---
    public int getTokenNumber() {
        return tokenNumber;
    }

    public Person getPerson() {
        return person;
    }

    /**
     * Creates a user-friendly string representation of the token for display.
     * @return A formatted string.
     */
    @Override
    public String toString() {
        // Using a core library for date formatting
        SimpleDateFormat dtf = new SimpleDateFormat("HH:mm:ss");
        return "Token " + tokenNumber + 
               " | " + person.getName() +
               " | " + person.getDetails() +
               " | Issued at: " + dtf.format(issueTime);
    }
}