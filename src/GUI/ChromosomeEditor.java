package GUI;

import simulation.Chromosome;
import utility.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

public class ChromosomeEditor extends JFrame {

    private Chromosome chromosome;
    private ChromosomeComponent chromosomeComponent;
    private JTextField mutationField;
    private JLabel statusLabel;

    public ChromosomeEditor() {
        this.setTitle("Chromosome Editor");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.statusLabel = new JLabel("Chromosome (not saved)");
        this.add(this.statusLabel, BorderLayout.NORTH);

        this.chromosome = new Chromosome(100, true);
        this.chromosomeComponent = new ChromosomeComponent(this.chromosome);

        JPanel buttonPanel = new JPanel();

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadChromosome();
            }
        });
        buttonPanel.add(loadButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveChromosome();
            }
        });
        buttonPanel.add(saveButton);

        buttonPanel.add(new JLabel("Mutation Rate:"));
        this.mutationField = new JTextField("0.01", 5);
        buttonPanel.add(this.mutationField);


        JButton mutateButton = new JButton("Mutate");
        mutateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mutateChromosome();
            }
        });
        buttonPanel.add(mutateButton);

        this.add(this.chromosomeComponent, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.chromosomeComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rows_cols = (int) Math.sqrt(chromosome.getLength());
                int col = e.getX() / chromosomeComponent.getPixelSize();
                int row = e.getY() / chromosomeComponent.getPixelSize();
                int index = row * rows_cols + col;

                if (index < chromosome.getLength()) {
                    boolean old = chromosome.getBit(index);
                    chromosomeComponent.getChromosome().setBit(index, !old);
                    chromosomeComponent.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        this.setVisible(true);

        this.pack();
    }

    private void loadChromosome() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (FileIO.readChromosomeFromFile(chooser.getSelectedFile()) != null) {
                this.chromosome = FileIO.readChromosomeFromFile(chooser.getSelectedFile());
                this.chromosomeComponent.setChromosome(this.chromosome);
                System.out.println("Chromosome loaded!");
                this.statusLabel.setText("Loaded: " + chooser.getSelectedFile().getName());
                this.pack();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to load chromosome",
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveChromosome() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileIO.writeChromosomeToFile(this.chromosome, chooser.getSelectedFile());
            System.out.println("Chromosome saved!");
            this.statusLabel.setText("Saved: " + chooser.getSelectedFile().getName());

        }
    }

    private void mutateChromosome() {
        try {
            double rate = Double.parseDouble(this.mutationField.getText());

            chromosome.mutate(rate);
//            chromosomeComponent.getChromosome().mutate(rate);
            chromosomeComponent.repaint();
            System.out.println("Chromosome mutated with rate: " + rate);
            this.statusLabel.setText("Mutated (not saved)");  // ADD THIS LINE

        } catch (NumberFormatException e) {
            System.err.println("Invalid mutation rate: " + this.mutationField.getText());
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number between 0 and 1",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
//        chromosomeComponent.getChromosome().randomizeGenotype(0.5);
        chromosomeComponent.repaint();
        System.out.println("Chromosome mutated!");
    }


    // 4. Add components to the window


    // We will add more functionality here for other tasks...

    /**
     * The main method to launch the editor.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChromosomeEditor::new);
    }
}
