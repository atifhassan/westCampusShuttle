import java.util.Vector;

/**
 * @author Atif Hassan
 *
 */
public class Stop
{
    private final String name;
    private Vector<Person> line = new Vector<>();

    /**
     * @param name
     */
    public Stop(String name)
    {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty()
    {
        return line.isEmpty();
    }

    /**
     *
     * @return
     */
    public int getSize()
    {
        return line.size();
    }

    /**
     *
     * @param p
     * @param e
     */
    public void enqueue(Person p, double clock)
    {
        p.setStartWait(clock);
        line.addElement(p);
    }

    /**
     * @return the value of the first element with out removing
     */
    public Person peek()
    {
        return line.firstElement();
    }

    /**
     *
     * @param e the triggering bus arrival event
     * @return the person at the head of the line
     * @throws Exception if line is empty
     */
    public Person dequeue(double clock) throws Exception
    {
        if(line.isEmpty())
        {
            throw new Exception("Line is Empty");
        }

        Person temp = line.elementAt(0);
        line.removeElementAt(0);
        temp.setEndWait(clock);
        return temp;
    }

    /**
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     */
    public String toString()
    {
        return name + ": " + line.size() + " " + line.toString();
    }
}
