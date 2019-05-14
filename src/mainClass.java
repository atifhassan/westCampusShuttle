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
        long startTime = System.nanoTime();
        Simulator sim;
        int rep = 950; // number of repeated simulations
        double[] averageUtil = new double[] { 0.0, 0.0, 0.0 };
        double[][] averageUtilData = new double[3][rep];
        double[] AverageQueueLength = new double[11];
        double[][] AverageQueueLengthData = new double[11][rep];
        double[][] AverageWaitTimeData = new double[11][rep];
        long[] AverageMaxQueueLength = new long[11];
        long[][] MaxQueueLengthData = new long[11][rep];
        int[] riderCountSum = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int[][] riderCountSumData = new int[11][rep];
        int[] SumData = new int[rep];
        int sum;
        Stop[] stops = new Stop[] { new Stop("West Campus"), new Stop("Rapidan River O"), new Stop("Field House O"),
                new Stop("RAC O"), new Stop("Mason Pond O"), new Stop("Presidents Park"), new Stop("Masonvale"),
                new Stop("Rappohannock"), new Stop("RAC I"), new Stop("Field House I"), new Stop("Rapidan River I") };
        // Loop until clock is greater than 7200 minutes, 24hr Mon-Fri
        for (long i = 0; i < rep; i++)
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
            double[] accumulatedWaitTime = sim.getAccumulatedWaitTime();
            sum = 0;
            for (int j = 0; j < averageUtil.length; j++)
            {
                averageUtil[j] += (accumulatedBusUtil[j] / busClock[j]);
                averageUtilData[j][(int) i] = accumulatedBusUtil[j] / busClock[j];
            }
            for (int j = 0; j < AverageQueueLength.length; j++)
            {
                AverageQueueLength[j] += accumulatedQueueLength[j] / 1500;
                AverageQueueLengthData[j][(int) i] = accumulatedQueueLength[j] / 1500;
            }
            for (int j = 0; j < maxQueueLength.length; j++)
            {
                AverageMaxQueueLength[j] += maxQueueLength[j];
                MaxQueueLengthData[j][(int) i] = maxQueueLength[j];
            }
            for (int j = 0; j < riderCountSum.length; j++)
            {
                riderCountSum[j] += sim.riderCounter[j];
                riderCountSumData[j][(int) i] = sim.riderCounter[j];
                sum += sim.riderCounter[j];

            }
            SumData[i] = sum;
        }

        for (int i = 0; i < averageUtil.length; i++)
        {
            averageUtil[i] = averageUtil[i] / rep;
        }
        for (int i = 0; i < AverageQueueLength.length; i++)
        {
            AverageQueueLength[i] = AverageQueueLength[i] / rep;
            AverageMaxQueueLength[i] = AverageMaxQueueLength[i] / rep;
        }
        int riderCountAverage[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < riderCountSum.length; i++)
        {
            riderCountAverage[i] = riderCountSum[i] / (int) rep;
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
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1000000000.0;
        int sum = 0;
        for (int i : riderCountAverage)
        {
            sum += i;
        }
        System.out.printf("SIMULATION REPORT:\n");
        System.out.printf("-----------------------------------------------------\n");
        System.out.printf("Simulation Time:\t 1500 minutes\n");
        System.out.printf("%-20s\t %d people\n", "Average Ridership:", sum);
        System.out.printf("Runtime:\t\t %.3f seconds\n", seconds);
        System.out.printf("Number of Replications:\t " + rep);
        System.out.printf("\n\n>>Average Utilization\n");
        for (double i : averageUtil)
        {
            System.out.printf("West Campus %d:\t%.2f people/minute\n", 1 + index, i);
            System.out.printf("\tUpper Bound: %.2f\n",
                    ((mean(averageUtilData[index], rep) + tDiff(1.96, variance(averageUtilData[index], rep), rep))));
            System.out.printf("\tLower bound: %.2f\n",
                    ((mean(averageUtilData[index], rep) - tDiff(1.96, variance(averageUtilData[index], rep), rep))));
            index++;
        }
        System.out.printf("\n>>Average Number Of People In Queue\n");
        index = 0;
        for (double i : AverageQueueLength)
        {
            System.out.printf("%-20s\t%.2f people\n", stops[index].getName(), i);
            System.out.printf("\tUpper Bound: %.2f\n", ((mean(AverageQueueLengthData[index], rep)
                    + tDiff(1.96, variance(AverageQueueLengthData[index], rep), rep))));
            System.out.printf("\tLower bound: %.2f\n", ((mean(AverageQueueLengthData[index], rep)
                    - tDiff(1.96, variance(AverageQueueLengthData[index], rep), rep))));
            index++;
        }
        System.out.printf("\n>>Maximum Length of Queues\n");
        index = 0;
        for (double i : AverageMaxQueueLength)
        {
            System.out.printf("%-20s\t%.2f people\n", stops[index].getName(), i);
            System.out.printf("\tUpper Bound: %.2f\n", ((mean((MaxQueueLengthData[index]), rep)
                    + tDiff(1.96, variance(MaxQueueLengthData[index], rep), rep))));
            System.out.printf("\tLower bound: %.2f\n", ((mean(MaxQueueLengthData[index], rep)
                    - tDiff(1.96, variance(MaxQueueLengthData[index], rep), rep))));
            index++;
        }
        System.out.printf("\n>>Total People through Queues:\n");
        index = 0;
        for (int i : riderCountAverage)
        {
            System.out.printf("%-20s\t%d people\n", stops[index].getName(), i);
            System.out.printf("\tUpper Bound: %.2f\n", ((mean(riderCountSumData[index], rep)
                    + tDiff(1.96, variance(riderCountSumData[index], rep), rep))));
            System.out.printf("\tLower bound: %.2f\n", ((mean(riderCountSumData[index], rep)
                    - tDiff(1.96, variance(riderCountSumData[index], rep), rep))));
            index++;
        }
        System.out.printf("\n>>%-20s\t\t%d people\n", "Total People Served Average:", mean(SumData, rep));
        System.out.printf("\n>>%-20s\t\t%d people\n", "Upper Bound:", ((mean(SumData, rep)
                  + tDiff(1.96, variance(SumData, rep), rep))));
        System.out.printf("\n>>%-20s\t\t%d people\n", "Lower Bound:", ((mean(SumData, rep)
                  - tDiff(1.96, variance(SumData, rep), rep))));

        out.printf("\n>>Average Wait Time People In Queue:\n");
        index = 0;
        for (double i : accumulatedWaitTime)
        {
            out.printf("\t%-20s\t\t%.2f minutes\n", stops[index].getName(), mean(AverageWaitTimeData[index],rep));
            System.out.printf("\tUpper Bound: %.2f\n", ((mean((AverageWaitTimeData[index]), rep)
                    + tDiff(1.96, variance(AverageWaitTimeData[index], rep), rep))));
            System.out.printf("\tLower bound: %.2f\n", ((mean(AverageWaitTimeData[index], rep)
                    - tDiff(1.96, variance(AverageWaitTimeData[index], rep), rep))));
            index++;
        }
    }

    private static double variance(double a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        double mean = (double) s / (double) n;
        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) * (a[i] - mean);

        return (double) sqDiff / n;
    }

    private static double variance(int a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        double mean = (double) s / (double) n;
        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) * (a[i] - mean);

        return (double) sqDiff / n;
    }

    private static double variance(long a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        double mean = (double) s / (double) n;
        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) * (a[i] - mean);

        return (double) sqDiff / n;
    }

    private static double mean(double a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        return (double) s / (double) n;
    }

    private static double mean(long a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        return (double) s / (double) n;
    }

    private static double mean(int a[], int n)
    {
        double s = 0;
        for (int i = 0; i < n; i++)
            s += a[i];
        return (double) s / (double) n;
    }

    static double tDiff(double t, double var, double n)
    {
        return t * Math.sqrt(var / n);
    }
}
