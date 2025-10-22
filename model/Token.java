package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Token implements java.io.Serializable {
    
    private int tokenNumber;
    private Person person;
    private Date issueTime;

    public Token(int tokenNumber, Person person) {
        this.tokenNumber = tokenNumber;
        this.person = person;
        this.issueTime = new Date();
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        SimpleDateFormat dtf = new SimpleDateFormat("HH:mm:ss");
        return "Token " + tokenNumber + 
               " | " + person.getName() +
               " | " + person.getDetails() +
               " | Issued at: " + dtf.format(issueTime);
    }
}