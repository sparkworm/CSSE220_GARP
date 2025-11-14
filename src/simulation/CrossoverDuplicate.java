package simulation;

import java.util.ArrayList;

/**
 * Technically not a form of crossover, but interfaces with the program in the same way,  This is the "Crossover" that
 * should be performed if Crossover is disabled.  Works by simply duplicating the parent chromosomes.
 */
public class CrossoverDuplicate extends Crossover {

    /**
     * Creates copies of the parents to repopulate.  Technically leaves some of the original generation since these are
     * all duplicates anyway.
     * @param survivors the population from which the new population should be progenated
     * @param targetPopulation the total number that the population should be rebuilt up to
     */
    @Override
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
