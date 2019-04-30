import java.util.PriorityQueue;

/**
 * @author Atif Hassan
 *
 */
public class Bus
{
    /**
     * current location of the bus
     */
    private Location loc;
    /**
     * 
     */
    private final int capacity = 24;
    /**
     * 
     */
    private int size;
    /**
     * 
     */
    private final char id;
    /**
     * 
     */
    private PriorityQueue<Person> seats = new PriorityQueue<>(capacity);

    /**
     * @param id
     * @param start
     */
    public Bus(char id, Location start)
    {
        this.id = id;
        loc = start;
    }

    /**
     * @return
     */
    public char getID()
    {
        return id;
    }

    /**
     * drops off passengers
     */
    public void arrive()
    {
        if(!seats.isEmpty())
        {
            while (seats.peek().getEndLoc().equals(loc))
            {
                seats.remove();
            }
        }
    }

    /**
     * @param s       the current stop
     * @param e       the triggering event
     * @param maxWait
     * @return
     */
    public double[] pickup(Stop s, Event e, double maxWait)
    {
        Person temp;
        double newMaxWait = maxWait;
        int waitTime = 0;
        while (!this.isFull() && !s.isEmpty())
        {
            try
            {
                temp = s.dequeue(e);
                seats.add(temp);
                size++;
                waitTime += temp.getWaitTime();
                if(newMaxWait < temp.getWaitTime())
                {
                    newMaxWait = temp.getWaitTime();
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return new double[] { waitTime, newMaxWait };
    }

    /**
     * 
     */
    public void step()
    {
        loc = loc.getNext();
    }

    /**
     *
     * @param currentLoc
     */
    /**
     * @param loc
     */
    public void setLoc(Location loc)
    {
        this.loc = loc;
    }

    /**
     *
     * @return
     */
    /**
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
    /**
     * @return
     */
    public Boolean isFull()
    {
        return size == capacity;
    }

    /**
     *
     */
    /**
     * @return
     */
    public int getSize()
    {
        return size;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return id + ": " + size;
    }
}
