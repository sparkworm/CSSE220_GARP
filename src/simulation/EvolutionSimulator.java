package simulation;

/**
 * Class responsible for simulating the steps of evolution of a population.
 */
public class EvolutionSimulator {
    private int generation;
    private boolean crossoverActive;
    private double surviveRatio;
    private Population population;
    private Fitness fitness;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;

    public EvolutionSimulator(int genotypeSize, int populationSize, double mutationRate, double surviveRatio) {
        this.generation = 0;
        this.crossoverActive = false; // change when crossover is more stable
        this.surviveRatio = surviveRatio;
        this.population = new Population(populationSize, genotypeSize); // create random population of specified size
        this.fitness = new FitnessMaxOnes();  // TODO: replace with real fitness function
        this.selection = new Selection(fitness);
        this.crossover = new Crossover();  // TODO: implement real crossover for when crossoverActive==true
        this.mutation = new Mutation(mutationRate);
    }

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
        generation++;
        // Create new population from the fittest
        population = new Population(crossover.repopulate(selection.trunctationSelection(population.getChromosomes(), surviveRatio), population.getSize()));
        // Mutate new population
        mutation.mutateChromosomes(population.getChromosomes());
        System.out.println(String.format("Population, generation %d:  %s", generation, population.toString()));
    }

    public void setMutationRate(double mutationRate) {
        mutation.setMutationRate(mutationRate);
    }
}
