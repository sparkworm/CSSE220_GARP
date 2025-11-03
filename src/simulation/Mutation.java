package simulation;

import java.util.ArrayList;

/**
 * Class responsible for mutating all the chromosomes in the population.  If other kinds of mutation (besides random
 * flips) are wanted in the future, this class should be made abstract and the function mutateChromosomes should be
 * overridden.
 */
public class Mutation {
    private double mutationRate;

    public Mutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    /**
     * Mutates all Chromosomes in the provided list with the specified mutation method.  In this case, bits are simply
     * randomly flipped, but this may be overridden in the future.
     * @param chromosomes the list of chromosomes that will be modified
     */
    public void mutateChromosomes(ArrayList<Chromosome> chromosomes) {
        for (Chromosome chrom : chromosomes) {
            chrom.randomizeGenotype(mutationRate);
        }
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}
