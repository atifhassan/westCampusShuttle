import java.util.PriorityQueue;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Bus
{
    /**
     * current location of the bus
     */
    private Location loc;
    private final int capacity = 24;
    private int size;
    private final char id;
    private PriorityQueue<Person> seats = new PriorityQueue<>(capacity);

    /**
     * initialize starting location and priorities
     */
    public Bus()
    {
        id = 0;
    }

    /**
     * 
     * @param id identification for bus
     */
    public Bus(char id)
    {
        this.id = id;
    }
    
    /**
     * 
     * @param id
     * @param start
     */
    public Bus(char id,Location start)
    {
        this.id = id;
        loc = start;
    }

    /**
     * 
     * @param s pass in the information of the current stop the bus is at
     */
    public void arrive()
    {
        while (seats.peek().getEndLoc().equals(loc))
        {
            seats.remove();
        }
    }

    /**
     * 
     * @param s the current stop
     * @param e the triggering event
     */
    public void pickup(Stop s,Event e)
    {
        while (!this.isFull() && !s.isEmpty())
        {
            try
            {
                seats.add(s.dequeue(e));
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public void step()
    {
        loc = loc.getNext();
    }
    
    /**
     * 
     * @param currentLoc
     */
    public void setLoc(Location loc)
    {
        this.loc = loc;
    }
    
    /**
     * 
     * @return
     */
    public Location getLoc()
    {
        return loc;
    }

    /**
     * 
     * @return
     */
    public Boolean isFull()
    {
        return size == capacity;
    }

    /**
     * 
     */
    public String toString()
    {
        return id + ": " + size;
    }
}
