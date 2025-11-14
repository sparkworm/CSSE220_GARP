package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SelectionRanked extends Selection {
    Random random;

    SelectionRanked(Fitness fitness, Random random) {
        super(fitness);
        this.random = random;
    }

    @Override
    public ArrayList<Chromosome> makeSelection(ArrayList<Chromosome> population, double surviveRatio) {
        ArrayList<Chromosome> popCopy = new ArrayList<>(population);

        int numSurvivors = calculateNumSurvivors(population.size(), surviveRatio);
        ArrayList<Chromosome> survivors = new ArrayList<>(numSurvivors);
        ArrayList<Double> fitnesses = new ArrayList<>(popCopy.size());
        ArrayList<Double> sortedFitnesses = new ArrayList<>(popCopy.size());
        for (Chromosome chrom : population) {
            Double fit = fitness.calculateFitness(chrom);
            fitnesses.add(fit);
            sortedFitnesses.add(fit); // Double is immutable, so this shouldn't create problems
        }

        // With this array sorted, the index can correlate to the "rank":
        // rank = length-idx;
        // sumRank = (length/2)(length+1)
        // position based on num [0, sumRank]:
        Collections.sort(sortedFitnesses);

        ArrayList<Double> rankedFitnesses = new ArrayList<>(sortedFitnesses.size());
        for (int i=0; i<sortedFitnesses.size(); i++) {
            rankedFitnesses.add((double)(i+1));
        }

        ArrayList<Double> unsortedRanks = new ArrayList<>(Collections.nCopies(sortedFitnesses.size(), null));
        for (int i=sortedFitnesses.size()-1; i>=0; i--) {
            for (int j=0; j<fitnesses.size(); j++) {
                if (sortedFitnesses.get(i).equals(fitnesses.get(j)) && unsortedRanks.get(j)==null) {
                    unsortedRanks.set(j, (double)(i+1));
                    break; // Will end up assigning unique ranks to all, even if their fitness is identical.
                }
            }
        }

        double totalFitness = maxDepth(rankedFitnesses.size());

        // Roulette Wheel selection (basically what's happening now that "ranks" are assigned as new fitness values)
        for (int i=0; i<numSurvivors; i++) {
            // Calculate a value between 0.0 and total fitness, this will determine how far the array of Chromosomes we
            // make it before stopping, determining the chosen Chromosome
            double depth = random.nextDouble() * totalFitness;

            int idx = 0;
            depth -= rankedFitnesses.get(idx);
            // Second condition ensures that the loop won't go too far even with floating point error
            while(depth > 0 && idx < numSurvivors) {
                idx++;
                depth -= rankedFitnesses.get(idx);
            }
            // idx is now the index of the chosen Chromosome
            totalFitness -= rankedFitnesses.get(idx);
            survivors.add(popCopy.get(idx));
            rankedFitnesses.remove(idx);
            popCopy.remove(idx);
        }

        return survivors;
    }

    /**
     * CURRENTLY UNUSED
     * Finds which Chromosome should be chosen based on a depth value from 0 to (length/2)*(length+1)
     * NOTE: could probably solve better with DP and binary search
     * @param depth works its way from the back of the array (which is the highest)
     * @return
     */
    public int findIdxFromDepth(double depth, int arrayLength) {
        int sum = 0;
        for (int i=arrayLength-1; i>=0; i--) {
            //sum += sortedFitnesses.get(i);  // might this work for roulette??
            sum += i+1;
            if (sum > depth) return i;
        }
        return 0;
    }

    /**
     * Finds max depth based on the length of the array.  The max depth is simply the sum of all ranks in the array,
     * or simply, a sum of all integers 1 through length, inclusive
     * @param arrayLength
     * @return
     */
    public int maxDepth(int arrayLength) {
        return (int) (((double)arrayLength / 2.0) * (arrayLength + 1));  // Shouldn't see floating point error for 1/2
    }
}
