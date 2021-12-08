package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Charles Yang
 *
 * Tests Empty
 */
class EmptyTest extends CellTest {
    @Test
    void who() throws FileNotFoundException {
        Town t = new Town("EmptyTest.txt");
        assertEquals(State.EMPTY, t.grid[1][1].who());
    }

    @Test
    @DisplayName("6a, 1 Outage")
    void next1() throws FileNotFoundException {
        Town t = new Town("EmptyTest.txt");
        assertTrue(t.grid[1][1].next(newTown()) instanceof Reseller);
    }

    @Test
    @DisplayName("5, Empty")
    void next2() throws FileNotFoundException {
        Town t = new Town("EmptyTest.txt");
        t.grid[1][0] = new Casual(t, 1, 0);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Reseller);
    }
}