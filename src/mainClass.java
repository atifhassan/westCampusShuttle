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
            for (int j = 0; j < averageUtil.length; j++)
            {
                averageUtil[j] += (accumulatedBusUtil[j] / busClock[j]);
            }
        }
        for (int i = 0; i < averageUtil.length; i++)
        {
            averageUtil[i] = averageUtil[i]/rep;
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
        for (double i : averageUtil)
        {
            System.out.printf("\tWest Campus %d:\t\t%.2f people/minute\n", 1 + index, i);
            index++;
        }

    }
}
