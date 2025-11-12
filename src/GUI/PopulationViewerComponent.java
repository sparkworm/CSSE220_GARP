package GUI;

import javax.swing.*;
import simulation.Chromosome;
import simulation.Population;
import java.awt.*;
import java.util.ArrayList;

public class PopulationViewerComponent extends JComponent {

        private ArrayList<Chromosome> chromosomes;
        private int generation = 0;

        // Size of each individual chromosome grid
        private static final int CELL_SIZE = 3;  // Small squares
        private static final int GRID_COLS = 10; // 10 chromosomes per row
        private static final int PADDING = 5;    // Space between chromosome grids

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

            // Fill background
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (chromosomes.isEmpty()) {
                return;
            }

            // Draw title
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Generation: " + generation, 10, 20);

            // Calculate chromosome grid dimensions
            int genomeLength = chromosomes.get(0).getLength();
            int chromoCols = (int) Math.ceil(Math.sqrt(genomeLength));
            int chromoRows = (int) Math.ceil((double) genomeLength / chromoCols);

            int chromoWidth = chromoCols * CELL_SIZE;
            int chromoHeight = chromoRows * CELL_SIZE;

            // Draw each chromosome
            int startY = 30;
            int startX = 10;

            for (int i = 0; i < chromosomes.size(); i++) {
                int row = i / GRID_COLS;
                int col = i % GRID_COLS;

                int x = startX + col * (chromoWidth + PADDING);
                int y = startY + row * (chromoHeight + PADDING);

                drawChromosome(g2, chromosomes.get(i), x, y, chromoCols, chromoRows);
            }
        }

        private void drawChromosome(Graphics2D g2, Chromosome chromosome,
                                    int x, int y, int cols, int rows) {
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
