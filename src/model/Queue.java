package model;

import java.io.Serializable;

/**
 * A simple generic queue implemented using a circular array.
 * Supports enqueue, dequeue, peek, checking if empty, and getting the size.
 * The array will allocate more space when needed.
 *
 * @param <T> - Type of items stored in the queue
 * 
 * @Author Emmanuel Calvin
 */
public class Queue<T> implements Serializable {
	private static final long serialVersionUID = 1L;

    /** Array to store the queue items. */
    private T[] queue;

    /** Index of the front element. */
    private int front;

    /** Index where the next item will be inserted. */
    private int rear;

    /** Number of items in the queue. */
    private int size;

    /**
     * Creates new empty queue with an initial capacity 10.
     */
    @SuppressWarnings("unchecked")
    public Queue() {
        queue = (T[]) new Object[10];
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Adds an item to the back of the queue.
     * If the array is full, Doubles the capacity(resize).
     *
     * @param item the item to be added
     */
    public void enqueue(T item) {
        if (size == queue.length) {
            resize();
        }
        queue[rear] = item;
        rear = (rear + 1) % queue.length;
        size++;
    }

    /**
     * Removes the item at the front of the queue.
     *
     * @throws IllegalStateException - if the queue is empty
     */
    public void dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        queue[front] = null;
        front = (front + 1) % queue.length;
        size--;
    }

    /**
     * Returns the item at the front of the queue W/o removing it.
     *
     * @return the front item.
     * @throws IllegalStateException - if the queue is empty.
     */
    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue[front];
    }

    /**
     * Checks if the queue has no elements(isEmpty), returns true.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements currently in the queue.
     * @return number of queue elements
     */
    public int size() {
        return size;
    }

    /**
     * Double capacity of the internal array, and
     * Copies the elements over in queue order.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newQueue = (T[]) new Object[queue.length * 2];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }

        queue = newQueue;
        front = 0;
        rear = size;
    }
    
    /**
     * Resets the queue.
     */
    public void reset() {
        for (int i = 0; i < queue.length; i++) {
            queue[i] = null;
        }
        front = 0;
        rear = 0;
        size = 0;
    }
    
    public T[] getRawArray() {
        return queue;
    }

    public int getFrontIndex() {
        return front;
    }

    public int getRearIndex() {
        return rear;
    }

    public int getCapacity() {
        return queue.length;
    }
}
