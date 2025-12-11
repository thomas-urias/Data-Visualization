package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An instance of this class represents a Linked List. It has append, prepend, insert, 
 * remove, reset, and find functionality.
 * @author Cole Mayo
 */
public class LinkedList <T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private Node head, tail;
	private int sz;
	
	public LinkedList() {
		head = null;
		tail = null;
		sz = 0;
	}
	
	/**
	 * An instance of this class represents a node in the linked list. Each node
	 * has a data value, and a reference to the next node (null if applicable).
	 * @author Cole Mayo
	 */
	private class Node implements Serializable{
		private static final long serialVersionUID = 1L;
		T data;
		Node next;
		private Node(T data) {
			this.data = data;
			this.next = null;
		}
	}
	
	/** 
	 * This method is used to prepend data to the LL.
	 * @param data : data value to be prepended
	 */
	public void prepend(T data) {
		if(data == null) { // prevent node's data value from becoming null
			return;
		}
		Node newNode = new Node(data);
		if(checkEmpty(newNode)) {
			return;
		}
		newNode.next = head;
		head = newNode;
		sz++;
	}
	
	/**
	 * This method is used to append a data value to the LL.
	 * @param data : data value to be appended
	 */
	public void append(T data) { 
		if(data == null) { // prevent node's data value from becoming null
			return;
		}
		Node newNode = new Node(data);
		if(checkEmpty(newNode)) {
			return;
		}
		tail.next = newNode;
		tail = newNode;
		sz++;
	}
	
	/**
	 * This method is used to insert a data value to the LL.
	 * @param data : data value to be inserted
	 * @param index : index where it should be inserted
	 */
	public void insert(T data, int index) {
		if(data == null) { // prevent node's data value from becoming null
			return;
		}
		if(index < 0 || index > sz) {
			return;
		}
		if(index == 0) {
			prepend(data);
			return;
		}
		if(index == sz) {
			append(data);
			return;
		}
		Node prev = head;
		Node curr = head.next;
		int i = 1;
		while(curr!=null) {
			if(i == index) {
				Node newNode = new Node(data);
				newNode.next = curr;
				prev.next = newNode;
				sz++;
				break;
			}
			i++;
			prev = curr;
			curr = curr.next;
		}
	}
	
	/**
	 * This method is used for removing a data value from the LL.
	 * @param data : data value to remove
	 */
	public void remove(T data) {
		if(data == null || sz == 0) {
			return;
		}
		if(head.data.equals(data)) {
			head = head.next;
			sz--;
			if(head == null) {
				tail = null;
			}
			return;
		}
		Node prev = head;
		Node curr = head.next;
		while(curr!=null) {
			if(curr.data.equals(data)) {
				 prev.next = curr.next;
				 sz--;
				 if(curr == tail) {
					 tail = prev;
				 }
				 break;
			}
			prev = curr;
			curr = curr.next;
		}
	}
	
	/**
	 * This method finds a data value in the LL.
	 * @param data : data value to find
	 * @return int representation of the index where data value was found
	 */
	public int find(T data) { // return index if found or -1 if not
		if(data == null) {
			return -1;
		}
		Node curr = head;
		int i = 0;
		while(curr!=null) {
			if(curr.data.equals(data)) {
				return i;
			}
			i++;
			curr = curr.next;
		}
		return -1;
	}
	
	/**
	 * This method resets the LL.
	 */
	public void reset() {
		head = null;
		tail = null;
		sz = 0;
	}
	
	/**
	 * This method returns the current size of the LL.
	 * @return
	 */
	public int size() {
		return sz;
	}
	
	/**
	 * This method check if the LL is empty.
	 * @param newNode : node to be added to LL 
	 * @return : boolean telling if empty or not
	 */
	public boolean checkEmpty(Node newNode) {
		if(sz == 0) { 
			head = newNode;
			tail = newNode;
			sz++;
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to get the LL.
	 * @return : the LL
	 */
	public ArrayList<T> getLL(){
		ArrayList<T> theList = new ArrayList<>();
		Node curr = head;
		while(curr != null) {
			theList.add(curr.data);
			curr = curr.next;
		}
		return theList;
	}
	
}
