import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Atif Hassan
 *
 */
public class Bus
{
    /**
     * the capacity of the Bus from data
     */
    private final int capacity = 24;
    /**
     * 
     */
    private final char id;
    /**
     * 
     */
    public PriorityQueue<Person> seats;

    /**
     * @param id
     */
    public Bus(char id)
    {
        seats = new PriorityQueue<>(capacity, new Comparator<Person>()
        {
            public int compare(Person p1, Person p2)
            {
                if(p1.getEndLoc() < p2.getEndLoc()) return -1;
                if(p1.getEndLoc() > p2.getEndLoc()) return 1;
                return 0;
            }
        });
        this.id = id;
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
    public void arrive(int location)
    {

        while (!seats.isEmpty() && seats.peek().getEndLoc() == location)
        {
            // System.out.println(seats.remove());
            seats.remove();
        }
    }

    /**
     * @param s       the current stop
     * @param e       the triggering event
     * @param maxWait
     * @return
     * @throws Exception
     */
    public double[] pickup(Stop s, Event e, double maxWait) throws Exception
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
                temp.getStartLoc();

                seats.add(temp);
                counter++;
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
        if(this.isFull())
        {
            throw new Exception("Bus is Full");
        }
        return new double[] { waitTime, newMaxWait, counter };
    }

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
        String ret = "Bus [capacity=" + capacity + ", id=" + id + ", seats= \n";
        for (Person i : seats)
        {
            ret += i.toString() + "\n";
        }
        ret += ", getSize()=" + getSize() + "]";
        return ret;
    }

}
