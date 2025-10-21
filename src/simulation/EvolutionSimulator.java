package simulation;

/**
 * Class responsible for simulating the steps of evolution of a population.
 */
public class EvolutionSimulator {
    private int generation;
    private boolean crossoverActive;
    private Population population;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private Fitness fitness;


    /**
     * Runs the simulation for the provided number of generations.  <br>
     * May break out if the end condition is reached sooner
     * @param numGenerations the number of generations that this simulatio will be run for.  Should be at least 0.
     */
    public void runSimulation(int numGenerations) {
        for (int i=0; i<numGenerations; i++) {
            simulateGeneration();
        }
    }

    /**
     * Simulates the Selection, Crossover, and Mutation of a single generation.  <br>
     * Modifies Population such that the new value is the next generation.  <br>
     * Increments generation.
     */
    public void simulateGeneration() {
        System.err.println("simulateGeneration not yet implemented");
    }
}
