package simulation;


/**
 * Calculates fitness such that a higher number of consecutive ones is more fit.
 */
public class FitnessMaxConsecutiveOnes implements Fitness {
    /**
     * @param chromosome
     * @return the number of consecutive ones in the genotype
     */
    public double calculateFitness(Chromosome chromosome) {
        int count = 0;
        int maxCount = 0;
        for (char c : chromosome.genotypeString(-1).toCharArray()) {
            if (c=='1') {
                count++;
                if (count > maxCount) maxCount = count;
            }
            else {
                count = 0;
            }
        }
        return maxCount;
    }
}
