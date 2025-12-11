package model.ModelTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.InsertionSort;

class InsertionSortTest {

    private InsertionSort sortInstance;

    @BeforeEach
    void setUp() {
        sortInstance = new InsertionSort();
    }

    @Test
    void testInitialArraySizeIs10() {
        assertEquals(10, sortInstance.getlistToSort().size(),
                "Array should be initialized with 10 integers");
    }

    @Test
    void testAppendIncreasesSizeByOne() {
        int before = sortInstance.getlistToSort().size();
        sortInstance.append(42);
        int after = sortInstance.getlistToSort().size();

        assertEquals(before + 1, after, "append() should increase array size by 1");
        assertTrue(sortInstance.getlistToSort().contains(42), "append() should add the given element");
    }

    @Test
    void testRemoveRemovesElement() {
        sortInstance.append(500);
        assertTrue(sortInstance.getlistToSort().contains(500));

        int before = sortInstance.getlistToSort().size();

        sortInstance.remove(500);

        assertEquals(before - 1, sortInstance.getlistToSort().size(),
                "remove(obj) should decrease size by 1");
        assertFalse(sortInstance.getlistToSort().contains(500),
                "remove(obj) should remove the object from the list");
    }

    @Test
    void testFindReturnsCorrectIndex() {
        sortInstance.append(12345);
        int index = sortInstance.find(12345);

        assertTrue(index >= 0, "find() should return a valid index");
        assertEquals(12345, sortInstance.getlistToSort().get(index));
    }

    @Test
    void testFindReturnsNegativeIfNotFound() {
        int index = sortInstance.find(98765);
        assertEquals(-1, index, "find() should return -1 if element not found");
    }

    @Test
    void testResetCreatesNewArrayOf10() {
        sortInstance.append(999);
        sortInstance.append(888);

        sortInstance.reset();

        assertEquals(10, sortInstance.getlistToSort().size(),
                "reset() should reinitialize array to size 10");
    }

    @Test
    void testResetDoesNotReuseOldArray() {
        ArrayList<Integer> oldRef = sortInstance.getlistToSort();

        sortInstance.reset();
        ArrayList<Integer> newRef = sortInstance.getlistToSort();

        assertNotSame(oldRef, newRef, "reset() should create a new ArrayList object");
    }

    @Test
    void testListIsSortedAfterConstruction() {
        // Repeatedly call stepSort until finished
        sortInstance.startSort();
        while (sortInstance.stepSort()) { }

        ArrayList<Integer> list = sortInstance.getlistToSort();

        for (int i = 1; i < list.size(); i++) {
            assertTrue(list.get(i - 1) <= list.get(i),
                    "List should be sorted after constructor calls sort()");
        }
    }

    @Test
    void testSortMaintainsAllElements() {
        ArrayList<Integer> before = new ArrayList<>(sortInstance.getlistToSort());

        // Sort using stepSort until done
        sortInstance.startSort();
        while (sortInstance.stepSort()) { }

        ArrayList<Integer> after = sortInstance.getlistToSort();
        assertEquals(before.size(), after.size(), "Size should remain the same");

        before.sort(Integer::compareTo);
        assertEquals(before, after, "Sorted list must contain same elements in order");
    }

    @Test
    void testSortAfterAppendSortsCorrectly() {
        sortInstance.append(0);
        sortInstance.append(999);

        sortInstance.startSort();
        while (sortInstance.stepSort()) { }

        ArrayList<Integer> list = sortInstance.getlistToSort();
        for (int i = 1; i < list.size(); i++) {
            assertTrue(list.get(i - 1) <= list.get(i),
                    "List should be sorted after appending elements and sorting");
        }
    }

    @Test
    void testSortOnAlreadySortedListDoesNothing() {
        sortInstance.startSort();
        while (sortInstance.stepSort()) { }

        ArrayList<Integer> before = new ArrayList<>(sortInstance.getlistToSort());

        // Sort again
        sortInstance.startSort();
        while (sortInstance.stepSort()) { }

        ArrayList<Integer> after = sortInstance.getlistToSort();
        assertEquals(before, after,
                "Sorting an already sorted list should not change elements");
    }
}
