package simulation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Makes a selection by simply picking the fittest individuals and discarding all others.
 */
public class SelectionTruncation extends Selection {
    public SelectionTruncation (Fitness fitness) {
        super(fitness);
    }

    /**
     * Returns the x fittest survivors, where x is the surviveRation times the population size, rounded up.  These are
     * in descending fitness order.
     * @param population the array of all the Chromosomes in the population
     * @param surviveRatio the ratio of survivors to non-suvivors. 0.5 will purge half and keep the other half.
     *                     MUST NOT BE 0
     * @return the surviving or "fittest" chromosomes in order of fittest to least fit
     */
    public ArrayList<Chromosome> makeSelection(ArrayList<Chromosome> population, double surviveRatio) {
        ArrayList<Chromosome> popCopy = new ArrayList<>(population);

        int numSurvivors = calculateNumSurvivors(population.size(), surviveRatio);
        ArrayList<Chromosome> survivors = new ArrayList<>(numSurvivors);
        ArrayList<Double> fitnesses = new ArrayList<>(popCopy.size());
        ArrayList<Double> sortedFitness = new ArrayList<>(popCopy.size());
        for (Chromosome chrom : population) {
            Double fit = fitness.calculateFitness(chrom);
            fitnesses.add(fit);
            sortedFitness.add(fit); // Double is immutable, so this shouldn't create problems
        }
        Collections.sort(sortedFitness);

        for (int i=sortedFitness.size()-1; i>=sortedFitness.size()-numSurvivors; i--) {
            for (int j=0; j<popCopy.size();j++) { // terrible efficiency btw
                if (fitnesses.get(j).equals(sortedFitness.get(i))) {
                    survivors.add(popCopy.get(j));
                    popCopy.remove(j);
                    fitnesses.remove(j);
                    break;
                }
            }
        }

        return survivors;
    }

}
