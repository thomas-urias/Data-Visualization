package model.ModelTests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Stack;

class StackTest {
	private Stack<Integer> stack;
	
	@BeforeEach
	public void setUp() {
		stack = new Stack<>();
	}
	
	@Test
	void testPushAndPeek() {
		stack.push(5);
		assertEquals(5, stack.peek(), "Peek should return the last pushed item");
		
		stack.push(6);
		assertEquals(6, stack.peek(), "Peek should return the new pushed item");
	}
	
	@Test
	void testPop() {
		stack.push(5);
		stack.push(6);
		stack.pop();
		assertEquals(5, stack.peek(), "After pop, peek should return the previous item");
		assertEquals(1, stack.size(), "After pop, stack should be the size - 1");
		
		stack.pop();
		assertTrue(stack.isEmpty(), "Stack should be empty after popping all items");
	}
	
	@Test
	void testPopEmptyStackThrowsIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			stack.pop();
		});
	}
	
	@Test
	void testPeekEmptyStackThrowsIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			stack.peek();
		});
	}
	
	@Test
	void testIsEmptyAndSize() {
		assertTrue(stack.isEmpty(), "New stack should be empty");
		assertEquals(0, stack.size(), "New stack size should be 0");
		
		stack.push(1);
		assertFalse(stack.isEmpty(), "Stack should not be empty after push");
		assertEquals(1, stack.size(), "Stack size should increase by 1 after push");
	}
	
	@Test
	void testMultiplePushesAndResizes() {
		for(int i = 1; i < 11; i++) {
			stack.push(i);
		}
		
		assertEquals(10, stack.size(), "Stack size should be 10 after pushing 10 times");
		assertEquals(10, stack.peek(), "Stack should peek last item pushed");
	}
	
	@Test
	void testPushAndPopsInSequence() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		assertEquals(3, stack.peek());
		
		stack.pop();
		assertEquals(2, stack.peek());
		
		stack.pop();
		assertEquals(1, stack.peek());
		
		stack.pop();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	void testGetStackElements() {
	    stack.push(1);
	    stack.push(2);
	    stack.push(3);
	    List<Integer> elements = stack.getStackElements();
	    assertEquals(List.of(1,2,3), elements, "getStackElements should return bottom-to-top list");
	}
	
	@Test
	void testReset() {
	    stack.push(1);
	    stack.push(2);
	    stack.reset();
	    assertTrue(stack.isEmpty(), "Stack should be empty after reset");
	    assertEquals(0, stack.size(), "Stack size should be 0 after reset");
	}

	@Test
	void testResize() {
	    for(int i = 1; i <= 15; i++) {  // initial capacity = 10
	        stack.push(i);
	    }
	    assertEquals(15, stack.size(), "Stack size should be 15 after resizing");
	    assertEquals(15, stack.peek(), "Stack should peek last item pushed after resize");
	}
}


