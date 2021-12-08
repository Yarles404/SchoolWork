package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Charles Yang
 *
 * Tests Reseller
 */
class ResellerTest extends CellTest{
    @Test
    void who() {
        TownCell R = new Reseller(newTown(), 1, 1);
        assertEquals(State.RESELLER, R.who());
    }

    @Test
    @DisplayName("3a, 3- Casual")
    void next1() throws FileNotFoundException {
        Town t = new Town("ResellerTest.txt");
        assertTrue(t.grid[1][1].next(newTown()) instanceof Empty);
    }

    @Test
    @DisplayName("3b, 3+ Empty")
    void next2() throws FileNotFoundException {
        Town t = new Town("ResellerTest.txt");
        t.grid[0][2] = new Casual(t, 0, 2);
        for (int i = 0; i < 3; i++) t.grid[2][i] = new Empty(t, 2, i);
        assertTrue(t.grid[1][1].next(newTown()) instanceof Empty);
    }

    @Test
    @DisplayName("6b, 5+ Casual")
    void next3() throws FileNotFoundException {
        Town t = new Town("ResellerTest.txt");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 1 && c == 1) continue;
                t.grid[r][c] = new Casual(t, r, c);
            }
        }
        assertTrue(t.grid[1][1].next(newTown()) instanceof Streamer);
    }
}