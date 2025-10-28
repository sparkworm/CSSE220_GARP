package simulation;

import java.util.BitSet;
import java.util.Random;

/**
 * Class responsible for storing a genotype and reading/writing to it.
 */
public class Chromosome {
    Random random;
    /**
     * The length of genotype is stored in this field since BitSet.length() will differ depending on what is stored: if
     * empty, BitSet.length() will simply be 0
     */
    private int length;
    private BitSet genotype;

    /**
     * Create a Chromosome with the specified genotype.  Mostly for debugging, as there will not really be a way to
     * access genotypes outside Chromosome.
     * @param length the length of the Chromosome's genotype in bits
     * @param genotype the specific genotype that this Chromosome will possess
     */
    public Chromosome(int length, BitSet genotype) {
        this.random = new Random();
        this.length = length;
        this.genotype = genotype;
    }

    /**
     * Create a Chromosome with a genotype of specified size.  All bits are initially false.
     * @param length the length of the Chromosome's genotype in bits
     */
    public Chromosome (int length, boolean randomize) {
        this.random = new Random();
        this.length = length;
        this.genotype = new BitSet(length);
        if (randomize) {
            randomizeGenotype(0.5); // use default value of 0.5
        }
    }

    /**
     * Creates a Chromosome with a specified random object, presumably so that the same random could be shared by
     * multiple Chromosomes for a seeded population.
     * <br>NOTE: the current implementation will also use the specified Random for mutation, so evolution will also be
     * deterministic.
     * @param length the number of bits in the genome
     * @param random a random object used for randomizing the Chromosome's genotype.
     */
    public Chromosome (int length, Random random) {
        this.random = random;
        this.length = length;
        this.genotype = new BitSet(length);
        randomizeGenotype(0.5);
    }

    /**
     * Create a Chromosome from a String, where 1 correlates to a true bit at its index
     * @param strGenotype the String of 1s and 0s representing a genotype
     */
    public Chromosome (String strGenotype) {
        this.random = new Random();
        this.length = strGenotype.length();
        this.genotype = new BitSet(strGenotype.length());
        for (int i=0; i<length; i++) {
            setBit(i, strGenotype.charAt(i)=='1');
        }
    }

    /**
     * TODO: add exception handling for out-of-bounds setting/getting <br>
     * Sets the bit at the specified index to the specified value
     * @param idx index of stored bit
     * @param val value (false==0, true==1)
     */
    public void setBit(int idx, boolean val) {
        genotype.set(idx, val);
    }

    /**
     * TODO: add exception handling for out-of-bounds setting/getting <br>
     * Get the value at a specific index
     * @param idx index of requested bit
     * @return true if the bit at idx is 1, false otherwise
     */
    public boolean getBit(int idx) {
        return genotype.get(idx);
    }

    /**
     * Goes through each bit, flipping them with a frequency of flipChance
     * @param flipChance chance of flipping any one bit: 0.0 - 1.0
     */
    public void randomizeGenotype(double flipChance) {
        for (int i=0; i<length; i++) {
            if (flipChance > random.nextDouble()) {
                genotype.flip(i);
            }
        }
    }

    /**
     * Will return an array of BitSet, with each BitSet storing fragSize bits.  Any remainder of length/fragSize is
     * discarded.
     * @param fragSize the number of bits in each BitSet in the array
     * @return an array of BitSet, each storing a fragment of the genotype of length fragSize
     */
    public BitSet[] getGenotypeFragments(int fragSize) {
        BitSet[] fragments = new BitSet[length/fragSize];
        // traverse fragments in
        //for (int i=fragSize; i<=length; i+=fragSize) {
        for (int i=0; i<fragments.length; i++) {
            fragments[i] = genotype.get(i*fragSize, i*fragSize+fragSize);
        }
        return fragments;
    }

    /**
     * Mutates the chromosome with the given mutation rate
     * @param mutationRate probability of each bit flipping (0.0 to 1.0)
     */
    public void mutate(double mutationRate) {
        randomizeGenotype(mutationRate);
    }

    public String toString() {
        return "Chromosome with genotype of length: " + length + "\n" + genotypeString();
    }

    /**
     * Produce a string representing the genotype, using 1s and 0s.
     * @param lineLength the length that the line will go before wrapping onto a newline.  If this is -1, then
     *                   no wrapping will be performed.
     * @return string representing the genotype, composed of 1s and 0s
     */
    public String genotypeString(int lineLength) {
        // if this is true, that means that the output is wanted on one line only, hence a newline should never be added
        if (lineLength == -1) lineLength = length + 1;

        StringBuilder builder = new StringBuilder(length);
        for (int i=0; i<length; ++i) {
            if (i%lineLength==0 && i!=0) builder.append("\n");
            builder.append((genotype.get(i)) ? "1" : "0");
        }
        return builder.toString();
    }
    public String genotypeString() {
        return genotypeString(10);
    }

    public int getLength() {
        return this.length;
    }
}
