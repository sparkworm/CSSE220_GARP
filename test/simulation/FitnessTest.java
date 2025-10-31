package simulation;

import junit.framework.TestCase;

public class FitnessTest extends TestCase {

    public void testMaxOnes() {
        FitnessMaxOnes maxOnes = new FitnessMaxOnes();
        Chromosome chrom = new Chromosome(100, false);
        assertEquals(0.0, maxOnes.calculateFitness(chrom));
        chrom.randomizeGenotype(1.0); // make all values 1
        assertEquals(100.0, maxOnes.calculateFitness(chrom));
        chrom.setBit(0,false);
        assertEquals(99.0, maxOnes.calculateFitness(chrom));
    }

    public void testMatchTarget() {
        Chromosome chrom1 = new Chromosome("0001000000");
        Chromosome chrom2 = new Chromosome("0000000100");

        FitnessMatchTarget match = new FitnessMatchTarget(chrom1);


        assertEquals(10.0, match.calculateFitness(chrom1));
        assertEquals(8.0, match.calculateFitness(chrom2));

        chrom2.setBit(3, true);
        assertEquals(9.0, match.calculateFitness(chrom2));
    }

    public void testConsecutiveOnes() {
        Chromosome chrom = new Chromosome(100, false);
        FitnessMaxConsecutiveOnes consecutiveOnes = new FitnessMaxConsecutiveOnes();

        assertEquals(0.0, consecutiveOnes.calculateFitness(chrom));

        chrom.randomizeGenotype(1.0);
        assertEquals(100.0, consecutiveOnes.calculateFitness(chrom));

        chrom.setBit(0,false);
        assertEquals(99.0, consecutiveOnes.calculateFitness(chrom));
        chrom.setBit(0,true);

        chrom.setBit(50, false);
        assertEquals(50.0, consecutiveOnes.calculateFitness(chrom));
    }

}
