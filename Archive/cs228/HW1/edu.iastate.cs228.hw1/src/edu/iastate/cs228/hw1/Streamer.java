package edu.iastate.cs228.hw1;

/**
 * @author Charles Yang
 *
 * Represents the Streamer subclass of TownCell
 */
public class Streamer extends TownCell {
    /**
     * Constructor. Initiates super.
     *
     * @param p Town which the cell is in
     * @param r row
     * @param c column
     */
    public Streamer(Town p, int r, int c) {
        super(p, r, c);
    }

    /**
     * @return Enum for Streamer
     */
    @Override
    public State who() {
        return State.STREAMER;
    }

    /**
     * Implements Streamer's + additional rules
     * @param tNew: town of the next cycle
     * @return Cell type next cycle
     */
    @Override
    public TownCell next(Town tNew) {
        int nCensus[] = new int[5];
        this.census(nCensus);
        TownCell output = new Streamer(tNew, row, col);

        if(hasOneEmptyORoneOutage(nCensus)) {
            output = new Reseller(tNew, row, col);
        }
        else if (nCensus[RESELLER] > 0) {
            output = new Outage(tNew, row, col);
        }
        else if (nCensus[OUTAGE] > 0) {
            output = new Empty(tNew, row, col);
        }

        return output;
    }
}
