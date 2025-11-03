package simulation;

/**
 * Counts fitness based on how close the Chromosome is to the base Chromosome
 */
public class FitnessMatchTarget extends Fitness {
    private Chromosome baseChromosome;

    public FitnessMatchTarget(Chromosome baseChromosome) {
        this.baseChromosome = baseChromosome;
    }

    /**
     * Calculates fitness according to class specifications
     * @param chromosome must be the same length as baseChromosome
     * @return a value between 0.0 and 1.0 (inclusive)
     */
    @Override
    public double calculateFitness(Chromosome chromosome) {
        return chromosome.getLength() - baseChromosome.hammingDistance(chromosome);
    }
}
