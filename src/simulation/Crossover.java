package simulation;

import java.util.ArrayList;

/**
 * Class responsible for 'breeding' surviving chromosomes from Selection such that the population is restored.
 */
public abstract class Crossover {

    /**
     * Creates an array of Chromosomes representing the new population
     * @param survivors the population from which the new population should be progenated
     * @param targetPopulation the total number that the population should be rebuilt up to
     */
    public abstract ArrayList<Chromosome> repopulate(ArrayList<Chromosome> survivors, int targetPopulation);
}
