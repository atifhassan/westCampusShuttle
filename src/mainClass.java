/**
 * @author Atif Hassan
 */
public class mainClass
{
    /**
     *
     * @param argv
     */
    public static void main(String argv[])
    {
        Simulator sim;
        int rep = 500; // number of repeated simulations
        double[] averageUtil = new double[] {0.0,0.0,0.0};
        double[][] averageUtilData = new double[3][500];
        double[] AverageQueueLength = new double[11];
        long[] AverageMaxQueueLength = new long[11];
        Stop[]  stops = new Stop[] { new Stop("West Campus"), new Stop("Rapidan River O"), new Stop("Field House O"),
                new Stop("RAC O"), new Stop("Mason Pond O"), new Stop("Presidents Park"), new Stop("Masonvale"),
                new Stop("Rappohannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I") };
        // Loop until clock is greater than 7200 minutes, 24hr Mon-Fri
        for (int i = 0; i < rep; i++)
        {
            sim = new Simulator(0, .5, 1.5, (Math.random() * .01) + .99);
            while (sim.getClock() < 1500)
            {

                Event evt = sim.FutureEventList.getMin(); // get imminent event
                sim.FutureEventList.dequeue(); // delete the event
                sim.setClock(evt.get_time()); // advance in time
                if(evt.get_type() == Simulator.arrival)
                {
                    sim.processArrivalEvent(evt);
                }
                else
                {
                    try
                    {
                        sim.processBusEvent(evt);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            double[] accumulatedBusUtil = sim.getAccBusUtil();
            double[] busClock = sim.getBusClock();
            double[] accumulatedQueueLength = sim.getAccQueueLength();
            long[] maxQueueLength = sim.getMaxQueueLength();
            for (int j = 0; j < averageUtil.length; j++)
            {
                averageUtil[j] += (accumulatedBusUtil[j] / busClock[j]);
                averageUtilData[j][i] = accumulatedBusUtil[j] / busClock[j];
            }
            for (int j = 0; j < AverageQueueLength.length; j++)
            {
                AverageQueueLength[j] += accumulatedQueueLength[j] / busClock[j];
            }
            for(int j = 0; j < maxQueueLength.length; j++)
            {
                AverageMaxQueueLength[j] += maxQueueLength[j];
            }
        }
        for (int i = 0; i < averageUtil.length; i++)
        {
            averageUtil[i] = averageUtil[i]/rep;
        }
        for (int i = 0; m < AverageQueueLength.length; i++)
        {
            AverageQueueLength[i] = AverageQueueLength[i]/rep;
            AverageMaxQueueLength[i] = AverageMaxQueueLength[i]/rep;
        }


        /*
         * double AverageQueueLength[] = new double[11]; for (int i = 0; i < AverageQueueLength.length; i++) {
         * AverageQueueLength[i] = accumulatedQueueLength[i] / Clock; }
         *
         * index = 0; for (double i : maxQueueLength) { out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(),
         * i); index++; } // out.printf("\n>>Total People at Queue:\n"); index = 0; int sum = 0; for (int i :
         * riderCounter) { out.printf("\t%-20s\t\t%d people\n", stops[index].getName(), i); index++; sum += i; }
         * out.printf("\n>>%-20s\t\t%d people\n","Total People Served:", sum);
         *
         * //// out.printf("\n>>Average Wait Time People In Queue:\n"); index = 0; for (double i : accumulatedWaitTime)
         * { out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), i / riderCounter[index]); index++; } //
         * out.printf("\n>>Max Wait Time People In Queue:\n"); index = 0; for (double i : maxWaitTime) {
         * out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), i); index++; }
         */

        int index = 0;
        out.printf("SIMULATION REPORT:\n");
        out.printf("-----------------------------------------------------\n");
        out.printf("Run Time: " + 1500);
        out.printf("Number of reps: " + rep);
        out.printf("\n>>>>BUS STATISTICS:\n");
        out.printf("\n>>Utilization\n");
        for (double i : averageUtil)
        {
            System.out.printf("\tWest Campus %d:\t\t%.2f people/minute\n", 1 + index, i);
            index++;
        }
        out.printf("\n>>Average Number Of People In Queue:\n");
        index = 0;
        for (double i : AverageQueueLength)
        {
            out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(), i);
            System.out.println("Average utilization for bus " + i + " upper bound: " + (mean(averageUtilData[i], rep) + tDiff(1.96,variance(averageUtilData[i], rep),rep)));
            System.out.println("Average utilization for bus " + i + " lower bound: " + (mean(averageUtilData[i], rep) - tDiff(1.96,variance(averageUtilData[i], rep),rep)));
            index++;
        }
        out.printf("\n>>Maximum Length of Queues:\n");
        index = 0;
        for (double i : maxQueueLength)
        {
            out.printf("\t%-20s\t\t%.2f people\n", stops[index].getName(), i);
            index++;
        }

    }
    static double variance(double a[],int n)
    {
      double s = 0;
      for (int i = 0; i < n; i++)
          s += a[i];
      double mean = (double)s/(double)n;
      double sqDiff = 0;
      for (int i = 0; i < n; i++)
        sqDiff += (a[i] - mean)*(a[i] - mean);

      return (double)sqDiff/n;
    }
    static double mean(double a[], int n)
    {
      double s = 0;
      for (int i = 0; i < n; i++)
        s += a[i];
      return (double)s/(double)n;
    }
    static double tDiff(double t, double var, double n)
    {
      return t * Math.sqrt(var/n);
    }
}
