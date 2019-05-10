import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * @author Atif Hassan
 */
public class EventList
{

    public PriorityQueue<Event> event_list;

    /**
     * creates a PQ and defines a comparator
     */
    public EventList()
    {
        event_list = new PriorityQueue<Event>(100, new Comparator<Event>()
        {
            public int compare(Event e1, Event e2)
            {
                if(e1.get_time() < e2.get_time()) return -1;
                if(e1.get_time() > e2.get_time()) return 1;
                return 0;
            }
        });
    }

    /**
     * 
     * @return size of list
     */
    public int size()
    {
        return event_list.size();
    }

    /**
     * 
     * @return gets the next in list
     */
    public Event getMin()
    {
        return event_list.peek();
    }

    /**
     * removes event from the head of the list
     */
    public void dequeue()
    {
        event_list.remove();
    }

    /**
     * adds item to list
     * 
     * @param e item to be added to list
     */
    public void enqueue(Event e)
    {
        event_list.add(e);
    }
}
