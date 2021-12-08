package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Charles Yang
 *
 * Tests Outage
 */
class OutageTest extends CellTest{
    @Test
    void who() {
        TownCell O = new Outage(newTown(), 0 , 0);
        assertEquals(State.OUTAGE, O.who());
    }

    @Test
    @DisplayName("4, Outage")
    void next() {
        TownCell O = new Outage(newTown(), 0 , 0);
        assertTrue(O.next(newTown()) instanceof Empty);
    }
}