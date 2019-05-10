/**
 * copied from blackboard
 */
public class Event
{
    /**
     * the time the event occurs
     */
    private double time;

    /**
     * the type of event
     */
    private int type;


    /**
     * 
     * @param _type the event type
     * @param _time the time the event occurs
     */
    public Event(int _type, double _time)
    {
        type = _type;
        time = _time;
    }

    /**
     * 
     * @return the event time
     */
    public double get_time()
    {
        return time;
    }

    /**
     * 
     * @return the event type
     */
    public int get_type()
    {
        return type;
    }

}