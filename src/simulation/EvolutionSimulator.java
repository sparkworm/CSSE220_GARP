package simulation;

import GUI.FitnessPlotComponent;

import java.util.ArrayList;

/**
 * Class responsible for simulating the steps of evolution of a population.
 */
public class EvolutionSimulator {
    public static final double CROSSOVER_DEFAULT_MARGIN = 0.1;

    private int generation;
    private boolean crossoverActive;
    private double surviveRatio;
    private FitnessPlotComponent plot;

    /**
     * The number of top individuals that will not undergo mutation.
     * MUST NOT EXCEED THE SIZE OF POPULATION
     */
    private int eliteSurvivors;
    private Population population;
    private Fitness fitness;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;

    public EvolutionSimulator(int genotypeSize, int populationSize, int eliteSurvivors, double mutationRate, double surviveRatio, long seed) {
        this.generation = 0;
        this.crossoverActive = false; // change when crossover is more stable
        this.surviveRatio = surviveRatio;
        this.eliteSurvivors = eliteSurvivors;
        this.population = new Population(populationSize, genotypeSize, seed); // create random population of specified size
        this.fitness = new FitnessMaxOnes();  // TODO: replace with real fitness function
        this.selection = new SelectionTruncation(fitness);
        this.crossover = new CrossoverDuplicate();
        this.mutation = new Mutation(mutationRate);
    }

    public EvolutionSimulator(int genotypeSize, int populationSize, int eliteSurvivors, double mutationRate, double surviveRatio) {
        this.generation = 0;
        this.crossoverActive = false; // change when crossover is more stable
        this.surviveRatio = surviveRatio;
        this.eliteSurvivors = eliteSurvivors;
        this.population = new Population(populationSize, genotypeSize); // create random population of specified size
        this.fitness = new FitnessMaxOnes();  // TODO: replace with real fitness function
        this.selection = new SelectionTruncation(fitness);
        this.crossover = new CrossoverDuplicate();
        this.mutation = new Mutation(mutationRate);
    }

    /**
     * Runs the simulation for the provided number of generations.  <br>
     * May break out if the end condition is reached sooner
     *
     * @param numGenerations the number of generations that this simulation will be run for.  Should be at least 0.
     */
    public void runSimulation(int numGenerations) {
        if (plot != null) plot.clear();
        for (int i = 0; i < numGenerations; i++) {
            simulateGeneration();
        }
    }

    /**
     * Simulates the Selection, Crossover, and Mutation of a single generation.  <br>
     * Modifies Population such that the new value is the next generation.  <br>
     * Increments generation.
     */
    public void simulateGeneration() {
        System.out.printf("Generation %d of fitness %.2f:  %s%n",
                generation,
                population.calculateAverageFitness(fitness),
//                population.toString()));
                "");
        generation++;
        // Create new population from the fittest
        ArrayList<Chromosome> survivors = selection.makeSelection(population.getChromosomes(), surviveRatio);
        ArrayList<Chromosome> elites = new ArrayList<>(eliteSurvivors);
        for (int i = 0; i < eliteSurvivors; i++) { // works because survivors is in descending order
            elites.add(new Chromosome(survivors.getFirst()));
            //survivors.removeFirst();
        }
        System.out.println("Fittest Chromosome: " + fitness.calculateFitness(elites.get(0)));// + "\n\n");
        ArrayList<Chromosome> newPop = crossover.repopulate(survivors, population.getSize() - eliteSurvivors);
        //newPop.addAll(elites);
//        population = new Population(newPop);
        population.setChromosomes(newPop);
        // Mutate new population
        mutation.mutateChromosomes(population.getChromosomes());
        population.addChromosomes(elites);
        if (plot != null) {
            double best  = Double.NEGATIVE_INFINITY;
            double worst = Double.POSITIVE_INFINITY;
            double sum   = 0.0;

            for (Chromosome c : population.getChromosomes()) {
                double f = fitness.calculateFitness(c);
                if (f > best)  best = f;
                if (f < worst) worst = f;
                sum += f;
            }
            double avg = sum / population.getSize();

            plot.addData(generation, best, avg, worst);
        }
    }

    public Population getPopulation() {
        return population;
    }

    public Fitness getFitness() {
        return fitness;
    }

    public int getGeneration() {
        return generation;
    }
    public void attachPlot(FitnessPlotComponent plot) {
        this.plot = plot;
        if (this.plot != null) this.plot.clear();
    }

    public void setMutationRate(double mutationRate) {
        mutation.setMutationRate(mutationRate);
    }

    /**
     * Changes crossover between single-point and duplicate (which is not deemed to truly be crossover, although it
     * interfaces the same.)
     * @param is_crossover
     */
    public void changeCrossover(boolean is_crossover) {
        if (is_crossover) this.crossover = new CrossoverSinglePoint(population.random, CROSSOVER_DEFAULT_MARGIN);
        else this.crossover = new CrossoverDuplicate();
    }

    /**
     * Sets the margin of the Crossover.  This doesn't do anything with CrossoverDuplicate.
     * @param margin
     */
    public void setCrossoverMargin(double margin) {
        this.crossover.setMargin(margin);
    }

    public void setSelectionType(SelectionType type) {
        switch (type) {
            case SelectionType.TRUNCATION -> this.selection = new SelectionTruncation(this.fitness);
            case SelectionType.ROULETTE -> this.selection = new SelectionRoulette(this.fitness, this.population.getRandom());
            case SelectionType.RANKED -> this.selection = new SelectionRanked(this.fitness, this.population.getRandom());
        }
    }
}
