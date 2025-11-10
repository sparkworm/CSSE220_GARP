package simulation;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PopulationTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConstructPopulation() {
        Population pop1 = new Population(10, 20, 42L);
        Population pop2 = new Population(10, 20, 42L);

        System.out.println(pop1);

        assertEquals(pop1.toString(), pop2.toString());
    }

    public void testMutateChromosomes() {
    }

    public void testCalculateDiversity() {
        ArrayList<Chromosome> chromList = new ArrayList<>();

        chromList.add(new Chromosome("000001"));
        chromList.add(new Chromosome("000011"));

        Population pop3 = new Population(chromList);

        double expected = 1.0 / 2 / 6;

        assertEquals(expected, pop3.calculateDiversity());

        chromList.add(new Chromosome("000001"));
        chromList.add(new Chromosome("000011"));

        expected = (1.0 + 1.0 + 1.0 + 1.0) / 4 / 6;

        pop3 = new Population(chromList);

        assertEquals(expected, pop3.calculateDiversity());
    }
}