import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Ethan Haarer
 * @version 1.0
 * @userid ehaarer3
 * @GTID 903586678
 *
 * Collaborators: No collaborators used.
 *
 * Resources: only consulted course reasources.
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY]; //Simple enough, just instantiated the array and size.
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data)  {
        //I first check for the provided errors.
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Provided data was null!");
        }
        //Loop backwards through array until size, shifting all data forward.
        //Then replace indexed data with passed in data.
        if (size < backingArray.length) {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
            size += 1;
        } else if (size == backingArray.length) {
            //Copy to all data in new array shifted forward until index, then transferred rest to same positions.
            //Then insert new data and increment size. Then assign newArray to backingArray.
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                if (i < index) {
                    newArray[i] = backingArray[i];
                }
                if (i >= index) {
                    newArray[i + 1] = backingArray[i];
                }
            }
            newArray[index] = data;
            size += 1;
            backingArray = newArray;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        //Check for errors.
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        //Loop backwards through array copying data forward, then insert new data into front of array.
        if (size < backingArray.length) {
            for (int i = size - 1; i >= 0; i--) {
                backingArray[i + 1] = backingArray[i];
            }
            backingArray[0] = data;
            size++;
        } else if (size == backingArray.length) {
            //Create a new and larger array, copy and shift all data forward one index.
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i + 1] = backingArray[i];
            }
            //Insert new data in front, then increment size, and assign newArray to Backing array.
            newArray[0] = data;
            size += 1;
            backingArray = newArray;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) throws IllegalArgumentException {
        //Check for Errors.
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        //Add data to the end of the array, then increment size.
        if (size < backingArray.length) {
            backingArray[size] = data;
            size++;
        } else if (size == backingArray.length) {
            //Create new Array of greater size, then copy all data to corresponding indices.
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            //Put data at the end of the array, then increment size and assign newArray to backingArray.
            newArray[size] = data;
            size += 1;
            backingArray = newArray;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        // Check for errors.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
        //Copy removed data to temp variable.
        T indexData = backingArray[index];
        // Check if data is at end, then set data at end to null and return removed data.
        // Decrement size.
        if (index == size - 1) {
            T backData = backingArray[size - 1];
            backingArray[size - 1] = null;
            size -= 1;
            return backData;
        } else {
            //Decrease index of all data after index, then assign last index to null.
            for (int i = index + 1; i < backingArray.length; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[backingArray.length - 1] = null;
            //Decrement size.
            size -= 1;
        }
        //Return indexData.
        return indexData;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        //Check for errors
        if (size == 0) {
            throw new NoSuchElementException("No more elements to remove!");
        }
        //Store front data in temp variable.
        //Loop all data back one to remove front data, then set index at size-1 to be null, and decrement size.
        T frontData = backingArray[0];
        for (int i = 0; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        //Return front data.
        return frontData;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        // Check for errors.
        if (size == 0) {
            throw new NoSuchElementException("No more elements to remove!");
        }
        //make temp variable for data at end, then assign last value to null and decriment size.
        T backData = backingArray[size - 1];
        backingArray[size - 1] = null;
        size -= 1;
        return backData;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        //check for errors.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
        //Return value at given index.
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        //return if size is zero as then no data would reside in array.
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        // Set backingArray to a newly casted Object array.
        // Reset size back to zero.
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}