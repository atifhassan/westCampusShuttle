import java.util.ArrayList;

/**
 * 
 * @author Atif Hassan
 *
 */
public class Simulator
{

    /**
     * constant to define arrival of person at bus stop
     */
    public final static int arrival = 0;
    /**
     * constant to define bus 1 event number
     */
    public final static int busArrive1 = 1;
    /**
     * constant to define bus 2 event number
     */
    public final static int busArrive2 = 2;
    /**
     * constant to define bus 1 event number
     */
    public final static int busArrive3 = 3;
    /**
     * constant to define bus 1 event number
     */
    public final static int busDepart1 = 10;
    /**
     * constant to define bus 2 event number
     */
    public final static int busDepart2 = 20;
    /**
     * constant to define bus 1 event number
     */
    public final static int busDepart3 = 30;

    public double Clock, MeanInterArrivalTime, MeanServiceTime, LastEventTime, TotalBusy, SumResponseTime, SumWaitTime,
            wightedQueueLength;
    public long NumberOfCustomers, Queuelength, TotalCustomers, NumberInService, NumberOfDepartures, MaxQueueLength;
    public int counter, counters;
    /**
     * List of events that will happen in the future
     */
    public EventList FutureEventList;
    /**
     * Queue of arrival events
     */
    // public Queue People;
    public Rand stream;
    private Stop[] stops;
    private ArrayList<Location> route;
    private Bus[] fleet;

    /**
     * 
     */
    public Simulator()
    {
    }

    /**
     * 
     * @param MIAT Mean Interarrival Time
     * @param MST  Mean Service Time
     */
    public Simulator(double MIAT, double MST)
    {
        FutureEventList = new EventList();
        stream = new Rand();
        Clock = 0.0;
        MeanInterArrivalTime = MIAT;
        MeanServiceTime = MST;
    }

    /**
     * INCOMPLETE!!! Initialize all variables,stops, and route and start the queue
     */
    public void Initialization()
    {
        Clock = 0.0;
        NumberInService = 0;
        Queuelength = 0;
        LastEventTime = 0.0;
        TotalBusy = 0.0;
        MaxQueueLength = 0;
        SumResponseTime = 0.0;
        NumberOfDepartures = 0;
        SumWaitTime = 0.0;
        wightedQueueLength = 0.0;
        stops = new Stop[] { new Stop("West Campus"), new Stop("Rapidan River O"), new Stop("Field House O"),
                new Stop("RAC O"), new Stop("Mason Pond O"), new Stop("Presidents Park"), new Stop("Masonvale"),
                new Stop("Rappohannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I") };
        route = new ArrayList<>(11);
        route.add(new Location("West Campus", 0));
        route.add(new Location("Rapidan River O", 10));
        route.add(new Location("Field House O", 9));
        route.add(new Location("RAC O", 8));
        route.add(new Location("Mason Pond O", 7));
        route.add(new Location("Presidents Park", 6));
        route.add(new Location("Masonvale", 5));
        route.add(new Location("Rappohannock", 4));
        route.add(new Location("RAC I", 3));
        route.add(new Location("Field House I", 2));
        route.add(new Location("Rapidan River I", 1));
        // sets the next location along the route
        for (int i = 0; i < route.size(); i++)
        {
            route.get(i).setNext(route.get((i++) % 11));
        }
        fleet = new Bus[] { new Bus('1', route.get(0)), new Bus('2', route.get(0)), new Bus('3', route.get(0)) };
        // schedule first arrival
        Event first_arrival = new Event(arrival, Clock + exponential(stream, MeanInterArrivalTime),
                new Person(genStartLoc(), genEndLoc(0)));
        FutureEventList.enqueue(first_arrival);
        // schedule all buses
        // TODO
    }

