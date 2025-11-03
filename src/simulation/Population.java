package simulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class responsible for managing the set of Chromosomes being evolved.
 * NOTE: design is a bit sketchy at the moment; the existence of getChromosomes() function may be a bad sign.
 */
public class Population {
    ArrayList<Chromosome> chromosomes;

    public Population(int numChromosomes, int chromosomeSize) {
        chromosomes = new ArrayList<>(numChromosomes);
        for (int i=0; i<numChromosomes; i++) {
            chromosomes.add(new Chromosome(chromosomeSize, true));
        }
    }
    public Population(int numChromosomes, int chromosomeSize, long seed) {
        chromosomes = new ArrayList<>(numChromosomes);
        Random random = new Random(seed);
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
     * TODO: IMPLEMENT
     * Finds average hamming distance between every Chromosome in the Population
     * @return average hamming distance
     */
    public double calculateDiversity() {
        System.err.println("calculateDiversity not yet implemented");
        return Double.NaN;
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
     * This may not be a good design, as it means things are operating on the population outside of itself.
     * @return
     */
    public ArrayList<Chromosome> getChromosomes() {
        return this.chromosomes;
    }
}
