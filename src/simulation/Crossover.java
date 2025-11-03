package simulation;

import java.util.ArrayList;

/**
 * Class responsible for 'breeding' surviving chromosomes from Selection such that the population is restored.
 */
public class Crossover {

    /**
     * Creates copies of the parents to repopulate
     * TODO: Should be overridden with a true crossover function
     * @param survivors the population from which the new population should be progenated
     * @param targetPopulation the total number that the population should be rebuilt up to
     */
    public ArrayList<Chromosome> repopulate(ArrayList<Chromosome> survivors, int targetPopulation) {
        int chromosomesToGo = targetPopulation - survivors.size();
        // doesn't matter if references are shared since old population will soon be replaced.
        ArrayList<Chromosome> newPopulation = new ArrayList<>(survivors);
        for (int i=0; i<chromosomesToGo; i++) {
            newPopulation.add(new Chromosome(survivors.get((i % survivors.size())))); // wraps back around
        }
        return newPopulation;
    }
}
