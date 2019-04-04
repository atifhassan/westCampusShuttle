
//Comparable will not work for multiple busses running at a time
//either have to create multiple sets of Location for each bus or find another method of sorting passengers on the bus
public class Location implements Comparable<Location>
{
    private final String name;
    private int priotity;
    
    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getPriotity()
    {
        return priotity;
    }

    public void setPriotity(int priotity)
    {
        this.priotity = priotity;
    }

    @Override
    public int compareTo(Location L)
    {
        if((L).getPriotity()>priotity)
{
    return 1;
}
        if((L).getPriotity()<priotity)
        {
            return -1;
        } 
        return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        return ((Location)o).getName().equals(name);
    }
    
 /**   
  WESTCAMPUS;
  RAPIDANRIVER;
  FIELDHOUSE;
  THERAC;
  RAPPAHANOCKRIVERLN;
  MASONVALE;
  PRESIDENTSPARK;
  MASONPOND;
  **/
}