import java.util.Vector;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Queue
{
    /**
     * 
     */
    private Vector<Event> all_data;

    /**
     * 
     */
    public Queue()
    {
        all_data = new Vector<Event>();
    }

    /**
     * 
     * @param e element to be added tail
     */
    public void enqueue(Event e)
    {
        all_data.addElement(e);
    }

    /**
     * 
     * @return 
     */
    public Event dequeue()
    {
        Event res = all_data.elementAt(0);
        all_data.removeElementAt(0);
        return res;
    }

    /**
     * 
     * @param i
     * @return
     */
    public Event Get(int i)
    {
        return all_data.elementAt(i);
    }
    
}