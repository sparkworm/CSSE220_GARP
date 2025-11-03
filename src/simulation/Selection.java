package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Selection {
    Fitness fitness;

    public Selection (Fitness fitness) {
        this.fitness = fitness;
    }

    /**
     * Returns the x fittest survivors, where x is the surviveRation times the population size, rounded up
     * @param population the array of all the Chromosomes in the population
     * @param surviveRatio the ratio of survivors to non-suvivors. 0.5 will purge half and keep the other half.
     *                     MUST NOT BE 0
     * @return the surviving or "fittest" chromosomes
     */
    public ArrayList<Chromosome> trunctationSelection(ArrayList<Chromosome> population, double surviveRatio) {
        ArrayList<Chromosome> popCopy = new ArrayList<>(population);
        // Cover potential edge case where there is only
//        if (population.size() < 2) {
//            if (surviveRatio >= 0.5) return population;
//            else return new ArrayList<Chromosome>();
//        }

        int numSurvivors = (int)(population.size() * surviveRatio);
        numSurvivors += (numSurvivors / surviveRatio < population.size()) ? 1 : 0;
        ArrayList<Chromosome> survivors = new ArrayList<>(numSurvivors);
        ArrayList<Double> fitnesses = new ArrayList<>(numSurvivors);
        ArrayList<Double> sortedFitness = new ArrayList<>(numSurvivors);
        // TODO FINISH THIS FUNCTION
        for (Chromosome chrom : population) {
            Double fit = fitness.calculateFitness(chrom);
            fitnesses.add(fit);
            sortedFitness.add(fit); // Double is immutable, so this shouldn't create problems
        }
        Collections.sort(sortedFitness);

        for (int i=sortedFitness.size()-1; i>=sortedFitness.size()-numSurvivors; i--) {
            for (int j=0; j<popCopy.size();j++) { // terrible efficiency btw
                if (fitnesses.get(j).equals(sortedFitness.get(i))) {
                    survivors.add(population.get(j));
                    popCopy.remove(j);
                    fitnesses.remove(j);
                    break;
                }
            }
        }

        return survivors;
    }
}
