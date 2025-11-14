
package simulation;

import GUI.FitnessPlotComponent;
import GUI.PathPhenotypePanel;
import GUI.PopulationViewerComponent;
import simulation.pathfinding.PathFitness;
import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;
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
//    private Fitness fitness_function = new FitnessMaxOnes();
    private Fitness fitness_function = new PathFitness(new TerrainGrid());
    private Selection selection = new SelectionTruncation(fitness_function);
    private Crossover crossover = new CrossoverSinglePoint(random);
    private Mutation mutation = new Mutation(0.01); 
    private Timer timer;

    private ArrayList<Double> best_history = new ArrayList<>();
    private ArrayList<Double> average_history = new ArrayList<>();
    private ArrayList<Double> lowHistory = new ArrayList<>();
    private ArrayList<Double> diversityHistory = new ArrayList<>();

    private FitnessPlotComponent fitnessPlot = new FitnessPlotComponent();
    private PopulationViewerComponent populationViewer = new PopulationViewerComponent();
    private PathPhenotypePanel pathPhenotypePanel = new PathPhenotypePanel();

//    private JButton startStopButton = new JButton("Start Evolution");
    private JButton startButton = new JButton("Start Evolution");
    private JButton pauseButton = new JButton("Pause");
    private JButton stopButton = new JButton("Stop");
    private JButton saveButton;
    
    private JTextField mutationField;
    private JComboBox<String> selection_types;
    private JCheckBox crossoverCheck;
    private JTextField population_sizefield;
    private JTextField generation_field;
    private JTextField genome_lengthfield;
    private JTextField elitism_field ;

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
        controlPanel.add(new JLabel("Mutation Rate:"));
        mutationField = new JTextField("1.0", 5);
        controlPanel.add(mutationField);

        controlPanel.add(new JLabel("Selection:"));
        selection_types = new JComboBox<>(new String[]{"Truncation", "Roulette", "Ranked"});
        controlPanel.add(selection_types);

        crossoverCheck = new JCheckBox("Crossover", true);
        controlPanel.add(crossoverCheck);

        controlPanel.add(new JLabel("Population Size:"));
        population_sizefield = new JTextField("100", 5);
        controlPanel.add(population_sizefield);

        controlPanel.add(new JLabel("Generations:"));
        generation_field = new JTextField("101", 5);
        controlPanel.add(generation_field);

        controlPanel.add(new JLabel("Genome Length:"));
        genome_lengthfield = new JTextField("100", 5);
        controlPanel.add(genome_lengthfield);

        controlPanel.add(new JLabel("Elitism %:"));
        elitism_field  = new JTextField("0", 3);
        controlPanel.add(elitism_field );

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

        add(fitnessPlot, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
//        add(populationAndPhenoPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startEvolution());
        pauseButton.addActionListener(e -> pauseEvolution());
        stopButton.addActionListener(e -> stopEvolution());
        saveButton.addActionListener(e -> saveData());

        // Timer 
        timer = new Timer(50, e -> runOneGeneration());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startEvolution() {
        if (!timer.isRunning()) {
            if (best_history.isEmpty()) {
                initializeSimulation();
            }
            // Start the timer
            timer.start();
            // Update button 
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
        }
    }

    private void pauseEvolution() {
        if (timer.isRunning()) {
            timer.stop();

            // Update button states
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void saveData() {
        if (best_history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to save! Run evolution first.", "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        fitnessPlot.saveDataToFile();
        JOptionPane.showMessageDialog(this, "Data saved to fitness_data.csv", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopEvolution() {
        // Stop timer
        timer.stop();

        // Clear data
        best_history.clear();
        average_history.clear();
        lowHistory.clear();
        diversityHistory.clear();
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
    
//            else {
//                // Check if we need to start fresh
//                if (best_history.isEmpty() || startStopButton.getText().equals("Start Over")) {
//                    initializeSimulation();  //
//                }
//            timer.start();
//            startStopButton.setText("Pause Evolution");
//        }
//    }
    private void initializeSimulation() {
        // Clear old data
        best_history.clear();
        average_history.clear();
        lowHistory.clear();
        diversityHistory.clear();
        fitnessPlot.clear();
        populationViewer.clear();

        int popSize = Integer.parseInt(population_sizefield.getText());
        int genomeLength = Integer.parseInt(genome_lengthfield.getText());
        double mutRate = Double.parseDouble(mutationField.getText()) / popSize;
        mutation.setMutationRate(mutRate);

        String selectionType = (String) selection_types.getSelectedItem();
        switch (selectionType) {
            case "Truncation":
                selection = new SelectionTruncation(fitness_function);
                break;
            case "Roulette":
                selection = new SelectionRoulette(fitness_function, random);
                break;
            case "Ranked":
                selection = new SelectionRanked(fitness_function, random);
                break;
        }

        population = new Population(popSize, genomeLength);
        analyzeAndPlot();

    }

    private void runOneGeneration() {

        int Maximum_genr = Integer.parseInt(generation_field.getText());
//        int elite_Count = Integer.parseInt(elitism_field .getText());

        int numElites = (int)(population.getSize() * (Double.parseDouble(elitism_field .getText()) / 100));
        population.sortChromosomesByFitness(fitness_function);

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

        if (best_history.size() >= Maximum_genr) {
            timer.stop();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void analyzeAndPlot() {
        double best = 0;
        double low = 100;
        double sum = 0;
        Chromosome best_chromosome = population.getChromosomes().getFirst();
        for (Chromosome c : population.getChromosomes()) {
            double fitness = fitness_function.calculateFitness(c);
            if (fitness > best) {
                best = fitness;
                best_chromosome = c;
            }
            if (fitness < low) low = fitness;
            sum += fitness;
        }
        double average = sum / population.getSize();

        best_history.add(best);
        average_history.add(average);
        lowHistory.add(low);
        diversityHistory.add(population.calculateDiversity());

        fitnessPlot.updateData(best_history, average_history, lowHistory, diversityHistory);
        populationViewer.updatePopulation(population.getChromosomes(), best_history.size() - 1);
        pathPhenotypePanel.updateWithNewPhenotype(new PathPhenotype(best_chromosome));
        //System.out.println(new PathPhenotype(best_chromosome));
        pathPhenotypePanel.revalidate();
        pathPhenotypePanel.repaint();

    }

    public static void main(String[] args) {
        new EvolutionViewer();
    }
}
