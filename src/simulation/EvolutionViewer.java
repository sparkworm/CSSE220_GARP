
package simulation;

import GUI.FitnessPlotComponent;
import GUI.PathPhenotypePanel;
import GUI.PopulationViewerComponent;
import simulation.pathfinding.PathPhenotype;
import utility.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private PopulationViewerComponent populationViewer = new PopulationViewerComponent(); // ← ADD THIS
    private PathPhenotypePanel pathPhenotypePanel = new PathPhenotypePanel();

    private JButton startStopButton = new JButton("Start Evolution");
    private JButton startButton = new JButton("Start Evolution");
    private JButton pauseButton = new JButton("Pause");
    private JButton stopButton = new JButton("Stop");
    private JButton saveButton;


    private JTextField mutationField;
    private JComboBox<String> selectionCombo;
    private JCheckBox crossoverCheck;
    private JTextField popSizeField;
    private JTextField genField;
    private JTextField genomeLengthField;
    private JTextField elitismField;

    public EvolutionViewer() {
        setTitle("Evolution Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        controlPanel.add(new JLabel("Mutation Rate:"));
//        controlPanel.add(new JTextField("1.0", 5));
//        controlPanel.add(new JLabel("Selection:"));
//        controlPanel.add(new JComboBox<>(new String[]{"Truncation"}));
//        controlPanel.add(new JCheckBox("Crossover?"));
//        controlPanel.add(new JLabel("Population Size:"));
//        controlPanel.add(new JTextField("100", 5));
//        controlPanel.add(new JLabel("Generations:"));
//        controlPanel.add(new JTextField("101", 5));
//        controlPanel.add(new JLabel("Genome Length:"));
//        controlPanel.add(new JTextField("100", 5));
//        controlPanel.add(new JLabel("Elitism %:"));
//        controlPanel.add(new JTextField("0", 3));
//        controlPanel.add(startStopButton);
//
//        add(fitnessPlot, BorderLayout.CENTER);
//        add(controlPanel, BorderLayout.SOUTH);
//
//        startStopButton.addActionListener(e -> toggleSimulation());
//
//        // Timer to run the simulation
//        timer = new Timer(50, e -> runOneGeneration());
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
        controlPanel.add(new JLabel("Mutation Rate (N/pop):"));
        mutationField = new JTextField("1.0", 5);
        controlPanel.add(mutationField);

        controlPanel.add(new JLabel("Selection:"));
        selectionCombo = new JComboBox<>(new String[]{"Truncation", "Roulette", "Ranked"});
        controlPanel.add(selectionCombo);

        crossoverCheck = new JCheckBox("Crossover?", true);
        controlPanel.add(crossoverCheck);

        controlPanel.add(new JLabel("Population Size:"));
        popSizeField = new JTextField("100", 5);
        controlPanel.add(popSizeField);

        controlPanel.add(new JLabel("Generations:"));
        genField = new JTextField("101", 5);
        controlPanel.add(genField);

        controlPanel.add(new JLabel("Genome Length:"));
        genomeLengthField = new JTextField("100", 5);
        controlPanel.add(genomeLengthField);

        controlPanel.add(new JLabel("Elitism %:"));
        elitismField = new JTextField("0", 3);
        controlPanel.add(elitismField);

        saveButton = new JButton("Save Data");
        saveButton.addActionListener(e -> fitnessPlot.saveDataToFile());

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(saveButton);

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);

        JPanel populationAndPhenoPanel = new JPanel();
        populationAndPhenoPanel.add(populationViewer, BorderLayout.NORTH);
        populationAndPhenoPanel.add(pathPhenotypePanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(populationAndPhenoPanel);
        scrollPane.setPreferredSize(new Dimension(600, 600));


        // Add components to the frame
        add(fitnessPlot, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
//        add(populationAndPhenoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        // Button action
        startButton.addActionListener(e -> startEvolution());
        pauseButton.addActionListener(e -> pauseEvolution());
        stopButton.addActionListener(e -> stopEvolution());
        saveButton.addActionListener(e -> saveData());

        // Timer to run the simulation
        timer = new Timer(50, e -> runOneGeneration());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startEvolution() {
        if (!timer.isRunning()) {
            // If no history, initialize fresh simulation
            if (bestHistory.isEmpty()) {
                initializeSimulation();
            }

            // Start the timer
            timer.start();

            // Update button states
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
        }
    }

    private void pauseEvolution() {
        if (timer.isRunning()) {
            timer.stop();

            // Update button states
            startButton.setEnabled(true);  // Can resume
            pauseButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void saveData() {
        if (bestHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No data to save! Run evolution first.",
                    "No Data",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        fitnessPlot.saveDataToFile();

        JOptionPane.showMessageDialog(this,
                "Data saved to fitness_data.csv",
                "Save Successful",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopEvolution() {
        // Stop timer
        timer.stop();

        // Clear all data
        bestHistory.clear();
        avgHistory.clear();
        lowHistory.clear();
        fitnessPlot.clear();
        populationViewer.clear();

        // Update button states
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }
//    private void toggleSimulation() {
//        if (timer.isRunning()) {
//            timer.stop();
//            startStopButton.setText("Resume Evolution");
//        }

    /// /        else {
    /// /            if (bestHistory.isEmpty()) {
    /// /                initializeSimulation();
    /// /            }
//            else {
//                // Check if we need to start fresh
//                if (bestHistory.isEmpty() || startStopButton.getText().equals("Start Over")) {
//                    initializeSimulation();  // ← Start fresh!
//                }
//            timer.start();
//            startStopButton.setText("Pause Evolution");
//        }
//    }
    private void initializeSimulation() {
        // Clear old data
        bestHistory.clear();
        avgHistory.clear();
        lowHistory.clear();
        fitnessPlot.clear();
        populationViewer.clear();

        int popSize = Integer.parseInt(popSizeField.getText());
        int genomeLength = Integer.parseInt(genomeLengthField.getText());
        double mutRate = Double.parseDouble(mutationField.getText()) / popSize;
        mutation.setMutationRate(mutRate);

        String selectionType = (String) selectionCombo.getSelectedItem();
        switch (selectionType) {
            case "Truncation":
                selection = new SelectionTruncation(fitnessFunction);
                break;
            case "Roulette":
                selection = new SelectionRoulette(fitnessFunction, random);
                break;
            case "Ranked":
                selection = new SelectionRanked(fitnessFunction, random);
                break;
        }

        population = new Population(popSize, genomeLength);
        analyzeAndPlot();

    }

    private void runOneGeneration() {

        int Maximum_genr = Integer.parseInt(genField.getText());
        int elite_Count = Integer.parseInt(elitismField.getText());

        int numElites = (int)(population.getSize() * (Double.parseDouble(elitismField.getText()) / 100));
        population.sortChromosomesByFitness(fitnessFunction);

        ArrayList<Chromosome> elites = new ArrayList<>(numElites);
        for (int i=0; i<numElites; i++) {
            elites.add(population.getChromosomes().getFirst());
            population.getChromosomes().removeFirst(); // possibly bad efficiency
        }

        ArrayList<Chromosome> parents = selection.makeSelection(population.getChromosomes(), 0.5);

        ArrayList<Chromosome> next_Generation;
        // Polymorphism should make this check unnecessary
        if (crossoverCheck.isSelected()) {
            next_Generation = crossover.repopulate(parents, population.getSize());
        } else {
            Crossover duplicator = new CrossoverDuplicate();
            next_Generation = duplicator.repopulate(parents, population.getSize());
        }

        population.setChromosomes(next_Generation);

        mutation.mutateChromosomes(population.getChromosomes());

        // Add elites back
        population.addChromosomes(elites);

         analyzeAndPlot();

        if (bestHistory.size() >= Maximum_genr) {
            timer.stop();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void analyzeAndPlot() {
        double best = 0, low = 100, sum = 0;
        Chromosome bestChromosome = population.getChromosomes().getFirst();
        for (Chromosome c : population.getChromosomes()) {
            double fitness = fitnessFunction.calculateFitness(c);
            if (fitness > best) {
                best = fitness;
                bestChromosome = c;
            }
            if (fitness < low) low = fitness;
            sum += fitness;
        }
        double avg = sum / population.getSize();

        bestHistory.add(best);
        avgHistory.add(avg);
        lowHistory.add(low);

        fitnessPlot.updateData(bestHistory, avgHistory, lowHistory);
        populationViewer.updatePopulation(population.getChromosomes(), bestHistory.size() - 1);
        pathPhenotypePanel.updateWithNewPhenotype(new PathPhenotype(bestChromosome));
        //System.out.println(new PathPhenotype(bestChromosome));
        pathPhenotypePanel.revalidate();
        pathPhenotypePanel.repaint();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EvolutionViewer::new);
    }
}
