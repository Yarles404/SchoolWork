package edu.iastate.cs228.hw1;

/**
 * @author Charles Yang
 *
 * Represents the Outage subclass of TownCell
 */
public class Outage extends TownCell {
    /**
     * Constructor. Initiates super.
     *
     * @param p Town which the cell is in
     * @param r row
     * @param c column
     */
    public Outage(Town p, int r, int c) {
        super(p, r, c);
    }

    /**
     * @return Enum for Outage
     */
    @Override
    public State who() {
        return State.OUTAGE;
    }

    /**
     * Implements Outage's + additional rules
     * @param tNew: town of the next cycle
     * @return Cell type next cycle
     */
    @Override
    public TownCell next(Town tNew) {
        // Outage always becomes Empty
        return new Empty(tNew, row, col);
    }
}
