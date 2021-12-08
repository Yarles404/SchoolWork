package Q7;

abstract class Animal implements LivingThing
{
    private int lifeSpan;

    protected Animal(int lifeSpan)
    {
        this.lifeSpan = lifeSpan;
    }

    public int lifeSpan()
    {
        return lifeSpan;
    }
}

abstract class Bird extends Animal
{
    private double wingSpan;

    protected Bird(double wingSpan, int lifeSpan)
    {
        super(lifeSpan);
        this.wingSpan = wingSpan;
    }

    public double wingSpan()
    {
        return wingSpan;
    }
}

public class Elephant extends Animal
{
    private boolean fromAfrica;
    private static final int LIFE_SPAN = 80;

    public Elephant (boolean fromAfrica)
    {
        super(LIFE_SPAN);
        this.fromAfrica = fromAfrica;
    }

    public boolean isAfricanElephant()
    {
        return fromAfrica;
    }
}

public class Sparrow extends Bird
{
    private static final int LIFE_SPAN = 3;
    private static final double WING_SPAN = 0.4; //feet
    public Sparrow() {
        super(WING_SPAN, LIFE_SPAN);
    }
}

public class Eagle extends Bird
{
    private boolean endangered;
    public Eagle(int lifeSpan, double wingSpan, boolean endangered) {
        super(wingSpan, lifeSpan);
        this.endangered = endangered;
    }

    public boolean isEndangered() {
        return endangered;
    }
}