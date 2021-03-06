import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author Atif Hassan
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
    private final double minArrivalTime;
    private final double meanArrivalTime;
    private final double maxArrivalTime;
    private final double[] busBreakTime = { 1410, 1425, 1420 };

    /**
     * State Variables
     */
    public int[] riderCounter; // the total number of riders in the system per stop
    private int[] pickupCounter; // the total number of times the pickup event is called per bus
    private double[] maxWaitTime; // the longest a person has had to wait for a bus per stop
    private long[] maxQueueLength; // the longest the line got at each stop

    /**
     * Statistics
     */
    private double[] accumulatedQueueLength; // the queue length over the run of the simulation per stop
    private double[] accumulatedWaitTime; // the wait time over the run of the simulation per stop
    private double[] accumulatedBusUtilization; // how full the bus is per minute, per bus

    private double LastEventTime;
    private double[] busClock;

    private double Clock;
    public EventList FutureEventList; // List of events that will happen in the future
    private Rand stream;
    private Stop[] stops; // the array of bus stop queues
    private int busLocationPointer[]; // keeps track of the location of each bus
    private Bus[] fleet; // stores all bus objects

    /**
     * @param mean Mean Inter-arrival Time
     * @param std  standard deviation
     */
    public Simulator(double min, double mean, double max, double seed)
    {
        FutureEventList = new EventList();
        stream = new Rand(seed);
        Clock = 405.0;// 6am Monday
        minArrivalTime = min;
        meanArrivalTime = mean;
        maxArrivalTime = max;
        this.initialize();
    }

    /**
     * Initialize all variables,stops, and route and start the queue
     */
    private void initialize()
    {
        stops = new Stop[] { new Stop("West Campus"), new Stop("Rapidan River O"), new Stop("Field House O"),
                new Stop("RAC O"), new Stop("Mason Pond O"), new Stop("Presidents Park"), new Stop("Masonvale"),
                new Stop("Rappahannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I") };
        LastEventTime = 0.0;
        accumulatedBusUtilization = new double[] { 0.0, 0.0, 0.0 };
        pickupCounter = new int[] { 0, 0, 0 };
        maxQueueLength = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        riderCounter = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        accumulatedQueueLength = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        accumulatedWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        maxWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        busLocationPointer = new int[] { 0, 0, 0 };

        // creates the buses and starts them at west campus
        fleet = new Bus[] { new Bus('1'), new Bus('2'), new Bus('3') };

        // schedule first arrival
        double time = triangular(stream, minArrivalTime, meanArrivalTime, maxArrivalTime);
        Event first_arrival = new Event(arrival, Clock + time);
        FutureEventList.enqueue(first_arrival);

        // schedule all buses
        double tClock = 420;// temporary clock 7am Monday
        busClock = new double[] { tClock, tClock + 15, tClock + 10 };
        while (tClock < 7200)
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
            if(!(tClock >= 645 && tClock < 900))
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

            tClock += 30;
        }
    }

    public double[] getAccBusUtil()
    {
        return accumulatedBusUtilization;
    }

    public double[] getBusClock()
    {
        return busClock;
    }

    public double[] getAccQueueLength()
    {
        return accumulatedQueueLength;
    }

    public long[] getMaxQueueLength()
    {
        return maxQueueLength;
    }

    public double[] getAccumulatedWaitTime()
    {
        return accumulatedWaitTime;
    }

    /**
     * @return current system time in minutes
     */
    public double getClock()
    {
        return Clock;
    }

    /**
     * @param clock system time in minutes
     */
    public void setClock(double clock)
    {
        Clock = clock;
    }

    /**
     * @param evt the event that needs is to be processed
     */
    public void processArrivalEvent(Event evt)
    {
        int start = genStartLoc(evt.get_time());
        Person passenger = new Person(start, genEndLoc(start));
        int index = passenger.getStartLoc();
        // adds person to bus stop of the starting location
        try
        {
            stops[index].enqueue(passenger, Clock);
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

        double time = triangular(stream, minArrivalTime, meanArrivalTime, maxArrivalTime);
        Event next_arrival = new Event(arrival, Clock + time);
        FutureEventList.enqueue(next_arrival);
        LastEventTime = evt.get_time();
    }

    /**
     * @param e
     * @throws Exception
     */
    public void processBusEvent(Event e) throws Exception
    {

        int b = e.get_type() - 1;
        if(busDropoff(b)) busPickup(b);
        busClock[b] += e.get_time() - busClock[b];
    }

    /**
     * @param b
     * @throws Exception
     */
    private boolean busDropoff(int b) throws Exception
    {
        Bus bus = fleet[b];
        double timeDif = Clock - busClock[b];
        accumulatedBusUtilization[b] += timeDif * bus.getSize();
        bus.arrive(busLocationPointer[b]);
        if(busLocationPointer[b] == 0 && Clock > busBreakTime[b])
        {
            if(bus.getSize() != 0)
            {
                throw new Exception("Bus " + bus.getID() + " is not empty when on break!\n There are " + bus.getSize()
                        + " people still on the bus.");
            }
            return false;
        }
        return true;
    }

    /**
     * @param e   the event to be processed
     * @param bus the bus being used
     * @param b   the index of bus
     */
    private void busPickup(int pointer)
    {
        pickupCounter[pointer]++;
        // Call bus pickup
        int index = busLocationPointer[pointer];
        // uses temporary stop variable to call pickup
        double[] d = fleet[pointer].pickup(stops[index], Clock, maxWaitTime[index]);

        // update stops max/average wait time statistic - might have to be done with pickup
        accumulatedWaitTime[index] += d[0];
        maxWaitTime[index] = d[1];
        riderCounter[index] += d[2];
        if(busLocationPointer[pointer] >= 10)
        {
            busLocationPointer[pointer] = 0;
        }
        else
        {
            busLocationPointer[pointer]++;
        }
    }

    /**
     * Randomly generates a starting location for each person
     *
     * @param time the current time
     * @return the starting location
     */
    private int genStartLoc(double time)
    {
        int max = 10;
        int min = 0;
        int range = max - min + 1;
        int index = ((int) (Math.random() * range) + min);
        return index;
    }

    /**
     * @param start the starting location of the person
     * @return a location along the route
     */
    private int genEndLoc(int start)
    {
        int max = 10;
        int min = start + 1;
        int range = max - min + 1;
        int index = ((int) (Math.random() * range) + min);
        return index % 11;
    }

    /**
     *
     * @param rng
     * @param a
     * @param b
     * @param c
     * @return
     */
    private double triangular(Rand rng, double a, double b, double c)
    {
        double R = rng.next();
        double x;
        if(R <= (b - a) / (c - a)) x = a + Math.sqrt((b - a) * (c - a) * R);
        else x = c - Math.sqrt((c - b) * (c - a) * (1 - R));
        return x;
    }

    /**
     *
     */
    public void generateReport()
    {
        try
        {
            PrintWriter out = new PrintWriter("Report.txt");
            int index = 0;
            out.printf("SIMULATION REPORT:\n");
            out.printf("-----------------------------------------------------\n");
            out.printf("Run Time: " + Clock);
            out.printf("\n>>>>BUS STATISTICS:\n");
            ////
            index = 0;
            double[] averageUtil = new double[3];
            for (int i = 0; i < averageUtil.length; i++)
            {
                averageUtil[i] = accumulatedBusUtilization[i] / busClock[index];
            }
            out.printf("\n>>Utilization\n");
            index = 0;
            for (double i : averageUtil)
            {
                out.printf("\tWest Campus %d:\t\t%.2f people/minute\n", 1 + index, i);
                index++;
            }

            ////
            out.printf("\n>>>>STOP STATISTICS\n");
            //
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

            //
            out.printf("\n>>Maximum Length of Queues:\n");
            index = 0;
            for (double i : maxQueueLength)
            {
                out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(), i);
                index++;
            }
            //
            out.printf("\n>>Total People at Queue:\n");
            index = 0;
            int sum = 0;
            for (int i : riderCounter)
            {
                out.printf("\t%-20s\t\t%d people\n", stops[index].getName(), i);
                index++;
                sum += i;
            }
            out.printf("\n>>%-20s\t\t%d people\n", "Total People Served:", sum);

            ////
            out.printf("\n>>Average Wait Time People In Queue:\n");
            index = 0;
            for (double i : accumulatedWaitTime)
            {
                out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), i / riderCounter[index]);
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
