package controller;

import model.Model;

/**
 * The Controller class connects the View and the Model.
 * It forwards user actions to the model and hides internal model logic.
 * 
 * @author thomasurias
 */
public class Controller<T extends Comparable<T>> {

    private Model<T> model;

    /** 
     * Initialize the controller with the given model.
     */
    public Controller(String ds) {
        model = new Model<T>(ds, null, null, null, null);
    }

    /**
     * Adds data to all active data structures in the model.
     * 
     * @param obj the object to add
     * @param optional optional flag (append/prepend for LinkedList)
     */
    public void add(T obj, boolean optional) {
        model.add(obj, optional);
    }

    /**
     * Inserts data at the given index (LinkedList only).
     * 
     * @param obj the object to insert
     * @param index the index to insert at
     */
    public void insert(T obj, int index) {
        model.insert(obj, index);
    }

    /**
     * Removes a value from all active data structures.
     * 
     * @param obj the object to remove
     */
    public void remove(T obj) {
        model.remove(obj);
    }

    /**
     * Calls find() on all active structures.
     * 
     * @param obj the object to search for
     * @return any returned search result
     */
    public Object find(T obj) {
        return model.find(obj);
    }

    /**
     * Resets all active data structures.
     */
    public void reset() {
        model.reset();
    }

    /**
     * Saves current data structures to disk.
     */
    public void save() throws Exception {
        model.save();
    }

    /**
     * Loads saved data from disk.
     */
    public void load() {
        model.load();
    }
    
    /**
     * This method creates a new model with just the sorting algorithms for the comparison feature
     */
    public void compareSorts() {
        model = new Model<T>("Insertion", "Bubble", null, null, null);
    }
    
    /**
     * This method gets the current model being used for the application
     * Used primarily for testing purposes 
     * 
     * @return Model<T> model 
     */
    public Model<T> getModel() {
        return model;
    }
}
