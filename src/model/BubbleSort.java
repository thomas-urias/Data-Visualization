package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * A simple class that creates an instance of a bubble sort algorithm
 * Supports methods for add, remove, and search the sorting algorithm
 * Uses an ArrayList of Integers that are randomized 
 * 
 * @author thomasurias
 */
@SuppressWarnings("deprecation")
public class BubbleSort extends Observable implements Serializable, Sorter {
    /** Long to store serial ID to make instance serializable */
    private static final long serialVersionUID = 1L;
    
    /** Random object rand used to add random integers to initialize list */
    private final Random rand = new Random();
    
    /** ArrayList that BubbleSort instance will be working on */
    private ArrayList<Integer> listToSort;
    
    private int key;
    
    private boolean sorting = false;
    private int i = 0;     // outer loop index
    private int j = 0;     // inner loop index
    
    /** Created new ArrayList to sort and calls setter to initialize array list */
    public BubbleSort() {
        listToSort = new ArrayList<>();
        initializelistToSort();
    }
    
    /** 
     * Sorting algorithm for the BubbleSort class.
     * Runs whenever a new instance is creates or when:
     * 	- Array list is appended to 
     *  - Array list has object removed 
     *  - Array list is reset
     * Notify's view class whenever a sort iteration happens 
     * through Observable abstract class 
     */
    public void startSort() {
        sorting = true;
        i = 0;
        j = 0;
        stepSort();  // first step
    }

    /** Performs a single step of bubble sort
     * @return true if more steps remain, false if sorting is finished
     */
    public boolean stepSort() {
        if (!sorting || listToSort.size() <= 1) {
            sorting = false;
            return false;
        }

        // If we've finished a full outer iteration
        if (i >= listToSort.size() - 1) {
            sorting = false;
            setChanged();
            notifyObservers();
            return false; // sorting done
        }

        // Inner loop: compare list[j] and list[j+1]
        if (j < listToSort.size() - i - 1) {
            if (listToSort.get(j) > listToSort.get(j + 1)) {
                // Swap
                int temp = listToSort.get(j);
                listToSort.set(j, listToSort.get(j + 1));
                listToSort.set(j + 1, temp);
            }

            j++; // move inner loop
        } else {
            j = 0;    // reset inner loop
            i++;      // advance outer loop
        }

        setChanged();
        notifyObservers();
        return true; // more steps remain
    }

    
    /** Removes random integer from the array list being sorted */
    public void remove(Integer obj) {
        listToSort.remove(obj);
    }
    
    /**
     * Method that takes a given obj and adds it to the array list being sorted 
     * 
     * @param obj object to append to the array list
     */
    public void append(Integer obj) {
        listToSort.add(obj);
    }

    //** Returns the array list being sorted */
    public ArrayList<Integer> getlistToSort() {
        return listToSort;
    }

    /** Setter for array list being sorted. 
     *  Initializes array list with 10 random integers
     */
    public void initializelistToSort() {
        for(int i = 0; i < 10; i++) {
            listToSort.add(rand.nextInt(100));
        }
        startSort();
    }
    
    /**
     * Find method to search array list for the given obj
     * 
     * @param obj object to find in the array list
     * @return listToSort.indexOf(obj) index of the found obj
     */
    public int find(Integer obj) {
        return listToSort.indexOf(obj);
    }

    /** Method that resets the current sorting instance 
     * by creating a new array list to sort over
     */
    public void reset() {
    	listToSort = new ArrayList<>();
    	initializelistToSort();
    }
}
