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

    public ChromosomeEditor() {
        this.setTitle("Chromosome Editor");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // this.setLayout(new BorderLayout());
        this.chromosome = new Chromosome(100, true);
        this.chromosomeComponent = new ChromosomeComponent(this.chromosome);
        this.add(this.chromosomeComponent, BorderLayout.NORTH);

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

        JButton mutateButton = new JButton("Mutate");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mutateChromosome();
            }
        });
        buttonPanel.add(mutateButton);
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
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        this.pack();
    }
    private void loadChromosome() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.chromosome = FileIO.readChromosomeFromFile(chooser.getSelectedFile());
            this.chromosomeComponent.setChromosome(this.chromosome);
            System.out.println("Chromosome Loaded!");

            /* old design where errors were caught here instead of in FileIO

            try {
                this.chromosome = FileIO.readChromosomeFromFile(chooser.getSelectedFile());
                this.chromosomeComponent.setChromosome(this.chromosome);

            } catch (FileNotFoundException ex) {
//                    buttonPanel. ("Invalid file: " + ex.getMessage());
            } catch (Exception ex) {
//                    setStatus("Error loading: " + ex.getMessage());
            }
            */
        }
    }
    private void saveChromosome(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileIO.writeChromosomeToFile(this.chromosome, chooser.getSelectedFile());
            System.out.println("Chromosome saved!");
        }
    }

    private void mutateChromosome(){
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
