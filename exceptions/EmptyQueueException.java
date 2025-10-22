package exceptions;

public class EmptyQueueException extends Exception implements java.io.Serializable {
    
    public EmptyQueueException(String message) {
        super(message);
    }
}