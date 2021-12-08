package edu.iastate.cs228.hw3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class StoutListTest {
    StoutList list;

    @BeforeEach
    void setup() {
        list = new StoutList<>(4);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("add(item) and add(pos, item)")
    void add() {
        list.add(4);
        list.add(5);
        list.add(0, 3);
        list.add(0, 2);
        list.add(0, 1);
        list.add(5, 6);
        list.add(5, 6);
        list.add(3, 69);
        list.add(3, 69);

        assertEquals("[(1, 2, -, -), (3, 69, 69, -), (4, 5, 6, 6)]", list.toStringInternal());
    }

    @org.junit.jupiter.api.Test
    void size() {
        list.add(5); list.add(5); list.add(5);
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("remove(pos)")
    void remove() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        list.remove(4);
        list.remove(4);

        assertEquals(4, list.get(3));
    }

    @Test
    @DisplayName("Iterator contains()")
    void iterator0() {
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator it = list.iterator();
        if (!list.contains(1) && list.contains(5)) fail();
    }

    @org.junit.jupiter.api.Test
    void iterator1() {
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator it = list.iterator();

        Integer[] out = new Integer[3];
        if (!it.hasNext()) fail("hasNext() returned false when should be true");
        for (int i = 0; i < 3; i++) {
            out[i] = (Integer) it.next();
        }
        try {
            it.next();
        } catch(Exception e) {
            assertArrayEquals(new Integer[]{1, 2, 3}, out);
            return;
        }
        fail("next() did not throw NoSuchElementException at end");
    }

    @Test
    @DisplayName("ListIterator previous()")
    void listIterator0() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        ListIterator it = list.listIterator(3);
        it.next();
        it.next();
        it.next();
        it.previous();
        it.previous();
        it.previous();
        it.previous();
        assertEquals("[(1, 2, | 3, 4), (5, 6, -, -)]", list.toStringInternal(it));
    }

    @Test
    @DisplayName("ListIterator indexOf()")
    void listIterator1() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        int[] is = new int[3];
        is[0] = list.indexOf(1);
        is[1] = list.indexOf(5);
        is[2] = list.indexOf(7);

        assertArrayEquals(new int[]{0, 4, -1}, is);
    }
}