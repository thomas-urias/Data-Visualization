package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * A simple class that creates an instance of an insertion sort algorithm
 * Supports methods for add, remove, and search the sorting algorithm
 * Uses an ArrayList of Integers that are randomized 
 * 
 * @author thomasurias
 */
@SuppressWarnings("deprecation")
public class InsertionSort extends Observable implements Serializable, Sorter {
    /** Long to store serial ID to make instance serializable */
    private static final long serialVersionUID = 1L;
    
    /** Random object rand used to add random integers to initialize list */
    private final Random rand = new Random();
    
    /** ArrayList that InsertionSort instance will be working on */
    private ArrayList<Integer> listToSort;
    
    private int key;
    
    private int currentIndex = 1;
    private int currentJ = 0;
    private boolean sorting = false;
    
    /** Created new ArrayList to sort and calls setter to initialize array list */
    public InsertionSort() {
        listToSort = new ArrayList<>();
        initializelistToSort();
    }
    
    /** 
     * Sorting algorithm for the InsertionSort class.
     * Runs whenever a new instance is creates or when:
     * 	- Array list is appended to 
     *  - Array list has object removed 
     *  - Array list is reset
     * Notify's view class whenever a sort iteration happens 
     * through Observable abstract class 
     */
    public void startSort() {
        sorting = true;
        currentIndex = 1;
        currentJ = currentIndex - 1;
        stepSort(); 
    }

    /** Performs a single step of insertion sort
     * @return true if more steps remain, false if sorting is finished
     */
    public boolean stepSort() {
        if (!sorting || currentIndex >= listToSort.size()) {
            sorting = false;
            return false;
        }

        if (currentJ == currentIndex - 1) { 
            key = listToSort.get(currentIndex);
        }

        if (currentJ >= 0 && listToSort.get(currentJ) > key) {
            listToSort.set(currentJ + 1, listToSort.get(currentJ));
            currentJ--;
        } else {
            listToSort.set(currentJ + 1, key);
            currentIndex++;
            currentJ = currentIndex - 1;
        }

        setChanged();
        notifyObservers();
        return true;
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
