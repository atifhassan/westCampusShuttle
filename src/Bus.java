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
    // private int index = 0;
    // private final int starting;
    // private Location dest;
    // private double arrivalTime;
    private PriorityQueue<Person> seats = new PriorityQueue<>(capacity);

    /**
     * private Location[] route = new Location[] { new Location("West Campus"), new Location("Rapidan River O"), new
     * Location("Field House O"), new Location("RAC O"), new Location("Mason Pond O"), new Location("Presidents Park"),
     * new Location("Masonvale"), new Location("Rappohannock"), new Location("RAC I"), new Location("Field House I"),
     * new Location("Rapidan River I"), };
     **/

    /**
     * initialize starting location and priorities
     */
    public Bus()
    {
        id = 0;
        // starting = 0;

        // currentLoc = route[index];
        /**
         * for (int i = 0; i < route.length; i++)// sets the priority for each stop { route[i].setPriotity(route.length
         * - i);// the earlier on the route you are the higher the priority }
         **/
    }

    /**
     *
     * @param id identification for bus
     */
    public Bus(char id)
    {
        this.id = id;
        // starting = 0;
        // currentLoc = route[index];
        /**
         * for (int i = 0; i < route.length; i++)// sets the priority for each stop { route[i].setPriotity(route.length
         * - i);// the earlier on the route you are the higher the priority }
         **/
    }

    public Bus(char id,Location start)
    {
        this.id = id;
        loc = start;
    }

    /**
     *
     * @param id       identification number for bus
     * @param starting give the bus a new staring stop
     */
    /**
     * public Bus(char id, int starting) { this.starting = starting; index = starting; currentLoc = route[index];
     * this.id = id; for (int i = 0; i < route.length; i++) { route[(i + starting) %
     * route.length].setPriotity(route.length - i); } }
     **/

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
