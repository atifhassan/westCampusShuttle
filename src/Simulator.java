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

    private final double meanArrivalTime;
    private final double stdArrivalTime;
    /**
     * the total number of riders in the system
     */
    private int[] riderCounter;
    private int[] pickupCounter; // the total number of times the pickup event is called
    private double[] accumulatedQueueLength;
    private double[] accumulatedWaitTime;
    private double[] maxWaitTime;
    private double[] accumulatedBusUtilization;
    private double[] maxOccupancy;
    private final double[] busBreakTime = { 1410, 1425, 1420 };
    private double Clock;
    private double LastEventTime;
    private long[] maxQueueLength;
    public EventList FutureEventList; // List of events that will happen in the future
    private Rand stream;
    private Stop[] stops; // the array of bus stop queues
    private int busLocationPointer[]; // keeps track of the location of each bus
    private Bus[] fleet; // stores all bus objects

    /**
     * @param mean Mean Inter-arrival Time
     * @param std  standard deviation
     */
    public Simulator(double mean, double std)
    {
        FutureEventList = new EventList();
        stream = new Rand();
        Clock = 360.0;// 6am Monday
        meanArrivalTime = mean;
        stdArrivalTime = std;
        this.initialize();
    }

    /**
     * @return
     */
    public double getClock()
    {
        return Clock;
    }

    /**
     * @param clock
     */
    public void setClock(double clock)
    {
        Clock = clock;
    }

    /**
     * Initialize all variables,stops, and route and start the queue
     */
    private void initialize()
    {
        LastEventTime = 0.0;
        accumulatedBusUtilization = new double[] { 0.0, 0.0, 0.0 };
        maxOccupancy = new double[] { 0.0, 0.0, 0.0 };
        pickupCounter = new int[] { 0, 0, 0 };
        maxQueueLength = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        riderCounter = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        accumulatedQueueLength = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        accumulatedWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        maxWaitTime = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        stops = new Stop[] { new Stop("West Campus"), new Stop("Rapidan River O"), new Stop("Field House O"),
                new Stop("RAC O"), new Stop("Mason Pond O"), new Stop("Presidents Park"), new Stop("Masonvale"),
                new Stop("Rappohannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I") };
        busLocationPointer = new int[] { 0, 0, 0 };

        // creates the buses and starts them at west campus
        fleet = new Bus[] { new Bus('1'), new Bus('2'), new Bus('3') };

        // schedule first arrival
        double time = normal(stream, meanArrivalTime, stdArrivalTime);
        Event first_arrival = new Event(arrival, Clock + time);
        FutureEventList.enqueue(first_arrival);

        // schedule all buses
        double tClock = 0.0;// temporary clock 7am Monday
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

            tClock += 30;
        }
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
            stops[index].enqueue(passenger, evt);
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
        if((Clock % 1440) < 360) // people can't arrive before 6am
        {
            Clock = (Clock - (Clock % 1440)) + 360; // set clock to 6am
        }
        double time = normal(stream, meanArrivalTime, stdArrivalTime);
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

        Bus temp = null;
        int b = -1;
        switch (e.get_type())
        {
            case 1:
                b = 0;
                temp = fleet[b];
                busDropoff(b);
                busPickup(e, temp, b);
                break;
            case 2:
                b = 1;
                temp = fleet[b];
                busDropoff(b);
                busPickup(e, temp, b);
                break;
            case 3:
                b = 2;
                temp = fleet[b];
                busDropoff(b);
                busPickup(e, temp, b);
                break;
            default:
                throw new Exception("wrong type of event");
        }
        LastEventTime = e.get_time();
    }

    /**
     * @param b
     * @throws Exception
     */
    private void busDropoff(int b) throws Exception
    {
        Bus bus = fleet[b];
        double timeDif = Clock - LastEventTime;
        if(bus.getSize() > maxOccupancy[b])
        {
            maxOccupancy[b] = bus.getSize();
        }
        accumulatedBusUtilization[b] += timeDif * bus.getSize();
        bus.arrive(busLocationPointer[b]);
        if(busLocationPointer[b] == 0 && (Clock % 1440) > busBreakTime[b])
        {
            if(bus.getSize() != 0)
            {
                throw new Exception("Bus " + bus.getID() + " is not empty when on break!\n There are " + bus.getSize()
                        + " people still on the bus.");
            }
        }
    }

    /**
     * @param e   the event to be processed
     * @param bus the bus being used
     * @param b   the index of bus
     * @throws Exception the wrong type of event is passed
     */
    private void busPickup(Event e, Bus bus, int pointer) throws Exception
    {
        pickupCounter[pointer]++;
        // Call bus pickup
        int index = busLocationPointer[pointer];
        // uses temporary stop variable to call pickup
        double[] d = bus.pickup(stops[index], e, maxWaitTime[index]);

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
        /*
         * double temp = Math.random(); double wcP, rroP, fhoP, racoP, mpoP, ppP, mvP, rapP, raciP, fhiP, rriP; wcP =
         * -0.15 * Math.log(time) + 0.8423; rroP = -0.15 * Math.log(time) + 0.8423; fhoP = -0.15 * Math.log(time) +
         * 0.8423; racoP = -0.15 * Math.log(time) + 0.8423; mpoP = -0.15 * Math.log(time) + 0.8423; ppP = -0.15 *
         * Math.log(time) + 0.8423; mvP = -0.15 * Math.log(time) + 0.8423; rapP = -0.15 * Math.log(time) + 0.8423; raciP
         * = -0.15 * Math.log(time) + 0.8423; fhiP = -0.15 * Math.log(time) + 0.8423; rriP = -0.15 * Math.log(time) +
         * 0.8423;
         */
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
        return index%11;
    }

//    /**
//     * @param rng
//     * @param mean
//     * @return
//     */
//    private double exponential(Rand rng, double mean)
//    {
//        return -mean * Math.log(rng.next());
//    }

//    /**
//     *
//     * @param rng
//     * @param a
//     * @param b
//     * @param c
//     * @return
//     */
//    private double triangular(Rand rng, double a, double b, double c)
//    {
//        double R = rng.next();
//        double x;
//        if(R <= (b - a) / (c - a)) x = a + Math.sqrt((b - a) * (c - a) * R);
//        else x = c - Math.sqrt((c - b) * (c - a) * (1 - R));
//        return x;
//    }

//    /**
//     * @param rng
//     * @param a
//     * @param b
//     * @return
//     */
//    private double uniform(Rand rng, double a, double b)
//    {
//        return a + (b - a) * rng.next();
//    }

    /**
     *
     * @param rng  random number stream
     * @param mean
     * @param std  standard deviation
     * @return
     */
    private double normal(Rand rng, double mu, double s)
    {
        return Math.exp(mu+(s*rng.next()));
    }

    /**
     *
     */
    public void generateReport()
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
                out.printf("\tWest Campus %d:\t\t%f people/minute\n", 1 + index, i);
                index++;
            }
            ////
            index = 0;
            for (double i : maxOccupancy)
            {
                out.printf("\tWest Campus %c:\t\t%f\n", fleet[index].getID(), i);
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
            //
            index = 0;
            int sum = 0;
            for (int i : riderCounter)
            {
                out.printf("\t%-20s\t\t%d people\n", stops[index].getName(), i);
                index++;
                sum += i;
            }
            out.printf("\t%d people\n", sum);
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
