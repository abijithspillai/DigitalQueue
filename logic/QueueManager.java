package logic;

import ds.MyLinkedList;
import ds.MyQueue;
import exceptions.EmptyQueueException;
import model.Person;
import model.Token;

/**
 * The "Brain" of the application. This class is the *only* class
 * that should modify the data structures.
 * The GUI talks to this class, and this class talks to the data.
 * This separation of logic from GUI is a core OOP principle.
 * It is Serializable so the entire state of the app can be saved.
 */
public class QueueManager implements java.io.Serializable {

    // The queue for people currently waiting
    private MyQueue<Token> waitingQueue;
    // The list for people who have already been served
    private MyLinkedList<Token> historyList;
    // The number for the next token to be generated
    private int nextTokenNumber;

    /**
     * Constructor for a new QueueManager, initializing empty data structures.
     */
    public QueueManager() {
        this.waitingQueue = new MyQueue<>();
        this.historyList = new MyLinkedList<>();
        this.nextTokenNumber = 1;
    }

    /**
     * Creates a new token, adds it to the waiting queue, and increments the token counter.
     * @param person The Person (Patient or BankCustomer) for whom to generate a token.
     * @return The newly created Token.
     */
    public Token generateNewToken(Person person) {
        Token newToken = new Token(nextTokenNumber, person);
        waitingQueue.enqueue(newToken);
        nextTokenNumber++;
        return newToken;
    }

    /**
     * Serves the next person in the queue.
     * Removes the token from the waiting queue and adds it to the history list.
     * @return The Token that was just served.
     * @throws EmptyQueueException if the waiting queue is empty.
     */
    public Token serveNextToken() throws EmptyQueueException {
        // Dequeue will throw the exception if the queue is empty
        Token servingToken = waitingQueue.dequeue();
        
        // If successful, add to history
        historyList.addLast(servingToken);
        return servingToken;
    }

    /**
     * Finds a token in the *history* list by its number.
     * This is a **Linear Search** algorithm.
     * @param tokenNumber The number of the token to find.
     * @return The Token, or null if not found.
     */
    public Token findTokenInHistory(int tokenNumber) {
        // We use the for-each loop (iterator) to traverse the list
        for (Token token : historyList) {
            if (token.getTokenNumber() == tokenNumber) {
                return token;
            }
        }
        return null; // Not found
    }

    /**
     * Getter for the history list, for display in the GUI.
     * @return The list of served tokens.
     */
    public MyLinkedList<Token> getHistoryList() {
        return historyList;
    }

    /**
     * Getter for the waiting list, for display in the GUI.
     * @return The list of waiting tokens.
     */
    public MyLinkedList<Token> getWaitingList() {
        return waitingQueue.getList();
    }
    
    /**
     * Peeks at the next token number in the queue without serving it.
     * @return The next token number as a String, or "--" if the queue is empty.
     */
    public String getNextTokenInQueue() {
        try {
            return String.valueOf(waitingQueue.peek().getTokenNumber());
        } catch (EmptyQueueException e) {
            // If empty, return a placeholder
            return "--";
        }
    }
}