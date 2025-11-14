package simulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Makes a selection by using each chromosome's relative fitness as its odds of being selected.
 */
public class SelectionRoulette extends Selection {
    private Random random;

    public SelectionRoulette(Fitness fitness, Random random) {
        super(fitness);
        this.random = random;
    }


    @Override
    public ArrayList<Chromosome> makeSelection(ArrayList<Chromosome> population, double surviveRatio) {
        // Shallow copy of population so that elements can be removed
        ArrayList<Chromosome> popCopy = new ArrayList<>(population);
        int numSurvivors = calculateNumSurvivors(population.size(), surviveRatio);
        ArrayList<Chromosome> survivors = new ArrayList<>(numSurvivors);
        ArrayList<Double> fitnesses = new ArrayList<>(numSurvivors);
        double totalFitness = 0.0;

        for (Chromosome chrom : popCopy) {
            double fit = fitness.calculateFitness(chrom);
            totalFitness += fit;
            fitnesses.add(fit);
        }

        // Roulette Wheel selection
        for (int i=0; i<numSurvivors; i++) {
            // Calculate a value between 0.0 and total fitness, this will determine how far the array of Chromosomes we
            // make it before stopping, determining the chosen Chromosome
            double depth = random.nextDouble() * totalFitness;
            // Bad complexity.  Could probably store values in fitnesses as sums up to given point, then binary search
            int idx = 0;
            depth -= fitnesses.get(idx);
            // Second condition ensures that the loop won't go too far even with floating point error
            while(depth > 0 && idx < numSurvivors) {
                idx++;
                depth -= fitnesses.get(idx);
            }
            // idx is now the index of the chosen Chromosome
            totalFitness -= fitnesses.get(idx);
            survivors.add(popCopy.get(idx));
            fitnesses.remove(idx);
            popCopy.remove(idx);
        }

        return survivors;
    }
}
