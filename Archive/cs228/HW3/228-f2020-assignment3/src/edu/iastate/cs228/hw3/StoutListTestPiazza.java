package edu.iastate.cs228.hw3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class StoutListTestPiazza {
  private StoutList<Integer> list;

  @BeforeEach
  void setup() {
    list = new StoutList<>(4);
    for (int i = 0; i < 11; i++) {
      list.add(i);
    }

  }

  private void log(String msg) {
    System.out.println(msg);
  }

  private <E extends Comparable<? super E>> void log(StoutList<E> l) {
    log(l.toStringInternal());
  }

  private void log() {
    log(list.toStringInternal());
  }

  @Test
  void size() {
    assertEquals(11, list.size());
  }

  @Test
  void addRemove() {
    StoutList<String> list = new StoutList<>();
    // initialize list to look like the list provided in the example:
    list.add("A");
    list.add("B");
    list.add("X");
    list.add("X2");
    list.add("C");
    list.add("D");
    list.add("E");
    list.remove(2);
    list.remove(2);

    assertThrows(NullPointerException.class, () -> list.add(null));

    // perform actions as in the example:
    log("Addition Example: ");
    sanityCheckNodes(list);
    assertEquals("[(A, B, -, -), (C, D, E, -)]", list.toStringInternal(null));
    list.add("V");
    sanityCheckNodes(list);
    assertEquals("[(A, B, -, -), (C, D, E, V)]", list.toStringInternal(null));
    list.add("W");
    sanityCheckNodes(list);
    assertEquals("[(A, B, -, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal(null));
    list.add(2, "X");
    sanityCheckNodes(list);
    assertEquals("[(A, B, X, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal(null));
    list.add(2, "Y");
    sanityCheckNodes(list);
    assertEquals("[(A, B, Y, X), (C, D, E, V), (W, -, -, -)]", list.toStringInternal(null));
    list.add(2, "Z");
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]", list.toStringInternal(null));

    log("Remove Example: ");
    list.remove(9);
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]", list.toStringInternal(null));
    list.remove(3);
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (X, C, -, -), (D, E, V, -)]", list.toStringInternal(null));
    list.remove(3);
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (C, D, -, -), (E, V, -, -)]", list.toStringInternal(null));
    list.remove(5);
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (C, D, -, -), (V, -, -, -)]", list.toStringInternal(null));
    list.remove(3);
    sanityCheckNodes(list);
    assertEquals("[(A, B, Z, -), (D, V, -, -)]", list.toStringInternal(null));

    log("Testing bounds... ");
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "Illegal!"));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(6, "Illegal!"));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(6));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
  }

  @Test
  void sort() {
    int[] arr = { 4, 1, 5, 2, 3, 9, 8, 7, 10, 0, 6 };
    list = new StoutList<>(4);
    for (int j : arr) {
      list.add(j);
    }
    list.remove(7);
    log();
    log("Sorted:");
    list.sort();
    sanityCheckNodes(list);
    assertEquals(10, list.size());
    assertEquals("[(0, 1, 2, 3), (4, 5, 6, 8), (9, 10, -, -)]", list.toStringInternal());
  }

  @Test
  void sortReverse() {
    int[] arr = { 4, 1, 5, 2, 3, 9, 8, 7, 10, 0, 6 };
    list = new StoutList<>(4);
    for (int j : arr) {
      list.add(j);
    }
    list.remove(5); // make a gap to make sure the sort actually condenses the array correctly
    assertEquals(10, list.size());
    log();
    log("Sorted in reverse:");
    list.sortReverse();
    sanityCheckNodes(list);
    assertEquals(10, list.size());
    assertEquals("[(10, 8, 7, 6), (5, 4, 3, 2), (1, 0, -, -)]", list.toStringInternal());
  }

  @Test
  void iterator() {
    Iterator<Integer> it = list.iterator();
    for (int i = 0; i < 11; i++) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next());
      if (i == 5) {
        assertDoesNotThrow(it::remove);
        assertThrows(IllegalStateException.class, it::remove);
      }
    }
    sanityCheckNodes(list);

    log("Should be missing element 5:");
    log();

    it = list.iterator();
    for (int i = 0; i < 11; i++) {
      assertTrue(it.hasNext());
      if (i == 5) continue; // element 5 should be missing now...
      assertEquals(i, it.next());
    }

    sanityCheckNodes(list);

    it = list.iterator();

    log("Removing the rest of the list:");
    // remove the rest of the list
    for (int i = 0; i < 10; i++) {
      assertDoesNotThrow(it::next);
      assertDoesNotThrow(it::remove);
    }

    sanityCheckNodes(list);
  }

  @Test
  void listIterator() {
    // A bunch of random listIterator commands to hopefully find a problem with your iterator if there is one

    ListIterator<Integer> it = list.listIterator();
                                 assertEquals(0, it.nextIndex());
                                 assertEquals(-1, it.previousIndex());
                                 assertTrue(it.hasNext());
                                 assertFalse(it.hasPrevious());
                                 assertThrows(NoSuchElementException.class, it::previous);
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(0, it.next()));
    sanityCheckNodes(list, it);  assertEquals(1, it.nextIndex());
                                 assertEquals(0, it.previousIndex());
                                 assertTrue(it.hasNext());
                                 assertTrue(it.hasPrevious());
                                 assertDoesNotThrow(it::remove);
    sanityCheckNodes(list, it);  assertThrows(IllegalStateException.class, it::remove);
                                 assertDoesNotThrow(() -> it.add(121));
                                 assertThrows(IllegalStateException.class, () -> it.set(121));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(121, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(it::remove);
                                 assertThrows(IllegalStateException.class, () -> it.set(121));
                                 assertDoesNotThrow(() -> assertEquals(1, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(1, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> it.add(0));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(0, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(0, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(1, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> it.set(142));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(142, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> it.set(132)); // 1 should now be 132, no longer 142
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> it.add(412)); // should be inserted BEFORE 142 (see add javaDoc)
    sanityCheckNodes(list, it);  assertThrows(IllegalStateException.class, () -> it.set(121));
                                 assertThrows(IllegalStateException.class, it::remove);
                                 assertDoesNotThrow(() -> assertEquals(132, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(2, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(it::remove);
    assertEquals("[(0, 412, 132, -), (| 3, 4, -, -), (5, 6, 7, -), (8, 9, 10, -)]", list.toStringInternal(it)); // verify the merge happened right

    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(132, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(412, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(it::remove);
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(132, it.next()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(132, it.previous()));
    sanityCheckNodes(list, it);  assertDoesNotThrow(() -> assertEquals(0, it.previous()));
    sanityCheckNodes(list, it);  assertThrows(NullPointerException.class, () -> it.add(null));

    // Verify listIterator(index):
    ListIterator<Integer> it2 = list.listIterator(5);
    assertDoesNotThrow(() -> assertEquals(5, it2.previous()));
    assertDoesNotThrow(() -> assertEquals(5, it2.next()));
    assertDoesNotThrow(() -> assertEquals(6, it2.next()));

    // verify end:
    assertDoesNotThrow(() -> {
      for (int i = 0; i < 10; i++) {
        it.next();
      }
    });
    assertFalse(it.hasNext());
    assertTrue(it.hasPrevious());
    assertThrows(NoSuchElementException.class, it::next);
    assertEquals(10, it.nextIndex());
  }

  @Test
  void nodeSize2() {
    list = new StoutList<>(2);
    for (int i = 0; i < 11; i++) {
      list.add(i);
    }

    sanityCheckNodes(list);
    list.add(4, 90);
    sanityCheckNodes(list);
    list.add(5, 21);
    sanityCheckNodes(list);
    list.add(0, 1291);
    sanityCheckNodes(list);
    list.remove(8);
    sanityCheckNodes(list);
    list.remove(0);
    sanityCheckNodes(list);
    list.remove(0);
    sanityCheckNodes(list);
    list.remove(0);
    sanityCheckNodes(list);
    list.remove(0);
    sanityCheckNodes(list);
  }

  public <E extends Comparable<? super E>> void sanityCheckNodes(StoutList<E> list) {
    sanityCheckNodes(list, null);
  }

  // This is some Reflection API stuff that is not important to understand and we haven't learned
  // I just didn't want to make everyone change Node to public, but I wanted to check the list structure
  public <E extends Comparable<? super E>> void sanityCheckNodes(StoutList<E> list, ListIterator<E> it) {
    try {
      log(list.toStringInternal(it));
      Class<?> n = null;
      for (Class<?> x : list.getClass().getDeclaredClasses()) {
        if (x.getSimpleName().equals("Node")) {
          n = x;
        }
      }
      if (n == null) fail("Could not find Node inner class!");
      Field previous = n.getField("previous");
      previous.setAccessible(true);
      Field next = n.getField("next");
      next.setAccessible(true);
      Field data = n.getField("data");
      data.setAccessible(true);
      Field count = n.getField("count");
      count.setAccessible(true);
      Field tail = list.getClass().getDeclaredField("tail");
      tail.setAccessible(true);
      Field nodeSize = list.getClass().getDeclaredField("nodeSize");
      nodeSize.setAccessible(true);

      Object current = next.get(list.head);
      Object prev = list.head;
      while (current != tail.get(list)) {
        if (previous.get(current) != prev) fail("Error! This node's previous is incorrect!");
        if (next.get(current) == null) fail("Error! This node is missing a next!");
        int i;
        // scan through data until we find a null item or we hit the count
        for (i = 0; i < (int) count.get(current) && ((Object[]) data.get(current))[i] != null; i++) ;
        // If i is less than nodeSize / 2 and the next element is not the tail, something's wrong:
        if (i < (int) nodeSize.get(list) / 2 && next.get(current) != tail.get(list))
          fail("Error! This node's data has too few items! It probably should have been merged...");
        if (i == 0) fail("Empty nodes are not allowed except for head and tail!");
        // if count is not equal to i, then we have a null in the valid data range...
        if (i != (int) count.get(current)) fail("Error! Count is incorrect!");
        // make sure all the rest of the elements in data are null:
        for (; i < (int) count.get(current); i++) {
          if (((Object[]) data.get(current))[i] != null) fail("Error! This node's data has a null gap!");
        }
        prev = current;
        current = next.get(current);
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Whoops... something went wrong with the Reflection API. Did you change the names of any provided classes and/or fields?");
    }
  }
}