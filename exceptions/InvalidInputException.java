package exceptions;

/**
 * A custom checked exception for our application.
 * This is thrown when a user enters invalid data into the text fields,
 * such as numbers in a name field or letters in a phone field.
 * It implements Serializable so it can be properly handled.
 */
public class InvalidInputException extends Exception implements java.io.Serializable {
    
    /**
     * Constructor for the InvalidInputException.
     * @param message The error message to be displayed to the user.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}