import java.util.Vector;

/**
 * copied from Queue.java of single server queue I'm editing the class to make
 * it into a Bus stop - Atif
 * 
 * @author atifm
 *
 */
public class Queue
{
    private Vector<Event> all_data;
    private Location name;

    public Queue(Location name)
    {
        this.name = name;
        all_data = new Vector<Event>();
    }
    
    public Queue()
    {
        all_data = new Vector<Event>();
    }

    public void enqueue(Event e)
    {
        all_data.addElement(e);
    }

    public Event dequeue()
    {
        Event res = all_data.elementAt(0);
        all_data.removeElementAt(0);
        return res;
    }

    public Event Get(int i)
    {
        return all_data.elementAt(i);
    }

    public String toString()
    {
        return "Stop" + name + ": " + all_data.size();
    }
}