package simulation;

import java.util.ArrayList;

/**
 * Class responsible for 'breeding' surviving chromosomes from Selection such that the population is restored.
 */
public abstract class Crossover {

    /**
     * Margin details for limiting where crossover can occur.  Implementation is left up to children.
     */
    protected double margin;

    /**
     * Creates an array of Chromosomes representing the new population
     * @param survivors the population from which the new population should be progenated
     * @param targetPopulation the total number that the population should be rebuilt up to
     */
    public abstract ArrayList<Chromosome> repopulate(ArrayList<Chromosome> survivors, int targetPopulation);

    public void setMargin(double margin) { this.margin = margin; }
}
