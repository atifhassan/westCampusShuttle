/**
 * 
 * @author Atif Hassan
 *
 */
public class Location implements Comparable<Location>
{
    /**
     * 
     */
    private final String name;
    /**
     * how soon along the route is the location
     */
    private int priority;
    /**
     * next location in route
     */
    private Location next;

    /**
     * 
     * @param name
     */
    public Location(String name)
    {
        this.name = name;
    }

    /**
     * 
     * @param name
     * @param priority
     */
    public Location(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
    }

    /**
     * 
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @return
     */
    public int getPriotity()
    {
        return priority;
    }

    /**
     * 
     * @param priotity
     */
    public void setPriotity(int priotity)
    {
        this.priority = priotity;
    }
    
    /**
     * 
     * @return
     */
    public Location getNext()
    {
        return next;
    }

    /**
     * 
     * @param next
     */
    public void setNext(Location next)
    {
        this.next = next;
    }

    @Override
    /**
     * @param L
     */
    public int compareTo(Location L)
    {
        if ((L).getPriotity() > priority)
        {
            return 1;
        }
        if ((L).getPriotity() < priority)
        {
            return -1;
        }
        return 0;
    }

    @Override
    /**
     * @param o
     */
    public boolean equals(Object o)
    {
        return ((Location) o).getName().equals(name);
    }
}