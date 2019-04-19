/**
 * 
 * @author Atif Hassan
 *
 */
public class Simulator
{

    /**
     * constant to define arrival of person at West Campus event number
     */
    public final static int arrivalWC = 1;
    /**
     * constant to define arrival of person at Rapidan River O event number
     */
    public final static int arrivalRR_O = 2;
    /**
     * constant to define arrival of person at Field House O event number
     */
    public final static int arrivalFH_O = 3;
    /**
     * constant to define arrival of person at RAC O event number
     */
    public final static int arrivalRAC_O = 4;
    /**
     * constant to define arrival of person at Mason Pond event number
     */
    public final static int arrivalMP = 5;
    /**
     * constant to define arrival of person at Presidents Park event number
     */
    public final static int arrivalPP = 6;
    /**
     * constant to define arrival of person at Masonvale event number
     */
    public final static int arrivalMV = 7;
    /**
     * constant to define arrival of person at Rappohannock event number
     */
    public final static int arrivalRAP = 8;
    /**
     * constant to define arrival of person at RAC I event number
     */
    public final static int arrivalRAC_I = 9;
    /**
     * constant to define arrival of person at RAC I event number
     */
    public final static int arrivalFH_I = 10;
    /**
     * constant to define arrival of person at RAC I event number
     */
    public final static int arrivalRR_I = 11;

    /**
     * constant to define bus 1 event number
     */
    public final static int busNext1 = 12;
    /**
     * constant to define bus 2 event number
     */
    public final static int busNext2 = 13;
    /**
     * constant to define bus 1 event number
     */
    public final static int busNext3 = 14;

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
    private Location[] route;

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
        // People = new Queue();
        stream = new Rand();
        Clock = 0.0;
        MeanInterArrivalTime = MIAT;
        MeanServiceTime = MST;
    }

    /**
     * Initialize all variables,stops, and route and start the queue
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
                new Stop("Rappohannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I"), };
        route = new Location[] { new Location("West Campus", 11), new Location("Rapidan River O", 10),
                new Location("Field House O", 9), new Location("RAC O", 8), new Location("Mason Pond O", 7),
                new Location("Presidents Park", 6), new Location("Masonvale", 5), new Location("Rappohannock", 4),
                new Location("RAC I", 3), new Location("Field House I", 2), new Location("Rapidan River I", 1),
                new Location("WestCampus", 0) };
        //Have to figure out how to initiate the future event queue

    }

    /**
     * 
     * @param evt
     */
    //NEEDS WORK
    public void ProcessArrival(Event evt)
    {
        // adds person to bus stop of the starting location
        stops[evt.getPerson().getStartLoc()].enqueue(evt.getPerson());
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        Queuelength++;
        // adjust max Queue Length statistics
        if(MaxQueueLength < Queuelength) MaxQueueLength = Queuelength;
        // Schedule the next arrival
        Event next_arrival = new Event(arrival, Clock + exponential(stream, MeanInterArrivalTime));
        FutureEventList.enqueue(next_arrival);
        LastEventTime = Clock;
    }

    /**
     * 
     */
    public void ScheduleDeparture()
    {
        // get the job at the head of the queue
        NumberOfCustomers++;
        Event depart = new Event(Simulator.departure, Clock + exponential(stream, MeanServiceTime));
        // Event depart= new Event(Simulator.departue,Clock+triangular(stream,1,3,8));
        double arrive = Customers.Get(0).get_time();
        double wait = Clock - arrive;
        SumWaitTime += wait;
        FutureEventList.enqueue(depart);
        NumberInService = 1;
        Queuelength--;
    }

    /**
     * 
     * @param e
     */
    public void ProcessDeparture(Event e)
    {
        // get the customers description
        Event finished = (Event) Customers.dequeue();
        // if there are customers in the queue then schedule the departure of the next
        // one
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        if(Queuelength > 0) ScheduleDeparture();
        else NumberInService = 0;
        // measure the response time and add to the sum
        double response = (Clock - finished.get_time());
        SumResponseTime += response;
        TotalBusy += (Clock - LastEventTime);
        NumberOfDepartures++;
        LastEventTime = Clock;
    }

    /**
     * Generates a endLocation for a person
     * 
     * @param start the starting location of the person
     * @return a location along the route
     */
    //NEEDS WORK
    private Location getEndLoc(int start)
    {
        int i = 0;
        return route[i];
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
