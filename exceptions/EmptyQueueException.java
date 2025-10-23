package exceptions;

/**
 * A custom checked exception for our application.
 * This is thrown when a user tries to dequeue or peek an empty MyQueue.
 * It implements Serializable so it can be properly handled by the serialization process.
 */
public class EmptyQueueException extends Exception implements java.io.Serializable {
    
    public EmptyQueueException(String message) {
        super(message);
    }
}