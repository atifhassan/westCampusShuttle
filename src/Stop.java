import java.util.Vector;

/**
 * @author Atif Hassan
 *
 */
public class Stop
{
    private final String name;
    private Vector<Person> line = new Vector<>();

//    /**
//     *
//     * @param name
//     */
//    public Stop(String name)
//    {
//        this.name = name;
//    }

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
    public void enqueue(Person p, Event e)
    {
        p.setStartWait(e.get_time());
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
    public Person dequeue(Event e) throws Exception
    {
        if(line.isEmpty())
        {
            throw new Exception("Line is Empty");
        }

        Person temp = line.elementAt(0);
        line.removeElementAt(0);
        temp.setEndWait(e.get_time());
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
        return name + ": " + line.size();
    }

}
