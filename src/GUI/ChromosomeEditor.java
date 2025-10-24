package GUI;

import simulation.Chromosome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChromosomeEditor extends JFrame {

    private Chromosome chromosome;
    private ChromosomeComponent chromosomeComponent;

    public ChromosomeEditor() {
        this.setTitle("Chromosome Editor");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
//                this.setLayout(new BorderLayout());
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

        JButton mutateButton = new JButton("Load");
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
                int col = e.getX() / chromosomeComponent.get();
                int row = e.getY() / chromosomeComponent.getPixelSize();
                int index = row * rows_cols + col;
                if (index < chromosome.getLength()) {
                    boolean old = chromosome.getBit(index);
                    chromosomeComponent.getChromosome().setBit(idx, !old);
                    chromosome.repaint();
                }
            }

//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
        });
    }
        private void loadChromosome() {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    this.chromosome = ChromosomeIO.readChromosomeFromeFile(chooser.getSelectedFile());
                    this.chromosomeComponent.setChromosome(this.chromosome);

                } catch (FileNotFoundException  ex) {
//                    buttonPanel. ("Invalid file: " + ex.getMessage());
                } catch (Exception ex) {
//                    setStatus("Error loading: " + ex.getMessage());
                }

            }

        }
        private void saveChromosome(){
            System.out.println("Chromosome saved!");

        }

        private void mutateChromosome(){
            System.out.println("Chromosome mutated!");

        }




        // 4. Add components to the window



}

    // We will add more functionality here for other tasks...

    /**
     * The main method to launch the editor.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChromosomeEditor::new);
    }
}
