public class Rand
{
    static int a = 16807;
    final static int c = 0;
    final static int m = 2147483647;
    // setting the seed x0.
    long x;

    public Rand(double seed)
    {
        x = 123456;
        a = (int) (a * seed);
    }

    double next()
    {
        // Calculate next value in sequence.
        x = ((a * x) + c) % m;
        // Return its 0-to-1 value.
        return (double) x / m;
    }
}