    /**
     * INCOMPLETE!!!
     * 
     * @param evt
     */
    public void ProcessArrival(Event evt)
    {
        // adds person to bus stop of the starting location
        try
        {
            stops[evt.getPerson().getStartLoc()].enqueue(evt.getPerson(), evt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // TODO
        // end of my code
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        Queuelength++;
        // if the server is idle, fetch the event, do statistics and put into service
        if(NumberInService == 0)
        {
            //ScheduleDeparture();
        }
        else TotalBusy += (Clock - LastEventTime); // server is busy
        // adjust max Queue Length statistics
        if(MaxQueueLength < Queuelength) MaxQueueLength = Queuelength;
        // Schedule the next arrival
        Event next_arrival = new Event(arrival, Clock + exponential(stream, MeanInterArrivalTime));
        FutureEventList.enqueue(next_arrival);
        LastEventTime = Clock;
    }

    /**
     * Removed: No longer needed
     */
    /**
     * public void ScheduleDeparture() { // get the job at the head of the queue NumberOfCustomers++; Event depart = new
     * Event(Simulator.departure, Clock + exponential(stream, MeanServiceTime)); // Event depart= new
     * Event(Simulator.departue,Clock+triangular(stream,1,3,8)); double arrive = Customers.Get(0).get_time(); double
     * wait = Clock - arrive; SumWaitTime += wait; FutureEventList.enqueue(depart); NumberInService = 1; Queuelength--;
     * }
     **/

    /**
     * INCOMPLETE!!! Was ScheduleDeparture
     * 
     * @param e
     */
    public void ProcessBusArrive(Event e)
    {
        Bus temp = null;
        switch (e.get_type())
        {
            case 2:
                temp = fleet[0];
                break;
            case 3:
                temp = fleet[1];
                break;
            case 4:
                temp = fleet[2];
                break;
            default:
                throw new Exception("wrong type of event");
                break;
        }
        // drops off people
        temp.arrive();
        // TODO
        // end of my code
        // get the customers description
        Event finished = (Event) Customers.dequeue();
        // if there are customers in the queue then schedule the departure of the next one
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        if(Queuelength > 0) ScheduleDeparture();
        else NumberInService--;
        // measure the response time and add to the sum
        double response = (Clock - finished.get_time());
        SumResponseTime += response;
        TotalBusy += (Clock - LastEventTime);
        NumberOfDepartures++;
        LastEventTime = Clock;
    }

    /**
     * INCOMPLETE!!!
     * 
     * @param e
     */
    public void ProcessBusDepart(Event e)
    {
        Bus temp = null;
        switch (e.get_type())
        {
            case 10:
                temp = fleet[0];
                break;
            case 20:
                temp = fleet[1];
                break;
            case 30:
                temp = fleet[2];
                break;
            default:
                throw new Exception("wrong type of event");
                break;
        }
        // picks up people
        temp.pickup(stops[route.indexOf(temp.getLoc())], e);
        // moves to next stop
        temp.step();
        // TODO
        // end of my code
        // get the customers description
        Event finished = (Event) Customers.dequeue();
        // if there are customers in the queue then schedule the departure of the next one
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        if(Queuelength > 0) ScheduleDeparture();
        else NumberInService--;
        // measure the response time and add to the sum
        double response = (Clock - finished.get_time());
        SumResponseTime += response;
        TotalBusy += (Clock - LastEventTime);
        NumberOfDepartures++;
        LastEventTime = Clock;
    }

    /**
     * INCOMPLETE!!! Randomly generates a starting location for each person
     * 
     * @return the starting location
     */
    // TODO
    private int genStartLoc()
    {

        return (int) (Math.random() * 12);
    }

    /**
     * INCOMPLETE!!! Generates a endLocation for a person
     * 
     * @param start the starting location of the person
     * @return a location along the route
     */
    // TODO
    private Location genEndLoc(int start)
    {
        return route.get((int) ((Math.random() * (12 - start) + start)));
    }

    /**
     * 
     * @param rng
     * @param mean
     * @return
     */
    public static double exponential(Rand rng, double mean)
    {
        return -mean * Math.log(rng.next());
    }

    public static double triangular(Rand rng, double a, double b, double c)
    {
        double R = rng.next();
        double x;
        if(R <= (b - a) / (c - a)) x = a + Math.sqrt((b - a) * (c - a) * R);
        else x = c - Math.sqrt((c - b) * (c - a) * (1 - R));
        return x;
    }

    public void ReportGeneration()
    {
        double RHO = TotalBusy / Clock;
        System.out.println("\n  Server Utilization                         " + RHO);
        double AverageWaittime = SumWaitTime / NumberOfCustomers;
        System.out.println("\n  Average Wait Time In Queue                 " + AverageWaittime);
        double AverageQueueLength = wightedQueueLength / Clock;
        System.out.println("\n  Average Number Of Customers In Queue       " + AverageQueueLength);
        System.out.println("\n  Maximum Number Of Customers In Queue       " + MaxQueueLength);
    }
}
