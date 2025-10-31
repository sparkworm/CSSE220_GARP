package simulation;

import junit.framework.TestCase;
import simulation.Chromosome;

import java.util.BitSet;

public class ChromosomeTest extends TestCase {

    Chromosome chrom1;
    Chromosome chrom2;

    public void setUp() throws Exception {
        super.setUp();
        chrom1 = new Chromosome("0010101010"); // length 10
        chrom2 = new Chromosome("10011010"); // length 8
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSetAndGetBit() {
        chrom1.setBit(0,true); // 0 to 1
        chrom2.setBit(0,false); // 1 to 1
        chrom1.setBit(9, false); // 0 to 0
        chrom2.setBit(6, true); // 1 to 1

        assertTrue(chrom1.getBit(0));
        assertFalse(chrom2.getBit(0));
        assertFalse(chrom1.getBit(9));
        assertTrue(chrom2.getBit(6));
    }

    public void testRandomizeGenotype() {
        chrom1.randomizeGenotype(0.0); // flip nothing
        chrom2.randomizeGenotype(1.0); // flip everything

        assertEquals(chrom1.genotypeString(-1), "0010101010");
        assertEquals(chrom2.genotypeString(-1), "01100101");
    }

    public void testGetGenotypeFragments() {
        BitSet[] fragments1 = chrom1.getGenotypeFragments(2);
        BitSet[] fragments2 = chrom2.getGenotypeFragments(2);
        BitSet[] fragments3 = chrom1.getGenotypeFragments(3);

        assertEquals(fragments1[2], fragments2[2]);
        assertTrue(fragments1[1].get(0));
        assertFalse(fragments2[3].get(1));
        assertEquals(fragments1.length, 5);
        assertEquals(fragments2.length, 4);
        assertEquals(fragments3.length, 3);
    }

    public void testToString() {
        assertEquals(chrom1.toString(), "Chromosome with genotype of length: 10\n0010101010");
        assertEquals(chrom2.toString(), "Chromosome with genotype of length: 8\n10011010");
    }

    public void testGenotypeString() {
        assertEquals(chrom1.genotypeString(-1), "0010101010");
        assertEquals(chrom1.genotypeString(5), "00101\n01010");
        assertEquals(chrom1.genotypeString(2), "00\n10\n10\n10\n10");
    }

    public void testHammingDistance() {
        assertEquals(0, chrom1.hammingDistance(chrom1));
        Chromosome newChrom = new Chromosome(chrom1.genotypeString(-1));
        newChrom.mutate(1.0);
        assertEquals(chrom1.getLength(), newChrom.hammingDistance(chrom1));
    }
}