package simulation;

import java.util.BitSet;

/**
 * Class responsible for storing a genotype and reading/writing to it.
 */
public class Chromosome {
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
        this.length = length;
        this.genotype = genotype;
    }

    /**
     * Create a Chromosome with a genotype of specified size.  All bits are initially false.
     * @param length the length of the Chromosome's genotype in bits
     */
    public Chromosome (int length, boolean randomize) {
        this.length = length;
        this.genotype = new BitSet(length);
        if (randomize) {
            randomizeGenotype(0.5); // use default value of 0.5
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
            double rand = Math.random();
            if (flipChance > Math.random()) {
                genotype.flip(i);
            }
        }
    }

    public String toString() {
        return "Chromosome with genotype of length: " + genotype.length() + "\n" + genotypeString();
    }

    /**
     * Produce a string representing the genotype, using 1s and 0s.
     * @param lineLength the length that the line will go before wrapping onto a newline.  Must be greater than 0
     * @return string representing the genotype, composed of 1s and 0s
     */
    public String genotypeString(int lineLength) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<length; ++i) {
            if (i%lineLength==0) builder.append("\n");
            builder.append((genotype.get(i)) ? "1" : "0");
        }
        return builder.toString();
    }
    public String genotypeString() {
        return genotypeString(10);
    }

}
