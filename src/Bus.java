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
    public char getID()
    {
       return id;
    }
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
    public double[] pickup(Stop s,Event e, int maxWait)
    {
        Person temp;
        int newMaxWait = maxWait;
        int waitTime = 0;
        while (!this.isFull() && !s.isEmpty())
        {
            try
            {
                temp = s.dequeue(e);
                seats.add(temp);
                waitTime += temp.getWaitTime();
                if(newMaxWait < temp.getWaitTime())
                {
                  newMaxWait = temp.getWaitTime();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return new double[]{waitTime,newMaxWait};
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
    public int getSize()
    {
      return size;
    }
    public String toString()
    {
        return id + ": " + size;
    }
}
