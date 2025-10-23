package ds;

import exceptions.EmptyQueueException;
import java.util.NoSuchElementException;

/**
 * A custom Queue implementation, built from scratch.
 * This class uses the "Composition" principle, as it *contains* a MyLinkedList.
 * It maps Queue operations (enqueue, dequeue) to LinkedList operations.
 *
 */
public class MyQueue<T> implements java.io.Serializable {

    // The underlying data structure is our custom linked list.
    private MyLinkedList<T> list;

    /**
     * Constructor for a new, empty queue.
     */
    public MyQueue() {
        this.list = new MyLinkedList<>();
    }

    /**
     * Adds an item to the back of the queue (FIFO).
     * @param data The data to add.
     */
    public void enqueue(T data) {
        list.addLast(data);
    }

    /**
     * Removes and returns the item from the front of the queue (FIFO).
     * @return The data from the front of the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    public T dequeue() throws EmptyQueueException {
        try {
            // This is where we use our custom exception
            return list.removeFirst();
        } catch (NoSuchElementException e) {
            // Convert the list's exception into our queue's specific exception
            throw new EmptyQueueException("Cannot dequeue from an empty queue.");
        }
    }

    /**
     * "Peeks" at the item in the front of the queue without removing it.
     * @return The data from the front of the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    public T peek() throws EmptyQueueException {
        try {
            return list.getFirst();
        } catch (NoSuchElementException e) {
            throw new EmptyQueueException("Cannot peek into an empty queue.");
        }
    }

    /**
     * Checks if the queue is empty.
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of items in the queue.
     * @return The size of the queue.
     */
    public int size() {
        return list.getSize();
    }

    /**
     * Allows the logic layer to get the full list for display purposes.
     * @return The underlying MyLinkedList.
     */
    public MyLinkedList<T> getList() {
        return list;
    }
}