package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class represents the Model, it is used to control the logic of each data structure.
 * That includes adding, removing, reseting, inserting, and finding. This class also controls
 * the logic for saving and loading if necessary.
 * @param <T>
 */
@SuppressWarnings("deprecation")
public class Model<T extends Comparable<T>> extends Observable{
	
	private LinkedList<T> myLL;
	private Stack<T> myStack;
	private Queue<T> myQueue;
	private BubbleSort myBubble; 
	private InsertionSort myInsertion;
	
	private boolean activeLL;
	private boolean activeStack;
	private boolean activeQueue;
	private boolean activeInsertion;
	
	
	/**
	 * The constructor method first tries to load any previous data structures or algorithms, if none exist
	 * it creates based on the method parameters. If any saved ds/algos exist they will be loaded, if there
	 * are remaining they will be created.
	 * @param ds1, ds2, ds3, ds4, ds5 : all String representations of a data structure or algorithm that should be active.
	 * @author Cole Mayo
	 */
	public Model(String ds1, String ds2, String ds3, String ds4, String ds5) {
			load();
			activateDS(ds1);
			activateDS(ds2);
			activateDS(ds3);
			activateDS(ds4);
			activateDS(ds5);
	}
	
	/**
	 * This method is used to activate the necessary DS according to the parameter.
	 * @param ds : String representation of the DS we want to activate.
	 * @author cole
	 */
	public void activateDS(String ds) {
		if(ds == null) {
			return;
		}
		switch (ds) {
		case "LL":
			activeLL = true;
			if(myLL == null) {
				myLL = new LinkedList<>();
			}
			break;
		case "Stack":
			activeStack = true;
			if(myStack == null) {
				myStack = new Stack<>();
			}
			break;
		case "Queue":
			activeQueue = true;
			if(myQueue == null) {
				myQueue = new Queue<>();
			}
			break;
		case "Insertion":
			activeInsertion = true;
			if(myInsertion == null) {
				myInsertion = new InsertionSort();
			}
			break;
		case "Bubble":
			if(myBubble == null) {
				myBubble = new BubbleSort();
			}
			break;
		}
	}
	
