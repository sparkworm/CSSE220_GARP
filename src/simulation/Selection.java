package simulation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Superclass from which other forms of selection inherit.  Needs a fitness function to perform selection.
 */
public abstract class Selection {
    protected Fitness fitness;

    public Selection (Fitness fitness) {
        this.fitness = fitness;
    }

    public abstract ArrayList<Chromosome> makeSelection(ArrayList<Chromosome> population, double surviveRatio);

    /**
     * Calculates the number of survivors there should be from a population of a specified size with a specified
     * survival ratio.  Rounds up if size * surviveRatio is not a whole number
     * @param size number of Chromosomes in population
     * @param surviveRatio value on range [0.0, 1.0] specifying ratio of survivors to total.  Can but shouldn't be 0 or 1
     * @return The number of survivors
     */
    public int calculateNumSurvivors(int size, double surviveRatio) {
        return (int) Math.ceil(surviveRatio * size);
    }
}
