import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot Sort data while either data or comparator is null");
        }
        int maxValIndex;
        for (int i = arr.length - 1; i > 0; i--) {
            maxValIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (j != maxValIndex && comparator.compare(arr[j], arr[maxValIndex]) > 0) {
                    maxValIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[maxValIndex];
            arr[maxValIndex] = temp;
        }

    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot Sort data while either data or comparator is null");
        }
        boolean swapMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        while (swapMade) {
            swapMade = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swapMade = true;
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
            if (swapMade) { //Used to reduce further comparisons
                endInd--; //Last position already sorted
                swapMade = false;
                for (int i = endInd - 1; i >= startInd; i--) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        swapMade = true;
                        T temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = temp;
                    }
                }
                startInd++;
            } else {
                break;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot Sort data while either data or comparator is null");
        }
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[length - midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < length - midIndex; i++) {
            right[i] = arr[midIndex + i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot Sort data while either data or comparator is null");
        }
        quickSortHelper(arr, 0, arr.length - 1, comparator, rand);
    }


    /**
     * This method aids in the implementation of the quickSort Method.
     *
     * @param arr array to be sorted
     * @param start start index
     * @param end end index
     * @param comparator Comparator object used to compare elements
     * @param rand Random object used to calculate random position in array
     * @param <T> data type to sort through
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }
        int pivotInd = start + rand.nextInt(end - start + 1); //This implementation is inclusive of end.
        T pivotVal = arr[pivotInd];
        T temp = arr[start];
        arr[start] = arr[pivotInd];
        arr[pivotInd] = temp;

        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }

        T temp3 = arr[start];
        arr[start] = arr[j];
        arr[j] = temp3;
        quickSortHelper(arr, start, j - 1, comparator, rand);
        quickSortHelper(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array set");
        }

        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        int divisor = 1;
        boolean cont = true; //Used to determine if there exists an element that still needs it's
        // digit stored in buckets.
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int currNum = arr[i] / divisor;
                if (currNum / 10 != 0) {
                    cont = true;
                }
                if (buckets[currNum % 10 + 9] == null) {
                    buckets[currNum % 10 + 9] = new LinkedList<Integer>();
                }
                buckets[currNum % 10 + 9].add(arr[i]);
            }
            int currInd = 0;
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        arr[currInd++] = num;
                    }
                    buckets[i].clear();
                }
            }
            divisor *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot perform HeapSort on null dataset");
        }
        PriorityQueue<Integer> backQueue = new PriorityQueue<>(data);
        int[] outter = new int[backQueue.size()];
        int i = 0;
        while (!backQueue.isEmpty()) {
            outter[i] = (int) backQueue.poll();
            i++;
        }
        return outter;
    }
}
