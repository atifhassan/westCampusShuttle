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
        /**
         * Simulator ss = new Simulator(); ss.FutureEventList = new EventList(); ss.People = new Queue(); ss.stream =
         * new Rand(); ss.Clock = 0.0; ss.MeanInterArrivalTime = 7.7; ss.MeanServiceTime = 6.21;
         **/
        Simulator sim = new Simulator(7.7, 6.21);
        sim.Initialization();
        // Loop until clock is greater than 7200 minutes, 24hr Mon-Fri
        // INCOMPLETE!!!
        // TODO
        while (sim.Clock < 7200)
        {
            Event evt = sim.FutureEventList.getMin(); // get imminent event
            sim.FutureEventList.dequeue(); // delete the event
            sim.Clock = evt.get_time(); // advance in time
            System.out.println(evt.get_type());
            if(evt.get_type() == Simulator.arrival)
            {
                sim.ProcessArrival(evt);
            }
            else
            {
                try {
                sim.ProcessBusArrive(evt);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        sim.ReportGeneration();
    }

}
