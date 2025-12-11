package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple generic stack using an array.
 * Supports push, pop, peek, checking if empty, and getting the size.
 * The array grows automatically when needed.
 *
 * @param <T> the type of items in the stack
 */
public class Stack<T> implements Serializable {
    private static final long serialVersionUID = 1L;

	/** Array to store stack items */
    private T[] stack;

    /** Index of the top element (-1 if empty) */
    private int top;

    /**
     * Creates a new stack with a starting size of 10.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        stack = (T[]) new Object[10];
        top = -1;
    }

    /**
     * Adds an item to the top of the stack.
     * If the array is full, it will grow bigger.
     *
     * @param obj the item to add
     */
    public void push(T obj) {
        if (top == stack.length - 1) {
            resize();
        } 
        top++;
        stack[top] = obj;
    }

    /**
     * Removes the top item from the stack.
     *
     * @throws IllegalStateException if the stack is empty
     */
    public void pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        stack[top] = null;
        top--;
    }

    /**
     * Looks at the top item without removing it.
     *
     * @return the top item
     * @throws IllegalStateException if the stack is empty
     */
    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[top];
    }

    /**
     * Checks if the stack has no items.
     *
     * @return true if empty, false if it has items
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Returns how many items are in the stack.
     *
     * @return the number of items
     */
    public int size() {
        return top + 1;
    }
    
    /**
     * Methods that resets the current stack instance 
     * with a new array and setting top back to -1
     */
    @SuppressWarnings("unchecked")
	public void reset() {
    	stack = (T[]) new Object[10];
        top = -1;
    }
    
    /**
     * Makes the array bigger when it is full.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newStack = (T[]) new Object[stack.length * 2];
        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
    }
    
    /**
     * Returns a list of all elements in the stack from bottom to top.
     * Model can safely call this to get the stack contents.
     */
    public List<T> getStackElements() {
        List<T> elements = new ArrayList<>();
        for (int i = 0; i <= top; i++) {
            elements.add(stack[i]);
        }
        return elements;
    }
}
