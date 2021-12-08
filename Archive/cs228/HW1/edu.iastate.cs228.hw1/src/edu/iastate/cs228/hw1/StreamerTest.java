package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Charles Yang
 *
 * Tests Streamer
 */
class StreamerTest extends CellTest{
    @Test
    void who() {
        TownCell S = new Streamer(newTown(), 1, 1);
        assertEquals(State.STREAMER, S.who());
    }

    @Test
    @DisplayName("6a,  1 Empty/Outage")
    void next1() throws FileNotFoundException {
        Town t = new Town("StreamerTest.txt");
        t.grid[0][1] = new Casual(t, 0 , 1);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Reseller);
    }

    @Test
    @DisplayName("2a, 1 Reseller")
    void next2() throws FileNotFoundException {
        Town t = new Town("StreamerTest.txt");
        assertTrue(t.grid[1][1].next(newTown()) instanceof Outage);
    }

    @Test
    @DisplayName("2b, 1 Outage")
    void next3() throws FileNotFoundException {
        Town t = new Town("StreamerTest.txt");
        t.grid[0][2] = new Casual(t, 0 , 2);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Empty);
    }
}