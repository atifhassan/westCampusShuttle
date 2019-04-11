import java.util.Comparator;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Person implements Comparator<Person>
{
    private final Location startLoc;
    private final Location endLoc;
    private int waitTime;

    public Person(Location start, Location end)
    {
        startLoc = start;
        endLoc = end;
    }

    public Location getStartLoc()
    {
        return startLoc;
    }

    public Location getEndLoc()
    {
        return endLoc;
    }

    public int getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }

    @Override
    public int compare(Person p1, Person p2)
    {
        return p1.getEndLoc().compareTo(p2.getEndLoc());
    }

}