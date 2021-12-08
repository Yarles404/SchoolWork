package edu.iastate.cs228.hw1;

import edu.iastate.cs228.hw1.Town;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static edu.iastate.cs228.hw1.ISPBusiness.getProfit;
import static edu.iastate.cs228.hw1.ISPBusiness.updatePlain;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Charles Yang
 *
 * Tests ISPBusiness
 */
class ISPBusinessTest {
    @Test
    void updatePlainTest() {
        Town t1 = new Town(4, 4);
        t1.randomInit(10);
        assertEquals("E E E E \nC C O E \nC O E O \nC E E E \n", updatePlain(t1).toString());
    }

    @Test
    @DisplayName("getProfit Test")
    void getProfitTest() {
        Town t = new Town(5, 3);
        t.randomInit(42);
        assertEquals(3, getProfit(t));
    }
}