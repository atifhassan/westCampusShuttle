/**
 * copied from single server queue
 * @author atifm
 *
 */
public class Event{
	private double time;
    private int type;
    public Event ( int _type , double _time ){
    	type = _type ;
        time = _time ;
    }

    public double get_time(){
    	return time ;
    }
    
    public int get_type (){
    	return type;
    }
}