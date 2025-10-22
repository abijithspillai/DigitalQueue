package logic;

import ds.MyLinkedList;
import ds.MyQueue;
import exceptions.EmptyQueueException;
import model.Person;
import model.Token;

public class QueueManager implements java.io.Serializable {

    private MyQueue<Token> waitingQueue;
    private MyLinkedList<Token> historyList;
    private int nextTokenNumber;

    public QueueManager() {
        this.waitingQueue = new MyQueue<>();
        this.historyList = new MyLinkedList<>();
        this.nextTokenNumber = 1;
    }

    public Token generateNewToken(Person person) {
        Token newToken = new Token(nextTokenNumber, person);
        waitingQueue.enqueue(newToken);
        nextTokenNumber++;
        return newToken;
    }

    public Token serveNextToken() throws EmptyQueueException {
        Token servingToken = waitingQueue.dequeue();
        historyList.addLast(servingToken);
        return servingToken;
    }

    public Token findTokenInHistory(int tokenNumber) {
        for (Token token : historyList) {
            if (token.getTokenNumber() == tokenNumber) {
                return token;
            }
        }
        return null;
    }

    public MyLinkedList<Token> getHistoryList() {
        return historyList;
    }

    public MyLinkedList<Token> getWaitingList() {
        return waitingQueue.getList();
    }
    
    public String getNextTokenInQueue() {
        try {
            return String.valueOf(waitingQueue.peek().getTokenNumber());
        } catch (EmptyQueueException e) {
            return "--";
        }
    }
}