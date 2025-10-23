package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of a Singly Linked List, built from scratch.
 * This class is the foundation for our custom Queue.
 * It implements Serializable to allow the list to be saved to a file.
 *
 */
public class MyLinkedList<T> implements Iterable<T>, java.io.Serializable {

    /**
     * Inner class representing a single node in the linked list.
     * It is also Serializable so it can be saved.
     */
    private class Node implements java.io.Serializable {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Head points to the first node in the list
    private Node head;
    // Tail points to the last node for efficient additions (O(1))
    private Node tail;
    // Keeps track of the number of items in the list
    private int size;

    /**
     * Constructor for a new, empty linked list.
     */
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds a new item to the *end* of the list.
     * @param data The data to add.
     */
    public void addLast(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode; // If list is empty, new node is both head and tail
        } else {
            tail.next = newNode; // Otherwise, link the old tail to the new node
        }
        tail = newNode; // Update the tail to be the new node
        size++;
    }

    /**
     * Removes and returns the item from the *front* of the list.
     * @return The data from the front of the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        T data = head.data; // Get data to return
        head = head.next; // Move head to the next node
        if (head == null) {
            tail = null; // If list is now empty, set tail to null too
        }
        size--;
        return data;
    }

    /**
     * "Peeks" at the item in the front of the list without removing it.
     * @return The data from the front of the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    /**
     * Returns the number of items in the list.
     * @return The size of the list.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Provides an iterator for the list.
     * This allows us to use a for-each loop on our custom list.
     * This is a form of **traversal**.
     * @return An iterator for the list.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}