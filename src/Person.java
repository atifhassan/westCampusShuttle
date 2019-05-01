import java.util.Comparator;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Person implements Comparator<Person>, Comparable<Person>
{
    private final int startLoc;
    private final Location endLoc;
    private double startWait;
    private double endWait;

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
     * @return
     */
    public double getStartWait()
    {
        return startWait;
    }

    /**
     * 
     * @param startWait
     */
    public void setStartWait(double startWait)
    {
        this.startWait = startWait;
    }

    /**
     * 
     * @return
     */
    public double getEndWait()
    {
        return endWait;
    }

    /**
     * 
     * @param endWait
     */
    public void setEndWait(double endWait)
    {
        this.endWait = endWait;
    }

    /**
     * 
     * @return the wait time for student
     */
    public double getWaitTime()
    {
        return endWait - startWait;
    }

    @Override
    /**
     * compares based on priority of ending location
     * 
     * @param p1
     * @param p2
     * @return -1 if p1<p2, 0 is p1=p2, +1 p1>p2
     */
    public int compare(Person p1, Person p2)
    {
        return p1.getEndLoc().compareTo(p2.getEndLoc());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Person p0)
    {
        return this.getEndLoc().compareTo(p0.getEndLoc());
    }

    @Override
    public String toString()
    {
        return "Person [startLoc=" + startLoc + ", endLoc=" + endLoc + ", startWait=" + startWait + ", endWait="
                + endWait + "]";
    }
    
    
}