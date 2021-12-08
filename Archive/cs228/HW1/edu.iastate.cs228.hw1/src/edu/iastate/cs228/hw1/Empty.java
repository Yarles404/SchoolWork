package edu.iastate.cs228.hw1;

/**
 * @author Charles Yang
 *
 * Represents the Empty subclass of TownCell
 */
public class Empty extends TownCell {
    /**
     * Constructor. Initiates super.
     *
     * @param p Town which the cell is in
     * @param r row
     * @param c column
     */
    public Empty(Town p, int r, int c) {
        super(p, r, c);
    }

    /**
     * @return Enum for Empty
     */
    @Override
    public State who() {
        return State.EMPTY;
    }

    /**
     * Implements Empty's + additional rules
     * @param tNew: town of the next cycle
     * @return Cell type next cycle
     */
    @Override
    public TownCell next(Town tNew) {
        int nCensus[] = new int[5];
        this.census(nCensus);
        TownCell output = new Casual(tNew, row, col);

        if(hasOneEmptyORoneOutage(nCensus)) {
            output = new Reseller(tNew, row, col);
        }

        return output;
    }
}
