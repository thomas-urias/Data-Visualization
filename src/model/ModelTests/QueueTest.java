package model.ModelTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Queue;

class QueueTest {

    private Queue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new Queue<>();
    }

    @Test
    void testEnqueueAndPeek() {
        queue.enqueue(10);
        assertEquals(10, queue.peek(), "Peek should return the first item enqueued");

        queue.enqueue(20);
        assertEquals(10, queue.peek(), "Peek should still return the front item");
    }

    @Test
    void testDequeue() {
        queue.enqueue(10);
        queue.enqueue(20);

        queue.dequeue();
        assertEquals(20, queue.peek(), "After dequeue, peek should return the next item");
        assertEquals(1, queue.size(), "Size should decrease after dequeue");

        queue.dequeue();
        assertTrue(queue.isEmpty(), "Queue should be empty after dequeuing all items");
    }

    @Test
    void testDequeueEmptyQueueThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    @Test
    void testPeekEmptyQueueThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    @Test
    void testIsEmptyAndSize() {
        assertTrue(queue.isEmpty(), "New queue should be empty");
        assertEquals(0, queue.size(), "New queue size should be 0");

        queue.enqueue(1);
        assertFalse(queue.isEmpty(), "Queue should not be empty after enqueue");
        assertEquals(1, queue.size(), "Queue size should increase after enqueue");
    }

    @Test
    void testMultipleEnqueuesAndResize() {
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
        }

        assertEquals(20, queue.size(), "Queue size should be 20 after enqueueing 20 items");
        assertEquals(0, queue.peek(), "Peek should return the first item enqueued");
    }

    @Test
    void testEnqueueDequeueSequence() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.peek());

        queue.dequeue();
        assertEquals(2, queue.peek());

        queue.dequeue();
        assertEquals(3, queue.peek());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
}
