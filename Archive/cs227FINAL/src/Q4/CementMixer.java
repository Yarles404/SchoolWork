package Q4;

public class CementMixer
{
    private static int MINUTES_TO_SET = 180;
    private static int POUR_RATE = 3;

    private int capacity;
    private int volume;
    private int minutesPassed;
    private boolean accelerant;

    public CementMixer(int capacity)
    {
        this.capacity = capacity;
        volume = 0;
    }

    public void clean()
    {
        volume = 0;
    }

    public int getVolume()
    {
        return volume;
    }

    public boolean isEmpty()
    {
        return volume == 0;
    }

    public void mix(int volume, boolean useAccelerant) throws IllegalStateException
    {
        if (volume != 0)
        {
            throw new IllegalStateException("Drum not empty");
        }
        if (volume > capacity)
        {
            throw new IllegalStateException("Insufficient capacity");
        }

        accelerant = useAccelerant;
        this.volume = volume;
    }

    public void pour(int volume) throws IllegalStateException
    {
        if (volume > this.volume)
        {
            throw new IllegalStateException("Insufficient volume");
        }
        if (this.getTimeRemaining() <= 0)
        {
            throw new IllegalStateException("Concrete is set");
        }

        while (!this.isSet() && volume > 0)
        {
            volume--;
            minutesPassed += POUR_RATE;
        }

        if (volume == 0)
        {
            minutesPassed = 0;
        }
    }

    public int getTimeRemaining()
    {
        return accelerant ? MINUTES_TO_SET / 2 - minutesPassed : MINUTES_TO_SET - minutesPassed;
    }

    public boolean isSet()
    {
        return this.getTimeRemaining() <= 0;
    }

    public void drive(int mph, int miles)
    {
        minutesPassed += 60 * (miles / mph);
    }
}
