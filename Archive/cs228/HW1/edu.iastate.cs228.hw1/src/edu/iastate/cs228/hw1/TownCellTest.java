package edu.iastate.cs228.hw1;

import edu.iastate.cs228.hw1.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Charles Yang
 *
 * Tests TownCell
 */
class TownCellTest {
    /*@Test
    @DisplayName("Testing all TownCell's .who")
    void who() {
        TownCell C = new Casual(new Town(1,1), 0, 0);
        TownCell S = new Streamer(new Town(1,1), 0, 0);
        TownCell R = new Reseller(new Town(1,1), 0, 0);
        TownCell O = new Outage(new Town(1,1), 0, 0);
        TownCell E = new Empty(new Town(1,1), 0, 0);

        assertAll(() -> assertEquals(C.who(), State.CASUAL),
                  () -> assertEquals(S.who(), State.STREAMER),
                  () -> assertEquals(R.who(), State.RESELLER),
                  () -> assertEquals(O.who(), State.OUTAGE),
                  () -> assertEquals(E.who(), State.EMPTY));
    }*/

    @Test
    @DisplayName("Census")
    void Census() throws FileNotFoundException {
        Town t = new Town("TownTest.txt");
        int[] nCensus = new int[5];
        t.grid[1][1].census(nCensus);

        assertAll(() -> assertEquals(1, nCensus[0]),
                  () -> assertEquals(2, nCensus[1]),
                () -> assertEquals(3, nCensus[2]),
                () -> assertEquals(1, nCensus[3]),
                () -> assertEquals(1, nCensus[4])
        );
    }
}