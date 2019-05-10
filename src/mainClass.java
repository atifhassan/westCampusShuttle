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
        Simulator sim = new Simulator(0, .5, 1.5);
        // Loop until clock is greater than 7200 minutes, 24hr Mon-Fri
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
        sim.generateReport();
        System.out.println("Done!");
    }
}
