/**
 * copied from single server queue
 * 
 * @author atifm
 *
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
     * person associated with event
     */
    private Person person;

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
    
    public Event(int _type, double _time, Person _person)
    {
        type = _type;
        time = _time;
        person = _person;
        
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

    /**
     * 
     * @return associated person
     */
    public Person getPerson()
    {
        return person;
    }
    
}