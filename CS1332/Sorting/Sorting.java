
import java.util.Random;
import java.util.ArrayList;

/**
 * Sorting implementation
 * CS 1332 : Fall 2014
 * @author Akshay Shukla
 * @version 1.0
 */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.
    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param arr the array that must be sorted after the method runs
     */
    //@Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        int swapCounter = arr.length;
        int j = 0;
        while (swapCounter != 0) {
            j++;
            swapCounter = 0;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    T larger = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = larger;
                    swapCounter++;
                }
            }
        }
    }
    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param arr the array that must be sorted after the method runs
     */
    //@Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T data = arr[i];
            int j = i - 1;
            while ((j >= 0) && (arr[j].compareTo(data) > 0)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = data;
        }

    }
    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array.
     *
     * @param arr the array that must be sorted after the method runs
     */
    //@Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        int min;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }
            T larger = arr[i];
            arr[i] = arr[min];
            arr[min] = larger;
        }

    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you data a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array.
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * @param arr the array that must be sorted after the method runs
     */
    //@Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr, Random r) {
        int low = 0;
        int high = arr.length - 1;
        quicksort(arr, r, low, high);
    }


    /**
     * quicksort helper.
     * @param arr the array that must be sorted after the method runs.
     * @param r random object to find pivot.
     * @param low pointer at front of array.
     * @param high pointer at end of array.
     */
    public <T extends Comparable<? super T>>
    void quicksort(T[] arr, Random r, int low, int high) {

        int start = low;
        int end = high;
        if ((high - low) > 0) {
            int pivot = r.nextInt(high - low) + low;
            T data = arr[low];
            arr[low] = arr[pivot];
            arr[pivot] = data;
            while (low != high) {
                if (low < high) {
                    if (arr[low].compareTo(arr[high]) >= 0) {
                        T temp = arr[low];
                        arr[low] = arr[high];
                        arr[high] = temp;
                        int index = high;
                        high = low;
                        low = index;
                        high++;
                    } else {
                        high--;
                    }
                } else {
                    if (arr[high].compareTo(arr[low]) > 0) {
                        T temp = arr[high];
                        arr[high] = arr[low];
                        arr[low] = temp;
                        int index = low;
                        low = high;
                        high = index;
                        high--;
                    } else {
                        high++;
                    }
                }
            }
            quicksort(arr, r, start, low - 1);
            quicksort(arr, r, low + 1, end);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     *
     * @param arr the array to be sorted
     */
    //@Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        Object[] helper =  new Object[arr.length];
        mergesort(arr, helper, 0, arr.length - 1);
    }
    /**
     * mergesort helper.
     * @param arr the array that must be sorted after the method runs.
     * @param helper helper array.
     * @param low pointer at front of array.
     * @param high pointer at end of array.
     */
    private <T extends Comparable<? super T>>
    void mergesort(T[] arr, Object[] helper, int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            mergesort(arr, helper, low, middle);
            mergesort(arr, helper, middle + 1, high);
            merge(arr, helper, low, middle, high);
        }
    }
    /**
     * mergesort helper.
     * @param arr the array that must be sorted after the method runs.
     * @param helper helper array.
     * @param low pointer at front of array.
     * @param middle pointer at middle of array.
     * @param high pointer at end of array.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<? super T>>
    void merge(T[] arr, Object[] helper, int low, int middle, int high) {

        for (int i = low; i <= high; i++) {
            helper[i] = arr[i];
        }
        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;
        while (helperLeft <= middle && helperRight <= high) {
            T leftData = (T) helper[helperLeft];
            T rightData = (T) helper[helperRight];
            if (leftData.compareTo(rightData) <= 0) {
                arr[current] = leftData;
                helperLeft++;
            } else {
                arr[current] = rightData;
                helperRight++;
            }
            current++;
        }
        int remaining = middle - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            arr[current + i] = (T) helper[helperLeft + i];
        }
    }

    /**
     * Implement radix sort
     *
     * Remember you CANNOT convert the ints to strings.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * You may use an ArrayList or LinkedList if you wish,
     * but it may only be used inside radixsort and any radix sort helpers
     * Do NOT use these classes with other sorts.
     *
     * @param arr the array to be sorted
     * @return the sorted array
     */

    @SuppressWarnings("unchecked")
    public int[] radixsort(int[] arr) {
        int r = 10;
        ArrayList<Integer>[] bucket1 =
                (ArrayList<Integer>[]) new ArrayList[r];
        ArrayList<Integer>[] bucket2 =
                (ArrayList<Integer>[]) new ArrayList[r];
        for (int i = 0; i < bucket1.length; i++) {
            bucket1[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < bucket2.length; i++) {
            bucket2[i] = new ArrayList<Integer>();
        }
        boolean tf = false;
        int temp = -1;
        int place = 1;
        while (!tf) {
            tf = true;
            for (Integer i : arr) {
                if (i >= 0) {
                    temp = i / place;
                    bucket1[temp % r].add(i);
                    if (tf && temp > 0) {
                        tf = false;
                    }
                } else {
                    i = -i;
                    temp = i / place;
                    bucket2[temp % r].add(i);
                    if (tf && temp > 0) {
                        tf = false;
                    }
                }
            }
            int a = 0;
            for (int i = r - 1; i >= 0; i--) {
                for (Integer integer : bucket2[i]) {
                    arr[a++] = -integer;
                }
                bucket2[i].clear();
            }
            for (int i = 0; i < r; i++) {
                for (Integer integer : bucket1[i]) {
                    arr[a++] = integer;
                }
                bucket1[i].clear();
            }
            place *= r;
        }
        return arr;
    }
}