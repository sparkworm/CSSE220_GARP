package simulation;

import junit.framework.TestCase;

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
    }
}