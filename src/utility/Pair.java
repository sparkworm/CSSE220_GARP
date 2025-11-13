package utility;


/**
 * Used for sorting an array of fitnesses without losing the original indices
 * Could eventually make generic, but not really necessary for this project.
 */
public class Pair {
    private double first;
    private int second;

    public Pair(double first, int second) {
        this.first = first;
        this.second = second;
    }

    public double getFirst() {
        return this.first;
    }

    public int getSecond() {
        return this.second;
    }
}
