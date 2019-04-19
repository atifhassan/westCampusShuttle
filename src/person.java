import java.util.Comparator;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Person implements Comparator<Person>
{
    private final int startLoc;
    private final Location endLoc;
    private int waitTime;

    /**
     * 
     * @param start
     * @param end
     */
    public Person(int start, Location end)
    {
        startLoc = start;
        endLoc = end;
    }

    /**
     * 
     * @return the starting Location
     */
    public int getStartLoc()
    {
        return startLoc;
    }

    /**
     * 
     */
    public Location getEndLoc()
    {
        return endLoc;
    }

    /**
     * 
     * @return the weight time for student
     */
    
    public int getWaitTime()
    {
        return waitTime;
    }
    
    /**
     * 
     * @param waitTime
     */
    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }

    @Override
    /**
     * compares based on priority of ending location
     * @param p1
     * @param p2
     * @return -1 if p1<p2, 0 is p1=p2, +1 p1>p2
     */
    public int compare(Person p1, Person p2)
    {
        return p1.getEndLoc().compareTo(p2.getEndLoc());
    }

}