package Q6;

import java.util.ArrayList;

public class AirTraffic {
    private ArrayList<Flying> items;

    public AirTraffic()
    {
        items = new ArrayList<Flying>();
    }

    public ArrayList<Flying> getSpeedInRange(int min, int max)
    {
        ArrayList<Flying> inRange = new ArrayList<>();

        for (Flying thing : items)
        {
            if(thing.getTopSpeed() >= min && thing.getTopSpeed() <= max)
            {
                inRange.add(thing);
            }
        }

        return inRange;
    }

    public void addItem(Flying m)
    {
        items.add(m);
    }

    public String getFastest()
    {
        if (items.size() == 0)
        {
            return null;
        }

        Flying fastest = items.get(0);
        for (Flying thing : items)
        {
            if (thing.getTopSpeed() > fastest.getTopSpeed())
            {
                fastest = thing;
            }
        }

        return fastest.name();
    }

    public int getSpeed(String name)
    {
        for (Flying thing : items)
        {
            if (thing.name().equals(name))
            {
                return thing.getTopSpeed();
            }
        }

        // no match case
        return -1;
    }
}