	/**
	 * This method is used to add data to a data structure. It adds the provided data to any
	 * active data structure if the data is not null.
	 * @param obj : the data value that we are adding to the DS
	 * @param optional : this optional boolean parameter tells if we are appending or prepending to the LL
	 * @author Cole Mayo
	 */
	public void add(T obj, boolean optional) { 
		if(obj == null) { // prevent null from being added
			return;
		}
		if(activeLL && myLL != null){
			if(optional == false) {
				myLL.append(obj);
			}
			else {
				myLL.prepend(obj);
			}
		}
		if(activeStack && myStack != null) {
			myStack.push(obj);
		}
		if(activeQueue && myQueue != null) {
			myQueue.enqueue(obj);
		}
		if(activeInsertion && myInsertion != null) {
	        myInsertion.append((Integer) obj);
	        myInsertion.startSort();
	    }
		if(myBubble != null) {
			myBubble.append((Integer) obj);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This method is used to call the linked list insertion and notify observers that the linked list
	 * may have changed.
	 * @param obj : data that is being inserted
	 * @param index : index where the data should be inserted
	 * @author Cole Mayo
	 */
	public void insert(T obj, int index) {
		if(myLL != null && obj != null) {
			myLL.insert(obj, index);
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * This method is used to call each active DS/algo's remove or remove equivalent method (e.g., pop).
	 * @param obj : data value that should be removed
	 * @author Cole Mayo
	 */
	public void remove(T obj) {
		if(obj == null) {
			if(activeStack && myStack != null) {
				myStack.pop();
			}
			else if(activeQueue && myQueue != null) {
				myQueue.dequeue();
			}
			else {
				return;
			}
		}
		if(activeLL && myLL != null){
			myLL.remove(obj);
		}
		if(myInsertion != null) {
			myInsertion.remove((Integer) obj);
		}
		if(myBubble != null) {
			myBubble.remove((Integer) obj);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * TODO this method needs revision
	 * @param obj
	 * @return 
	 */
	public Object find(T obj) {
		if(activeLL && myLL != null){
			return myLL.find(obj);
		}
		if(activeStack && myStack != null) {
			return myStack.peek();
		}
		if(activeQueue && myQueue != null) {
			return myQueue.peek();
		}
		if(activeInsertion && myInsertion != null) {
			return myInsertion.find((Integer) obj);
		}
		if(myBubble != null) {
			return myBubble.find((Integer) obj);
		}
		else {
			return null;
		}
	}
	
	/**
	 * This method is used to call each active DS/algo's reset or reset equivalent method (e.g., clear).
	 * @author Cole Mayo
	 */
	public void reset() {
		if(activeLL && myLL != null){
			myLL.reset();
		}
		if(activeStack && myStack != null) {
			myStack.reset();
		}
		if(activeQueue && myQueue != null) {
			myQueue.reset();
		}
		if(activeInsertion && myInsertion != null) {
			myInsertion.reset();
		}
		if(myBubble != null) {
			myBubble.reset();
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * This method is used to save the game's state so it can be loaded in the future.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @author: Cole Mayo
	 */
	public void save() throws IOException {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save_one.dat"))) {
			if(myLL != null){
		        oos.writeObject(myLL);
			}
			if(myStack != null) {
		        oos.writeObject(myStack);
			}
			if(myQueue != null) {
		        oos.writeObject(myQueue);
			}
			if(myInsertion != null) {
		        oos.writeObject(myInsertion);
			}
			if(myBubble!= null) {
		        oos.writeObject(myBubble);
			}
	    }
	    catch (IOException e) {
	        e.printStackTrace(); // or handle the exception appropriately
	    }
	}
	
	/**
	 * This method is used to load saved data structures and algorithms. It tries to load from the
	 * file "save_one.dat". If the files exists, the method uses a loop to read from the input stream
	 * to load up to 5 saved data structures or algorithms.
	 * @author Cole Mayo
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		int count = 0;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save_one.dat"))){
			while(true) {
				Object ds = ois.readObject();
				if(ds instanceof LinkedList) {
					myLL = (LinkedList<T>) ds;
					count++;
				}
				else if(ds instanceof Queue) {
					myQueue = (Queue<T>) ds;
					count++;
				}
				else if(ds instanceof Stack) {
					myStack = (Stack<T>) ds;
					count++;
				}
				else if(ds instanceof BubbleSort) {
					myBubble = (BubbleSort) ds;
					count++;
				}
				else if(ds instanceof InsertionSort) {
					myInsertion = (InsertionSort) ds;
					count++;
				}
				if(count >= 5) {
					break;
				}
			}
		} catch(IOException | ClassNotFoundException e) {
			return;
		}
	}
	
	/**
	 * Method to get the stack elements or returns empty list if no stack exists 
	 * 
	 * @return List<T> of stack elements 
	 * @author thomasurias
	 */
	public List<T> getStackElements() {
	    if (myStack != null) {
	        return myStack.getStackElements();
	    }
	    return new ArrayList<>();
	}
	
	public InsertionSort getInsertionSort() {
	    return myInsertion;
	}
	
	public BubbleSort getBubbleSort() {
	    return myBubble;
	}
	
	public Object getStack() {
		return myStack;
	}
	
	/**
	 * Method to get the listing being sorted by the InsertionSort class
	 * 
	 * @return ArrayList<T> of elements being sorted in InsertionSort 
	 * @author thomasurias
	 */
	public ArrayList<Integer> getInsertionSortElements() {
	    if (myInsertion != null) {
	        return myInsertion.getlistToSort();
	    }
	    return new ArrayList<>(); // return empty if insertion sort is not active
	}
	
	public ArrayList<Integer> getBubbleSortElements() {
	    if (myBubble != null) {
	        return myBubble.getlistToSort();
	    }
	    return new ArrayList<>(); // return empty if insertion sort is not active
	}

	public Queue<T> getQueue() {
	    return myQueue;
	}
		
	public ArrayList<T> getLL(){
		return myLL.getLL();
	}

	public boolean hasStack() {
	    return myStack != null;
	}

	public boolean hasLinkedList() {
	    return myLL != null;
	}

	public boolean hasQueue() {
	    return myQueue != null;
	}

	public boolean hasInsertionSort() {
	    return myInsertion != null;
	}

	public boolean hasBubbleSort() {
	    return myBubble != null;
	}
}

