
package simulation;

import GUI.FitnessPlotComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class EvolutionViewer extends JFrame {

    private Population population;
    private Random random = new Random();
    private Fitness fitnessFunction = new FitnessMaxOnes();
    private Selection selection = new SelectionTruncation(fitnessFunction);
    private Crossover crossover = new CrossoverSinglePoint(random);
    private Mutation mutation = new Mutation(0.01); // Default 1% mutation
    private Timer timer;

    private ArrayList<Double> bestHistory = new ArrayList<>();
    private ArrayList<Double> avgHistory = new ArrayList<>();
    private ArrayList<Double> lowHistory = new ArrayList<>();

    private FitnessPlotComponent fitnessPlot = new FitnessPlotComponent();
    private JButton startStopButton = new JButton("Start Evolution");

    public EvolutionViewer() {
        setTitle("Evolution Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Mutation Rate:"));
        controlPanel.add(new JTextField("1.0", 5));
        controlPanel.add(new JLabel("Selection:"));
        controlPanel.add(new JComboBox<>(new String[]{"Truncation"}));
        controlPanel.add(new JCheckBox("Crossover?"));
        controlPanel.add(new JLabel("Population Size:"));
        controlPanel.add(new JTextField("100", 5));
        controlPanel.add(new JLabel("Generations:"));
        controlPanel.add(new JTextField("101", 5));
        controlPanel.add(new JLabel("Genome Length:"));
        controlPanel.add(new JTextField("100", 5));
        controlPanel.add(new JLabel("Elitism %:"));
        controlPanel.add(new JTextField("0", 3));
        controlPanel.add(startStopButton);

        add(fitnessPlot, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        startStopButton.addActionListener(e -> toggleSimulation());

        // Timer to run the simulation
        timer = new Timer(50, e -> runOneGeneration());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void toggleSimulation() {
        if (timer.isRunning()) {
            timer.stop();
            startStopButton.setText("Resume Evolution");
        } else {
            if (bestHistory.isEmpty()) {
                initializeSimulation();
            }
            timer.start();
            startStopButton.setText("Pause Evolution");
        }
    }

    private void initializeSimulation() {
        // Clear old data
        bestHistory.clear();
        avgHistory.clear();
        lowHistory.clear();
        fitnessPlot.clear();

        // Create initial population
        population = new Population(100, 100);

        // Analyze and display generation 0
        analyzeAndPlot();
    }

    private void runOneGeneration() {
        // 1. SELECTION
        ArrayList<Chromosome> parents = selection.makeSelection(population.getChromosomes(), 0.5); // Keep top 50%
        // 2. CROSSOVER
        ArrayList<Chromosome> offspring = crossover.repopulate(parents, population.getSize());
        // 3. MUTATION
        mutation.mutateChromosomes(offspring);

        population.setChromosomes(offspring);

        analyzeAndPlot();

        if (bestHistory.size() >= 101) { // Stop after 101 generations
            timer.stop();
            startStopButton.setText("Start Over");
        }
    }

    private void analyzeAndPlot() {
        double best = 0, low = 100, sum = 0;
        for (Chromosome c : population.getChromosomes()) {
            double fitness = fitnessFunction.calculateFitness(c);
            if (fitness > best) best = fitness;
            if (fitness < low) low = fitness;
            sum += fitness;
        }
        double avg = sum / population.getSize();

        bestHistory.add(best);
        avgHistory.add(avg);
        lowHistory.add(low);

        fitnessPlot.updateData(bestHistory, avgHistory, lowHistory);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EvolutionViewer::new);
    }
}
