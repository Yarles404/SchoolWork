package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

/**
 * @author Charles Yang
 *
 * Tests Town
 */
class TownTest {
    @Test
    @DisplayName("randomInit for given example")
    void randomInit() {
        Town t = new Town(4, 4);
        t.randomInit(10);
        assertEquals(t.toString(),
                "O R O R \n" +
                        "E E C O \n" +
                        "E S O S \n" +
                        "E O R R \n");
    }

    @Test
    @DisplayName("Town file constructor for 3x3")
    void fileConstruct() throws FileNotFoundException {
        Town t = new Town("TownTest.txt");
        assertEquals("E O S \nE R R \nC C C \n", t.toString());
    }

    @Test
    @DisplayName("Get dimensions")
    void getDimensions() {
        Town t = new Town(16,3242);
        assertAll(() -> assertEquals(t.getLength(), 16),
                  () -> assertEquals(t.getWidth(), 3242));

    }
}
