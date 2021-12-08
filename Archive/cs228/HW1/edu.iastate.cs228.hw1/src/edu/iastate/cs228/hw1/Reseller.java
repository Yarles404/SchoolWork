package edu.iastate.cs228.hw1;

/**
 * @author Charles Yang
 *
 * Represents the Reseller subclass of TownCell
 */
public class Reseller extends TownCell {
    /**
     * Constructor. Initiates super.
     *
     * @param p Town which the cell is in
     * @param r row
     * @param c column
     */
    public Reseller(Town p, int r, int c) {
        super(p, r, c);
    }

    /**
     * @return Enum for Reseller
     */
    @Override
    public State who() {
        return State.RESELLER;
    }

    /**
     * Implements Reseller's + additional rules
     * @param tNew: town of the next cycle
     * @return Cell type next cycle
     */
    @Override
    public TownCell next(Town tNew) {
        int nCensus[] = new int[5];
        this.census(nCensus);
        TownCell output = new Reseller(tNew, row, col);

        if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {
            output = new Empty(tNew, row, col);
        }
        else if (nCensus[CASUAL] >= 5) {
            output = new Streamer(tNew, row, col);
        }

        return output;
    }
}
