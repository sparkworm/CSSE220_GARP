package simulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Responsible for performing a genetic 'crossover' where [0,n) of one genotype is combined with [n,end] of the other
 */
public class CrossoverSinglePoint extends Crossover {
    private final double DEFAULT_MARGIN = 0.2;
    /**
     * Might also consider grabbing the Random of one of the Chromosomes in the survivors, but this might make things
     * harder to test/debug.
     */
    private Random random;


    /**
     * When picking a point to perform crossover, margin forms the min distance in from either end that the point must be.
     * <br>
     * MUST NOT EXCEED 0.5
     */
    public CrossoverSinglePoint(Random random, double margin) {
        this.random = random;
        this.margin = margin;
    }
    public CrossoverSinglePoint(Random random) {
        this.random = random;
        this.margin = DEFAULT_MARGIN;
    }

    /**
     * Create a new generation from the survivors of last generation
     * UNLIKE CrossoverDuplicate, this leaves no survivors
     * @param survivors the population from which the new population should be progenated
     *                  SHOULD BE GREATER THAN 1
     * @param targetPopulation the total number that the population should be rebuilt up to
     * @return
     */
    public ArrayList<Chromosome> repopulate(ArrayList<Chromosome> survivors, int targetPopulation) {
        int chromosomesToGo = targetPopulation;// - survivors.size();
        ArrayList<Chromosome> newPopulation = new ArrayList<>(targetPopulation);
        for (int i=0; i<chromosomesToGo; i++) {
            Chromosome parent1 = survivors.get(i % survivors.size());
            Chromosome parent2 = survivors.get((i+1) % survivors.size());
            int splicePoint = generateSplicePoint(parent1.getLength());
            newPopulation.add(parent1.spliceChromosomeWithOther(parent2, splicePoint));
        }
        return newPopulation;
    }

    /**
     * Generate a splicePoint randomly
     * @param genomeLength
     * @return random integer within margins of genomeLength
     */
    public int generateSplicePoint(int genomeLength) {
        return findSplicePoint(genomeLength, random.nextDouble());
    }

    /**
     * Finds where randVal would fall along the acceptable (within margins) range of genomeLength integers
     * @param genomeLength
     * @param randVal double between 0.0 and 1.0
     * @return the point indicated by randVal within margins on the range from 0 to genomeLength
     */
    public int findSplicePoint(int genomeLength, double randVal) {
        return (int) ((randVal * (1 - 2*margin) + margin) * genomeLength);
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }
}
