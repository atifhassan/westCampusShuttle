import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
     * the total number of riders in the system
     */
    public int[] riderCounter;
    /**
     * the total number of times the pickup event is called
     */
    public int[] pickupCounter;
    /**
     *
     */
    public double[] accumulatedQueueLength;
    /**
     * 
     */
    public double[] accumulatedWaitTime;
    /**
     * 
     */
    public double[] maxWaitTime;
    /**
     * 
     */
    public double[] accumulatedBusUtilization;
    /**
     * the end of day for each bus
     */
    public final double[] busBreakTime = { 1050, 1065, 1060 };
    /**
     *
     */
    public double Clock;
    /**
     * 
     */
    public double MeanInterArrivalTime;
    /**
     * 
     */
    public double LastEventTime;
    /**
     * 
     */
    public double SumResponseTime;
    /**
     * 
     */
    public double SumWaitTime;
    /**
     *
     */
    public long[] maxQueueLength;
    /**
     *
     */
    public long passengerCount;
    /**
     * 
     */
    public long Queuelength;
    /**
     * 
     */
    public long passangerTotal;
    /**
     * 
     */
    public long NumberInService;
    /**
     * 
     */
    public long NumberOfDepartures;

    /**
     * List of events that will happen in the future
     */
    public EventList FutureEventList;
    /**
     *
     */
    public Rand stream;
    /**
     * the array of bus stop queues
     */
    private Stop[] stops;
    /**
     *
     */
    private ArrayList<Location> route;
    /**
     *
     */
    private Bus[] fleet;

    /**
     *
     * @param MIAT Mean Interarrival Time
     */
    public Simulator(double MIAT)
    {
        FutureEventList = new EventList();
        stream = new Rand();
        Clock = 360.0;// 6am Monday
        MeanInterArrivalTime = MIAT;
        this.initialize();
    }

    /**
     * Initialize all variables,stops, and route and start the queue
     */
    private void initialize()
    {
        LastEventTime = 0.0;
        accumulatedBusUtilization = new double[] { 0.0, 0.0, 0.0 };
        pickupCounter = new int[] { 0, 0, 0 };
        maxQueueLength = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        riderCounter = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        accumulatedQueueLength = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        accumulatedWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        maxWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
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
            route.get(i).setNext(route.get((i + 1) % 11));
        }

        // creates the buses and starts them at west campus
        fleet = new Bus[] { new Bus('1', route.get(0)), new Bus('2', route.get(0)), new Bus('3', route.get(0)) };

        // schedule first arrival
        double time = uniform(stream, 1, 10);
        Event first_arrival = new Event(arrival, Clock + time, new Person(genStartLoc(time), genEndLoc(0)));
        FutureEventList.enqueue(first_arrival);

        // schedule all buses
        double tClock = 0.0;// temp clock 7am Monday
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
/*
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

                // accounts for break for west campus 3
                if(!((tClock % 1440) >= 645 && (tClock % 1440) < 900))
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
            */
            tClock += 30;
        }
    }

    /**
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
            System.out.print(stops);
            e.printStackTrace();
        }
        // Updates accumulatedQueueLength statistic
        accumulatedQueueLength[index] += (Clock - LastEventTime) * stops[index].getSize();

        // adjust max Queue Length statistics
        if(maxQueueLength[index] < stops[index].getSize())
        {
            maxQueueLength[index] = stops[index].getSize();
        }

        // Create next arrival
        double time = uniform(stream, 5, 10);
        Event next_arrival = null;
        if(Clock%1440>=15&&Clock%1440<360)
        {
            Clock= (Clock-Clock%1440)+360;
        }
        next_arrival = new Event(arrival, Clock + time, new Person(genStartLoc(time), genEndLoc(0)));
        FutureEventList.enqueue(next_arrival);
        LastEventTime = evt.get_time();
    }

    /**
     * @param e
     * @throws Exception
     */
    public void processBus(Event e) throws Exception
    {
        Bus temp = null;
        int b = -1;
        double timeDif = Clock - LastEventTime;
        switch (e.get_type())
        {
            case 1:
                b = 0;
                temp = fleet[b];
                accumulatedBusUtilization[b] += timeDif * temp.getSize();
                temp.arrive();
                if(Clock % 1440 >= busBreakTime[b] && temp.getLoc().equals(route.get(0)))
                {
                    // checks if bus is empty
                    if(temp.getSize() != 0)
                    {
                        throw new Exception("Bus" + temp.getID() + "not empty when on break");
                    }
                    return;
                }
                pickupCounter[b]++;
                this.busPickup(e, temp);
                break;
            case 2:
                b = 1;
                temp = fleet[b];
                accumulatedBusUtilization[b] += timeDif * temp.getSize();
                temp.arrive();
                if(Clock % 1440 >= busBreakTime[b] && temp.getLoc().equals(route.get(0)))
                {
                    if(temp.getSize() != 0)
                    {
                        throw new Exception("Bus" + temp.getID() + "not empty when on break");
                    }
                    return;
                }
                pickupCounter[b]++;
                this.busPickup(e, temp);
                break;
            case 3:
                b = 2;
                temp = fleet[b];
                accumulatedBusUtilization[b] += timeDif * temp.getSize();
                temp.arrive();
                if((Clock % 1440 >= busBreakTime[b] || ((Clock % 1440) >= 645 && (Clock % 1440) < 900))
                        && temp.getLoc().equals(route.get(0)))
                {
                    if(temp.getSize() != 0)
                    {
                        throw new Exception("Bus" + temp.getID() + "not empty when on break");
                    }
                    return;
                }
                pickupCounter[b]++;
                this.busPickup(e, temp);
                break;
            default:
                throw new Exception("wrong type of event");
        }
        LastEventTime = e.get_time();
    }

    /**
     * @param e   the event to be processed
     * @param bus the bus being used
     * @param b   the index of bus
     * @throws Exception the wrong type of event is passed
     */
    private void busPickup(Event e, Bus bus) throws Exception
    {
        // Call bus pickup
        int index = route.indexOf(bus.getLoc());
        // uses temporary stop variable to call pickup
        double[] d = bus.pickup(stops[index], e, maxWaitTime[index]);

        // update stops max/average wait time statistic - might have to be done with pickup
        accumulatedWaitTime[index] += d[0];
        maxWaitTime[index] = d[1];
        riderCounter[index] += d[2];

        // Move bus to next stop
        bus.step();
        // May have to drop off remaining passengers here if going to be on break
    }

    /**
     * Randomly generates a starting location for each person
     *
     * @param time the current time
     * @return the starting location
     */
    private int genStartLoc(double time)
    {
        /*
         * double temp = Math.random(); double wcP, rroP, fhoP, racoP, mpoP, ppP, mvP, rapP, raciP, fhiP, rriP; wcP =
         * -0.15 * Math.log(time) + 0.8423; rroP = -0.15 * Math.log(time) + 0.8423; fhoP = -0.15 * Math.log(time) +
         * 0.8423; racoP = -0.15 * Math.log(time) + 0.8423; mpoP = -0.15 * Math.log(time) + 0.8423; ppP = -0.15 *
         * Math.log(time) + 0.8423; mvP = -0.15 * Math.log(time) + 0.8423; rapP = -0.15 * Math.log(time) + 0.8423; raciP
         * = -0.15 * Math.log(time) + 0.8423; fhiP = -0.15 * Math.log(time) + 0.8423; rriP = -0.15 * Math.log(time) +
         * 0.8423;
         */
        return (int) (Math.random() * 11);
    }

    /**
     * @param start the starting location of the person
     * @return a location along the route
     */
    private Location genEndLoc(int start)
    {
        return route.get((int) ((Math.random() * (11 - start) + start)));
    }

    /**
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
     * @param rng  random number stream
     * @param mean
     * @param std  standard deviation
     * @return
     */
    public static double normal(Rand rng, double mean, double std)
    {
        return (1 / (std * Math.sqrt(2 * Math.PI))
                * Math.pow(Math.E, -Math.pow(rng.next() - mean, 2.0) / (2 * Math.pow(std, 2.0))));
    }

    /**
     *
     */
    public void ReportGeneration()
    {
        try
        {
            PrintWriter out = new PrintWriter("output.txt");
            int index = 0;
            out.printf("REPORT:\n");
            out.printf("------------------\n");
            out.printf("Run Time: " + Clock);
            out.printf("\n>BUS STATISTICS:\n");
            ////
            for (double i : accumulatedBusUtilization)
            {
                out.printf("\tWest Campus %c:\t\t%f\n", fleet[index].getID(), i);
                index++;
            }
            ////
            double[] averageUtil = new double[3];
            for (int i = 0; i < averageUtil.length; i++)
            {
                averageUtil[i] = accumulatedBusUtilization[i] / Clock;
            }
            out.printf("\n>>Utilization\n");
            index = 0;
            for (double i : averageUtil)
            {
                out.printf("\tWest Campus %d:\t\t%f\n", 1 + index, i);
                index++;
            }

            out.printf("\n>STOP STATISTICS\n");
            ////
            double AverageQueueLength[] = new double[11];
            for (int i = 0; i < AverageQueueLength.length; i++)
            {
                AverageQueueLength[i] = accumulatedQueueLength[i] / Clock;
            }
            out.printf("\n>>Average Number Of People In Queue:\n");
            index = 0;
            for (double i : AverageQueueLength)
            {
                out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(), i);
                index++;
            }

            ////
            out.printf("\n>>Maximum Length of Queues:\n");
            index = 0;
            for (double i : maxQueueLength)
            {
                out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(), i);
                index++;
            }

            ////
            out.printf("\n>>Average Wait Time People In Queue:\n");
            index = 0;
            for (double i : accumulatedWaitTime)
            {
                out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), i / riderCounter[index]);
                index++;
            }

            ////
            out.printf("\n>>Max Wait Time People In Queue:\n");
            index = 0;
            for (double i : maxWaitTime)
            {
                out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), i);
                index++;
            }

            /////
            out.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
