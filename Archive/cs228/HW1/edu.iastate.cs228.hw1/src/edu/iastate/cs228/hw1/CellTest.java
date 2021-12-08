package edu.iastate.cs228.hw1;

import edu.iastate.cs228.hw1.Town;

/**
 * @author Charles Yang
 *
 * Helper for creating arbitrary new towns
 */
public abstract class CellTest {
    protected Town newTown() {
        return new Town(3, 3);
    }
}
