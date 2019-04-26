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
    private int totalArrivals = 0;

    public Stop(String name)
    {
        this.name = name;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

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

    public Person peek()
    {
        return line[head];
    }

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
        totalArrivals++;
        return temp;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name + ": " + size;
    }
    public int getTotalArrivals()
    {
      return totalArrivals;
    }

}
