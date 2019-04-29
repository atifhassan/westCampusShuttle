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

    public double[] wightedQueueLength, totalBusy;
    public double Clock, MeanInterArrivalTime, MeanServiceTime, LastEventTime, SumResponseTime, SumWaitTime,
            maxWaitTime;
    public long[] maxQueueLength;
    public long passengerCount, Queuelength, passangerTotal, NumberInService, NumberOfDepartures;
    public int counter, counters;
    /**
     * List of events that will happen in the future
     *
     **/
    public EventList FutureEventList;
    /**
     * Queue of arrival events
     */
    // public Queue People;
    public Rand stream;
    private Stop[] stops;
    private ArrayList<Location> route;
    private Bus[] fleet;
    private double[] waitTime;
    private double[] maxWaitTime;
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
        totalBusy = new double[] { 0.0, 0.0, 0.0 };
        maxQueueLength = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        SumResponseTime = 0.0;
        NumberOfDepartures = 0;
        SumWaitTime = 0.0;
        wightedQueueLength = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        waitTime = new double[] {0,0,0,0,0,0,0,0,0,0,0}
        maxWaitTime = new double[] {0,0,0,0,0,0,0,0,0,0,0}
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
        double tClock = 0.0;// 7am Monday
        while (tClock < 7200)
        {
            if((tClock % 1440) > 420 && (tClock % 1440) < 1425)
            {
                // Schedule loop for west campus 1
                double tClock1 = tClock;
                // arrivals
                FutureEventList.enqueue(new Event(busArrive1, tClock1));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 1.268));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 7.183));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 10.986));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 12.254));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 14.367));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 16.057));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 16.902));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 19.015));
                FutureEventList.enqueue(new Event(busArrive1, tClock1 + 22.818));
                FutureEventList.enqueue(new Event(busArrive1, tClock1));
                // departures
                /*
                 * FutureEventList.enqueue(new Event(busDepart1, tClock1+1.268+.1)); FutureEventList.enqueue(new
                 * Event(busDepart1, tClock1+7.183+.1)); FutureEventList.enqueue(new Event(busDepart1,
                 * tClock1+10.986+.1)); FutureEventList.enqueue(new Event(busDepart1, tClock1+12.254+.1));
                 * FutureEventList.enqueue(new Event(busDepart1, tClock1+14.367+.1)); FutureEventList.enqueue(new
                 * Event(busDepart1, tClock1+16.057+.1)); FutureEventList.enqueue(new Event(busDepart1,
                 * tClock1+16.902+.1)); FutureEventList.enqueue(new Event(busDepart1, tClock1+19.015+.1));
                 * FutureEventList.enqueue(new Event(busDepart1, tClock1+22.818+.1));
                 */
                // Schedule loop for west campus 2
                double tClock2 = tClock + 15.0;
                // arrivals
                FutureEventList.enqueue(new Event(busArrive2, tClock2));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 1.268));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 7.183));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 10.986));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 12.254));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 14.367));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 16.057));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 16.902));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 19.015));
                FutureEventList.enqueue(new Event(busArrive2, tClock2 + 22.818));
                // departures
                /*
                 * FutureEventList.enqueue(new Event(busDepart2, tClock2+1.268+.1)); FutureEventList.enqueue(new
                 * Event(busDepart2, tClock2+7.183+.1)); FutureEventList.enqueue(new Event(busDepart2,
                 * tClock2+10.986+.1)); FutureEventList.enqueue(new Event(busDepart2, tClock2+12.254+.1));
                 * FutureEventList.enqueue(new Event(busDepart2, tClock2+14.367+.1)); FutureEventList.enqueue(new
                 * Event(busDepart2, tClock2+16.057+.1)); FutureEventList.enqueue(new Event(busDepart2,
                 * tClock2+16.902+.1)); FutureEventList.enqueue(new Event(busDepart2, tClock2+19.015+.1));
                 * FutureEventList.enqueue(new Event(busDepart2, tClock2+22.818+.1));
                 */
                // accounts for break for west campus 3
                if(!((tClock % 1440) > 645 && (tClock % 1440) < 900))
                {
                    // schedule loop for west campus 3
                    double tClock3 = tClock + 10.0;
                    FutureEventList.enqueue(new Event(busArrive3, tClock3));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 1.268));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 7.183));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 10.986));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 12.254));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 14.367));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 16.057));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 16.902));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 19.015));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 22.818));
                    FutureEventList.enqueue(new Event(busArrive3, tClock3 + 30));
                }
            }
            tClock += 30;
        }
    }

    /**
     * INCOMPLETE!!!
     *
     * @param evt
     */
    public void processArrival(Event evt)
    {
        int index = evt.getPerson().getStartLoc();
        // adds person to bus stop of the starting location
        try
        {
            stops[index].enqueue(evt.getPerson(), evt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // Updates wightedQueueLength statistic
        wightedQueueLength[index] += (Clock - LastEventTime) * stops[index].size();

        // adjust max Queue Length statistics
        if(maxQueueLength[index] < stops[index].size())
        {
            maxQueueLength[index] = stops[index].size();
        }
        // TODO
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
     public void processBus(Event e) throws Exception
     {
       //Identify which bus
       Bus temp = null;
       int timeDif = Clock - LastEventTime;
       switch (e.get_type())
       {
           case 2:
               temp = fleet[0];
               busUtilization += timeDif * temp.getSize();
               temp.arrive();
               if(Clock = busBreakTime1)
               {
                 if(temp.getSize() != 0)
                 {
                   throws new Exception("Bus" + temp.getID() + "not empty when on break")
                 }
                 return;
               }
               break;
           case 3:
               temp = fleet[1];
               busUtilization += timeDif * temp.getSize();
               temp.arrive();
               if(Clock = busBreakTime2)
               {
                 if(temp.getSize() != 0)
                 {
                   throws new Exception("Bus" + temp.getID() + "not empty when on break")
                 }
                 return;
               }
               break;
           case 4:
               temp = fleet[2];
               busUtilization += timeDif * temp.getSize();
               temp.arrive();
               if(Clock = busBreakTime3)
               {
                 if(temp.getSize() != 0)
                 {
                   throws new Exception("Bus" + temp.getID() + "not empty when on break")
                 }
                 return;
               }
               break;
           default:
               throw new Exception("wrong type of event");
               break;
       }
      //Update average bus capacity statistic
      //Appropriate passengers leave
      //Check if bus takes break - this might be complicated, will probably to have to cardcope time/bus pairings

      //Call bus pickup
      //create temp stop
      Stop s;
      int index = 0;
      //for loop cycles through stop array and compares bus' loc to figure out which stop it's at and stores it in temp stop variable
      for(int i = 0; i < 11; i++)
      {
        if(temp.getLoc().equals(stops[i]))
        {
          s = stops[i];
          index = i;
          break;
        }
      }
      //uses temp stop variable to call pickup
      double[] d = temp.pickup(s, e, maxWaitTime[index]);

      waitTime[index] += d[0];
      maxWaitTime[index] = d[1];
      //update stops max/average wait time statistic - might have to be done with pickup

      //Move bus to next stop
      temp.step();
      //May have to drop off remaining passengers here if going to be on break

    }
    public void ProcessBusArrive(Event e)
     *
     * @param e the event the be processed
     * @throws Exception the wrong type of event is passed
     */
    public void processBus(Event e) throws Exception
    {
        Bus temp = null;
        switch (e.get_type())
        {
            case 1:
                temp = fleet[0];
                break;
            case 2:
                temp = fleet[1];
                break;
            case 3:
                temp = fleet[2];
                break;
            default:
                throw new Exception("wrong type of event");
        }
        // drops off people
        temp.arrive();
        // TODO
        // process departure if not end of the day
        // and in the case of west campus 3, if it's not your break
        // end of my code
    }

    /**
     * INCOMPLETE!!!
     *
     * @param e the event to be processed
     * @throws Exception the wrong type of event is passed
     */
    public void busPickup(Event e) throws Exception
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
        }
        // picks up people
        temp.pickup(stops[route.indexOf(temp.getLoc())], e);
        // moves to next stop
        temp.step();
        // TODO
        // end of my code
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

    /**
     *
     * @param rng
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double triangular(Rand rng, double a, double b, double c)
    {
        double R = rng.next();
        double x;
        if(R <= (b - a) / (c - a)) x = a + Math.sqrt((b - a) * (c - a) * R);
        else x = c - Math.sqrt((c - b) * (c - a) * (1 - R));
        return x;
    }

    /**
     *
     * @param rng
     * @param a
     * @param b
     * @return
     */
    public static double uniform(Rand rng, double a, double b)
    {
        return a + (b - a) * rng.next();
    }

    /**
     *
     */
    public void ReportGeneration()
    {
        double[] RHO = new double[3];
        int index = 0;
        for (double i : RHO)
        {
            i = totalBusy[index++] / Clock;
        }
        double AverageWaittime = SumWaitTime / passengerCount;
        System.out.printf("\n  Bus Utilization                         %f\n", RHO);
        System.out.println("\n  Average Wait Time In Queue                 " + AverageWaittime);
        double AverageQueueLength[] = new double[11];
        index = 0;
        for (double i : AverageQueueLength)
        {
            i = wightedQueueLength[index] / Clock;

        }
        System.out.println("\n  Average Number Of Customers In Queue       " + AverageQueueLength);
        System.out.printf("\nAverage Maximum Length of Queues:\n");
        index= 0;
        for(double i : maxQueueLength)
        {
            System.out.printf("\t%s\t%f\n",stops[index++].getName(),i);
        }
    }
}
