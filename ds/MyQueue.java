package ds;

import exceptions.EmptyQueueException;
import java.util.NoSuchElementException;

public class MyQueue<T> implements java.io.Serializable {

    private MyLinkedList<T> list;

    public MyQueue() {
        this.list = new MyLinkedList<>();
    }

    public void enqueue(T data) {
        list.addLast(data);
    }

    public T dequeue() throws EmptyQueueException {
        try {
            return list.removeFirst();
        } catch (NoSuchElementException e) {
            throw new EmptyQueueException("Cannot dequeue from an empty queue.");
        }
    }

    public T peek() throws EmptyQueueException {
        try {
            return list.getFirst();
        } catch (NoSuchElementException e) {
            throw new EmptyQueueException("Cannot peek into an empty queue.");
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.getSize();
    }

    public MyLinkedList<T> getList() {
        return list;
    }
}