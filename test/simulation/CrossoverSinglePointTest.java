package simulation;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

public class CrossoverSinglePointTest extends TestCase {
    CrossoverSinglePoint crossover;


    public void setUp() {
        this.crossover = new CrossoverSinglePoint(new Random(0L));
    }

    public void testNonAssertive() {
        ArrayList<Chromosome> chromArray = new ArrayList<>(4);
        chromArray.add(new Chromosome("11111111"));
        chromArray.add(new Chromosome("00000000"));
        chromArray.add(new Chromosome("10101010"));
        chromArray.add(new Chromosome("01010101"));

        ArrayList<Chromosome> popOf4 = crossover.repopulate(chromArray, 4);
        ArrayList<Chromosome> popOf8 = crossover.repopulate(chromArray, 8);

        System.out.println(popOf4);
        System.out.println(popOf8);
    }

    public void testFindSplicePoint() {
        crossover.setMargin(0.0);

        assertEquals(0, crossover.findSplicePoint(100, 0));
        assertEquals(50, crossover.findSplicePoint(100, 0.5));
        assertEquals(100, crossover.findSplicePoint(100, 1.0));

        crossover.setMargin(0.2);

        assertEquals(20, crossover.findSplicePoint(100, 0));
        assertEquals(50, crossover.findSplicePoint(100, 0.5));
        assertEquals(80, crossover.findSplicePoint(100, 1.0));
    }
}
