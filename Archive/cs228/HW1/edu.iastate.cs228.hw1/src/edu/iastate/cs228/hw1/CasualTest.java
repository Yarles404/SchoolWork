package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;


/**
 * @author Charles Yang
 *
 * Tests Casual
 */
class CasualTest extends CellTest{

    @Test
    void who() {
        TownCell C = new Casual(new Town(1, 1), 0, 0);
        assertEquals(State.CASUAL, C.who());
    }

    @Test
    @DisplayName("6a, 1 Empty/Outage")
    void next1() throws FileNotFoundException {
        Town t = new Town("CasualTest.txt");
        System.out.println(t);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Reseller);
    }

    @Test
    @DisplayName("1a, 1 Reseller")
    void next2() throws FileNotFoundException {
        Town t = new Town("CasualTest.txt");
        t.grid[2][0] = new Reseller(newTown(), 2, 0);
        t.grid[0][0] = new Empty(newTown(), 2, 0);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Outage);
    }

    @Test
    @DisplayName("1b, 1 Streamer")
    void next3() throws FileNotFoundException {
        Town t = new Town("CasualTest.txt");
        t.grid[2][0] = new Streamer(newTown(), 2, 0);
        t.grid[0][0] = new Empty(newTown(), 2, 0);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Streamer);
    }

    @Test
    @DisplayName("6b, 5+ Casuals")
    void next4() throws FileNotFoundException {
        Town t = new Town("CasualTest.txt");
        t.grid[0][0] = new Empty(newTown(), 2, 0);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Streamer);
    }

    @Test
    @DisplayName("7, no rules apply")
    void next5() throws FileNotFoundException {
        Town t = new Town("CasualTest.txt");
        t.grid[2][0] = new Empty(newTown(), 2, 0);
        t.grid[2][1] = new Empty(newTown(), 2, 0);
        t.grid[2][2] = new Empty(newTown(), 2, 0);
        t.grid[0][0] = new Empty(newTown(), 2, 0);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Casual);
    }
}