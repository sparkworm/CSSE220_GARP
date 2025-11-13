package GUI;

import javax.swing.*;
import simulation.Chromosome;
import simulation.Population;
import java.awt.*;
import java.util.ArrayList;

public class PopulationViewerComponent extends JComponent {

        private ArrayList<Chromosome> chromosomes;
        private int generation = 0;

        // Grid Size
        private static final int CELL_SIZE = 3;  
        private static final int COLOUMN = 10; 
        private static final int PADDING = 5;    

        public PopulationViewerComponent() {
            chromosomes = new ArrayList<>();
            setPreferredSize(new Dimension(400, 600));
            setBackground(Color.WHITE);
        }

        public void updatePopulation(ArrayList<Chromosome> newChromosomes, int gen) {
            this.chromosomes = new ArrayList<>(newChromosomes);
            this.generation = gen;
            repaint();
        }

        public void clear() {
            chromosomes.clear();
            generation = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (chromosomes.isEmpty()) {
                return;
            }

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Generation: " + generation, 10, 20);

            // Chromosome grid 
            int genome_length = chromosomes.get(0).getLength();
            int chromo_cols = (int) Math.ceil(Math.sqrt(genome_length));
            int chromoRows = (int) Math.ceil((double) genome_length / chromo_cols);
            int width_chromosome = chromo_cols * CELL_SIZE;
            int height_chromosome = chromoRows * CELL_SIZE;
            // Draw  chromosome
            int startY = 30;
            int startX = 10;
            for (int i = 0; i < chromosomes.size(); i++) {
                int row = i / COLOUMN;
                int col = i % COLOUMN;
                int x = startX + col * (width_chromosome + PADDING);
                int y = startY + row * (height_chromosome + PADDING);
                drawChromosome(g2, chromosomes.get(i), x, y, chromo_cols, chromoRows);
            }
        }

        private void drawChromosome(Graphics2D g2, Chromosome chromosome,int x, int y, int cols, int rows) {
            for (int i = 0; i < chromosome.getLength(); i++) {
                int row = i / cols;
                int col = i % cols;

                int cellX = x + col * CELL_SIZE;
                int cellY = y + row * CELL_SIZE;

                // Color based on gene value
                if (chromosome.getBit(i)) {
                    g2.setColor(new Color(0, 200, 0)); // Green for 1
                } else {
                    g2.setColor(Color.BLACK); // Black for 0
                }

                g2.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
            }
        }
    }
