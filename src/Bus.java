import java.util.PriorityQueue;

public class Bus
{
    private Location currentLoc;
    private final int capacity = 30;
    private int size;
    private Location dest;
    private double arrivalTime;
    private PriorityQueue<Person> seats = new PriorityQueue<>(capacity);
    public Bus()
    {
    }

    public void arrive()
    {
        while(seats.peek().getEndLoc().equals(currentLoc))
        {
            seats.remove();
        }
        if(!this.isFull())
        {
            seats.add();
        }
        
    }
    
    public void continueRoute(Location next)
    {
        currentLoc = next;
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
