import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedQueue. It should NOT be circular.
 *
 * @author Ethan Haarer
 * @version 1.0
 * @userid ehaarer3
 * @GTID 903586678
 *
 * Collaborators: None
 *
 * Resources: Only course resources
 */
public class LinkedQueue<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the back of the queue.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data passed is null");
        }
        size++;
        LinkedNode<T> temp = new LinkedNode<T>(data);
        if (head == null && tail == null) {
            head = temp;
            tail = temp;
        } else if (head == tail) {
            head.setNext(temp);
            tail = temp;
        }else {
            temp.setNext(null);
            tail.setNext(temp);
            tail = temp;
        }
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        size--;
        LinkedNode<T> temp = null;
        if (head == tail) {
            temp = head;
            head = null;
            tail = null;
        } else {
            temp = head;
            head = head.getNext();
        }
        return temp.getData();
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.getData();
    }

    /**
     * Returns the head node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
