package model.ModelTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.LinkedList;

class LLTests {

    private LinkedList<Integer> list;

    @BeforeEach
    void build() {
        list = new LinkedList<>();
    }

    @Test
    void testPrependAppendAndSize() {
        list.prepend(2);
        list.prepend(1);
        list.append(3);
        assertEquals(3, list.size());
        assertEquals(List.of(1, 2, 3), list.getLL());
    }

    @Test
    void testInsert() {
        list.append(1);
        list.append(3);
        list.insert(2, 1);
        assertEquals(List.of(1, 2, 3), list.getLL());

        int sz = list.size();
        list.insert(99, -1);
        list.insert(99, sz + 1);
        list.insert(null, 1);
        assertEquals(sz, list.size());
    }

    @Test
    void testRemove() {
        list.append(1);
        list.append(2);
        list.append(3);

        list.remove(1);
        assertEquals(List.of(2, 3), list.getLL());

        list.remove(3);
        assertEquals(List.of(2), list.getLL());

        list.remove(2); 
        assertTrue(list.getLL().isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveNotFoundAndNull() {
        list.append(1);
        list.append(2);
        list.remove(5);
        list.remove(null);
        assertEquals(List.of(1, 2), list.getLL());
    }

    @Test
    void testFind() {
        list.append(10);
        list.append(20);
        list.append(30);

        assertEquals(0, list.find(10));
        assertEquals(1, list.find(20));
        assertEquals(2, list.find(30));
        assertEquals(-1, list.find(40));
        assertEquals(-1, list.find(null));
    }

    @Test
    void testReset() {
        list.append(1);
        list.append(2);
        list.reset();
        assertEquals(0, list.size());
        assertTrue(list.getLL().isEmpty());
    }
}