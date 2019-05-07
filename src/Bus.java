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
    //private Location loc;
    /**
     * 
     */
    private final int capacity = 24;
    /**
     * 
     */
    private final char id;
    /**
     * 
     */
    public PriorityQueue<Person> seats = new PriorityQueue<>(capacity);

    /**
     * @param id
     */
    public Bus(char id)
    {
        super();
        this.id = id;
    }

//    /**
//     * @param id
//     * @param start
//     */
//    public Bus(char id, Location start)
//    {
//        this.id = id;
//        loc = start;
//    }

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
    public void arrive(int location)
    {

            while (!seats.isEmpty() && seats.peek().getEndLoc()==location)
            {
                //System.out.println(seats.remove());
                seats.remove();
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
        int counter = 0;
        int waitTime = 0;
        while (!this.isFull() && !s.isEmpty())
        {
            try
            {
                temp = s.dequeue(e);
                seats.add(temp);
                counter++;
                waitTime += temp.getWaitTime();
                if(newMaxWait < temp.getWaitTime())
                {
                    //System.out.println(temp);
                    newMaxWait = temp.getWaitTime();
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return new double[] { waitTime, newMaxWait, counter };
    }

//    /**
//     * 
//     */
//    public void step()
//    {
//        loc = loc.getNext();
//    }
//
//    /**
//     * @param currentLoc
//     */
//    public void setLoc(Location loc)
//    {
//        this.loc = loc;
//    }
//
//    /**
//     * @return
//     */
//    public Location getLoc()
//    {
//        return loc;
//    }

    /**
     * @return
     */
    public Boolean isFull()
    {
        return seats.size() == capacity;
    }


    /**
     * @return
     */
    public int getSize()
    {
        return seats.size();
    }

    @Override
    public String toString()
    {
        return "Bus [capacity=" + capacity + ", id=" + id + ", seats=" + seats.toArray()
                + ", getSize()=" + getSize() + "]";
    }
    
    
}
