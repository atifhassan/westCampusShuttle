/**
 * copied from single server queue
 * 
 * @author atifm
 *
 */
public class Simulator
{
    /**
     * 
     */
    public Simulator()
    {
    }

    /**
     * 
     */
    public final static int arrival = 1;
    /**
     * 
     */
    public final static int departue = 2;
    /**
     * 
     */
    public double Clock, MeanInterArrivalTime, MeanServiceTime, LastEventTime, TotalBusy, SumResponseTime, SumWaitTime,
            wightedQueueLength;
    public long NumberOfCustomers, Queuelength, TotalCustomers, NumberInService, NumberOfDepartures, MaxQueueLength;
    public int counter, counters;
    public EventList FutureEventList;
    public Queue Customers;
    public Rand stream;

    /**
     * 
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
        // Create First Arrival Event
        Event evt = new Event(arrival, exponential(stream, MeanInterArrivalTime));
        FutureEventList.enqueue(evt);
    }

    /**
     * 
     * @param evt
     */
    public void ProcessArrival(Event evt)
    {
        Customers.enqueue(evt);
        wightedQueueLength += (Clock - LastEventTime) * Queuelength;
        Queuelength++;
        // if the server is idle, fetch the event, do statistics and put into service
        if (NumberInService == 0)
        {
            ScheduleDeparture();
        } else
            TotalBusy += (Clock - LastEventTime); // server is busy
        // adjust max Queue Length statistics
        if (MaxQueueLength < Queuelength)
            MaxQueueLength = Queuelength;
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
        Event depart = new Event(Simulator.departue, Clock + exponential(stream, MeanServiceTime));
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
        if (Queuelength > 0)
            ScheduleDeparture();
        else
            NumberInService = 0;
        // measure the response time and add to the sum
        double response = (Clock - finished.get_time());
        SumResponseTime += response;
        TotalBusy += (Clock - LastEventTime);
        NumberOfDepartures++;
        LastEventTime = Clock;
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
        if (R <= (b - a) / (c - a))
            x = a + Math.sqrt((b - a) * (c - a) * R);
        else
            x = c - Math.sqrt((c - b) * (c - a) * (1 - R));
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
