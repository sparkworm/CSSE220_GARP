package simulation;

import junit.framework.TestCase;

public class EvolutionSimulatorTest extends TestCase {

    // TODO: add assertions
    public void testRunSimulation() {
        EvolutionSimulator simulator = new EvolutionSimulator(20, 10, 0.05, 0.3);
        simulator.runSimulation(100);
    }

    // TODO: implement
    public void testSimulateGeneration() {

    }
}