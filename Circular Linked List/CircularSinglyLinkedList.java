import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Ethan Haarer
 * @version 1.0
 * @userid ehaarer3
 * @GTID 903586678
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) { // throw required errors.
            throw new IllegalArgumentException("Inputted Data is Null");
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index was out of bounds");
        }
        CircularSinglyLinkedListNode<T> addCurr = new CircularSinglyLinkedListNode<T>(data);
        CircularSinglyLinkedListNode<T> curr = head;
        if (index == 0) {
            if (head == null) { //if null, then addCurr becomes new head
                head = addCurr;
                head.setNext(head);
                size = 1;
            } else { //if grouping is not null, then add to CLL
                addCurr.setNext(head.getNext());
                head.setNext(addCurr);
                addCurr.setData(head.getData());
                head.setData(data);
                size++;
            }
        } else if (index == size) {
            if (head == null) { //if null, then addCurr becomes new head
                head = addCurr;
                head.setNext(head);
                size = 1;
            } else {
                addCurr.setNext(head.getNext());
                head.setNext(addCurr);
                addCurr.setData(head.getData());
                head.setData(data);
                head = addCurr;
                size++;
            }
        } else { // If index not on the edges, then loop until the node before the index.
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            addCurr.setNext(curr.getNext());
            curr.setNext(addCurr);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) { // throw required errors.
            throw new IllegalArgumentException("Inputted Data is Null");
        }
        CircularSinglyLinkedListNode<T> addCurr = new CircularSinglyLinkedListNode<T>(data);
        if (head == null) { //if null, then addCurr becomes new head
            head = addCurr;
            head.setNext(head);
            size = 1;
        } else if (size == 1) { //if grouping is not null, then add to CLL
            addCurr.setNext(head);
            head.setNext(addCurr);
            addCurr.setData(head.getData());
            head.setData(data);
            size++;
        } else { //if list is greater than 1, then point towards whatever the head is pointing to
            addCurr.setNext(head.getNext());
            head.setNext(addCurr);
            addCurr.setData(head.getData());
            head.setData(data);
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) { // throw required errors.
            throw new IllegalArgumentException("Inputted Data is Null");
        }
        CircularSinglyLinkedListNode<T> addCurr = new CircularSinglyLinkedListNode<T>(data);
        if (head == null) { //if null, then addCurr becomes new head
            head = addCurr;
            head.setNext(head);
            size = 1;
        } else if (size == 1) { //if grouping is not null, then add to CLL
            addCurr.setNext(head);
            head.setNext(addCurr);
            addCurr.setData(head.getData());
            head.setData(data);
            head = addCurr;
            size++;
        } else { //if list is greater than 1, then point towards whatever the head is pointing to
            addCurr.setNext(head.getNext());
            head.setNext(addCurr);
            addCurr.setData(head.getData());
            head.setData(data);
            head = addCurr; // change head so previous 'head' is now at the back
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) { // throw required errors.
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T outData = null;
        CircularSinglyLinkedListNode<T> curr = head;
        if (index == 0) { //if index is 0, then does the same as removefromfront() method
            return removeFromFront();
        } else { // loop through until node right before indexed position
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            outData = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
        }
        size--; //decrement size and return removed data
        return outData;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) { // throw required errors.
            throw new NoSuchElementException("List is Empty");
        }
        T outData = head.getData();
        if (size == 1) { // make list empty
            head = null;
        } else { //if size > 1, then just rearrange the pointers
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        }
        size--;
        return outData;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) { // throw required errors.
            throw new NoSuchElementException("List is Empty");
        }
        T outData = head.getData();
        CircularSinglyLinkedListNode<T> curr = head;
        if (head.getNext() == head) { // if only one element, then clear the whole list
            this.clear();
        } else { // not including the head, stop right before the back so that we can just exchange the pointers.
            for (int i = 0; i < size - 2; i++) {
                curr = curr.getNext();
            }
            outData = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
        }
        return outData;
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) { // throw required errors.
            throw new IndexOutOfBoundsException("Index was out of bounds");
        }
        T outData  = null;
        if (index == 0) { //if index = 0, then just return the head's data
            outData = head.getData();
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) { //loop through until you get to the desired node, then hold & return data.
                curr = curr.getNext();
            }
            outData = curr.getData();
        }
        return outData;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null && size == 0); // check that head is null and size is 0.
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() { // just reset size and clear head.
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) { //throw exceptions
            throw new IllegalArgumentException("Data is Null");
        }
        int index = -1; //default is -1, so if data isn't in CLL then index is -1
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) { //loop through until size, recording the latest index
            if (curr.getData().equals(data)) {
                index = i;
            }
            curr = curr.getNext();
        }
        if (index == -1) { //data wasn't found
            throw new NoSuchElementException("Data not Found in List");
        }
        System.out.println(index);
        return this.removeAtIndex(index); //refer to method above
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() { // loop through the entire array and list copying data to
        T[] outArray = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            outArray[i] = curr.getData();
            curr = curr.getNext();
        }
        return outArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
