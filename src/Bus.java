public class Bus
{
    private Location currentLoc;
    private final int capacity = 30;
    private int size;
    private Location dest;
    private double arrivalTime;
    private person[] seats = new person[capacity];

    public Bus()
    {
    }

    public void depart()
    {

    }

    public void arrive()
    {

    }

    public void setCurrentLoc(Location currentLoc)
    {
        this.currentLoc = currentLoc;
    }

    public Boolean isFull()
    {
        return size == capacity;
    }
}
