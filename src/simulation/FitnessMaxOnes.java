package simulation;

/**
 * Evaluates fitness as the number of 1s in chromosome.  For example '01101011' would have fitness 5.
 */
public class FitnessMaxOnes extends Fitness {
    @Override
    public double calculateFitness(Chromosome chromosome) {
        int count = 0;
        for (char c : chromosome.genotypeString(-1).toCharArray()) {
            if (c=='1') count++;
        }
        return count;
    }
}
