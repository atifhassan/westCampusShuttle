/**
 * 
 * @author Atif Hassan
 *
 */
public class Location implements Comparable<Location>
{
    private final String name;
    private int priority;
    
    public Location(String name) {
        this.name = name;
    }
    
    public Location(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }
    public int getPriotity()
    {
        return priority;
    }

    public void setPriotity(int priotity)
    {
        this.priority = priotity;
    }

    @Override
    public int compareTo(Location L)
    {
        if((L).getPriotity()>priority)
{
    return 1;
}
        if((L).getPriotity()<priority)
        {
            return -1;
        } 
        return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        return ((Location)o).getName().equals(name);
    }
}