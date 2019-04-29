/**
 * @author Atif Hassan
 *
 */
public class Stop
{
    private String name;
    private int capacity = 30;
    private Person[] line = new Person[capacity];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    /**
     * 
     * @param name
     */
    public Stop(String name)
    {
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * 
     * @return
     */
    public int getSize()
    {
        return size;
    }

    /**
     * 
     * @param p
     * @param e
     * @throws Exception
     */
    public void enqueue(Person p, Event e) throws Exception
    {
        if (size == capacity)
            throw new Exception("Stop is Full!");
        p.setStartWait(e.get_time());
        line[tail] = p;
        if (tail == capacity - 1)
            tail = 0;
        else
            tail++;
        size++;

    }

    /**
     * 
     * @return
     */
    public Person peek()
    {
        return line[head];
    }

    /**
     * 
     * @param e
     * @return
     * @throws Exception
     */
    public Person dequeue(Event e) throws Exception
    {
        if (this.isEmpty())
            throw new Exception("Line is Empty");
        Person temp = line[head];
        temp.setEndWait(e.get_time());
        if (head == capacity - 1)
            head = 0;
        else
            head++;
        size--;
        return temp;
    }

    /**
     * 
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     */
    public String toString()
    {
        return name + ": " + size;
    }

}
