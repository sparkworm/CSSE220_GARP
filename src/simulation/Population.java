package simulation;

import utility.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Class responsible for managing the set of Chromosomes being evolved.
 * NOTE: design is a bit sketchy at the moment; the existence of getChromosomes() function may be a bad sign.
 */
public class Population {
    ArrayList<Chromosome> chromosomes;
    Random random;

    public Population(int numChromosomes, int chromosomeSize) {
        chromosomes = new ArrayList<>(numChromosomes);
        for (int i=0; i<numChromosomes; i++) {
            chromosomes.add(new Chromosome(chromosomeSize, true));
        }
    }
    public Population(int numChromosomes, int chromosomeSize, long seed) {
        chromosomes = new ArrayList<>(numChromosomes);
        this.random = new Random(seed);
        for (int i=0; i<numChromosomes; i++) {
            chromosomes.add(new Chromosome(chromosomeSize, random));
        }
    }
    public Population(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    /**
     * TODO: IMPLEMENT
     * Applies a mutation to every Chromosome of the population.
     * @param mutation the Mutation object that will be applied to every Chromosome.
     */
    public void mutateChromosomes(Mutation mutation) {
        System.err.println("mutateChromosomes not yet implemented");
    }

    /**
     * Sorts Chromosomes from greatest to least
     */
    public void sortChromosomesByFitness(Fitness fitness) {
        ArrayList<Pair> indexedFitnessList = new ArrayList<>(getSize());
        //ArrayList<Chromosome> chromosomes = population.getChromosomes();
        for (int i=0; i<chromosomes.size(); i++) {
            indexedFitnessList.add(new Pair(fitness.calculateFitness(chromosomes.get(i)), i));
        }
        // Sort indexedFitnessList by fitness
        Collections.sort(indexedFitnessList, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return Double.compare(p2.getFirst(), p1.getFirst()); // Descending order
            }
        });

        ArrayList<Chromosome> sortedChromosomes = new ArrayList<>(chromosomes.size());
        for (int i=0; i<indexedFitnessList.size(); i++) {
            sortedChromosomes.add(chromosomes.get(indexedFitnessList.get(i).getSecond()));
        }
        chromosomes = sortedChromosomes;
    }

    /**
     * NOTE: can be done in a more efficient manner.
     * Finds average hamming distance between every Chromosome in the Population
     * @return average hamming distance
     */
    public double calculateDiversity() {
        if (chromosomes.isEmpty()) {
            System.err.println("NO CHROMOSOMES");
            return Double.NaN;
        }
        double sum = 0.0;
        for (int i=0; i<chromosomes.size(); i++) {
            for (int j=i+1; j<chromosomes.size(); j++) {
                sum += chromosomes.get(i).hammingDistance(chromosomes.get(j));
            }
        }
        int compareCount = (int)(chromosomes.size() / 2.0 * (chromosomes.size()-1));
        return (sum / compareCount) / chromosomes.getFirst().getLength();
    }

    /**
     * Finds the average fitness of the entire population.
     * @param fitness the method by which fitness is determined
     * @return the average fitness
     */
    public double calculateAverageFitness(Fitness fitness) {
        double totalFitness = 0.0;
        for (Chromosome chrom : chromosomes) {
            totalFitness += fitness.calculateFitness(chrom);
        }
        return totalFitness / chromosomes.size();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("Population with %d chromosomes:\n\n", chromosomes.size()));

        for (Chromosome c : chromosomes) {
            builder.append(c.toString() + "\n");
        }

        return builder.toString();
    }

    public int getSize() {
        return chromosomes.size();
    }

    /**
     * Adds the specified chromosomes to the population.  Useful for introducing elites after mutation has been applied
     * to others.
     * @param chromosomesToAdd Chromosomes that will be appended to chromosomes
     */
    public void addChromosomes(ArrayList<Chromosome> chromosomesToAdd) {
        chromosomes.addAll(chromosomesToAdd);
    }

    /**
     * This may not be a good design, as it means things are operating on the population outside of itself.
     * @return
     */
    public ArrayList<Chromosome> getChromosomes() {
        return this.chromosomes;
    }

    public void setChromosomes(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    /**
     * @return the Random object used by all constituent chromosomes
     */
    public Random getRandom() {
        return this.random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
