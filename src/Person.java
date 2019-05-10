/**
 * @author Atif Hassan
 */
public class Person
{
    private final int startLoc;
    private final int endLoc;
    private double startWait;
    private double endWait;

    /**
     * 
     * @param start
     * @param end
     */
    public Person(int start, int end)
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
    public int getEndLoc()
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
    public String toString()
    {
        return "Person [startLoc=" + startLoc + ", endLoc=" + endLoc + "\n startWait=" + startWait + ", endWait="
                + endWait + ", getWaitTime()=" + getWaitTime() + "]\n";
    }

}