/**
 * 
 * @author Atif Hassan
 *
 */
public class mainClass
{
    /**
     * 
     * @param argv
     */
    public static void main(String argv[])
    {
        Simulator sim = new Simulator(7.7);
        // Loop until clock is greater than 7200 minutes, 24hr Mon-Fri
        while (sim.Clock < 7200)
        {
            Event evt = sim.FutureEventList.getMin(); // get imminent event
            sim.FutureEventList.dequeue(); // delete the event
            sim.Clock = evt.get_time(); // advance in time
            if(evt.get_type() == Simulator.arrival)
            {
                sim.processArrival(evt);
            }
            else
            {
                try
                {
                    sim.processBus(evt);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        sim.ReportGeneration();
    }

}
