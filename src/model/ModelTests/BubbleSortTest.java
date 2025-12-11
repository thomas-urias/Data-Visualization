package model.ModelTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.BubbleSort;

class BubbleSortTest {

    private BubbleSort bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
        bubbleSort.getlistToSort().clear(); // start with empty list for testing
    }

    @Test
    void testAppendAndRemove() {
        bubbleSort.append(5);
        bubbleSort.append(3);
        bubbleSort.append(8);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(5, 3, 8));
        assertEquals(expected, bubbleSort.getlistToSort());

        bubbleSort.remove(3);
        expected = new ArrayList<>(Arrays.asList(5, 8));
        assertEquals(expected, bubbleSort.getlistToSort());
    }

    @Test
    void testFind() {
        bubbleSort.append(10);
        bubbleSort.append(20);
        bubbleSort.append(30);

        assertEquals(1, bubbleSort.find(20));
        assertEquals(-1, bubbleSort.find(99)); // not present
    }

    @Test
    void testStepSortFull() {
        bubbleSort.append(5);
        bubbleSort.append(1);
        bubbleSort.append(3);

        bubbleSort.startSort();

        // Keep stepping until sorting is done
        while (bubbleSort.stepSort()) {}

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 3, 5));
        assertEquals(expected, bubbleSort.getlistToSort());
    }

    @Test
    void testReset() {
        bubbleSort.append(2);
        bubbleSort.append(1);

        bubbleSort.reset(); // should re-initialize with 10 random elements
        assertEquals(10, bubbleSort.getlistToSort().size());
    }
}
