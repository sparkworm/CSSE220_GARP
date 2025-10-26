package GUI;

import simulation.Chromosome;

import javax.swing.*;
import java.awt.*;

public class ChromosomeComponent extends JComponent {

    private Chromosome chromosome;
    private static final int pixel_size = 30;
//    private static final int rows_cols = 10;


    public ChromosomeComponent(Chromosome chromosome) {
        this.chromosome = chromosome;
        grid();

    }

    public void grid() {
        int length = chromosome.getLength();
        int rows_cols = (int) Math.sqrt(length);
        int width = rows_cols * pixel_size;
        int height = rows_cols * pixel_size;
        this.setPreferredSize(new Dimension(width, height));
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
        grid();
        repaint();
    }

    /**
     * Set the chromosome from a String of 1s and 0s
     * @param strGenotype the String of 1s and 0s representing a genotype
     */
    public void setChromosome(String strGenotype) {
        setChromosome(new Chromosome(strGenotype));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int length = chromosome.getLength();
        int rows_cols = (int) Math.sqrt(length);

//        if (this.chromosome == null) {
//            return;
//        }

        for (int i = 0; i < length; i++) {
            int row = i / rows_cols;
            int col = i % rows_cols;

            int x = col * pixel_size;
            int y = row * pixel_size;

            if (chromosome.getBit(i)) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.BLACK);
            }

            g.fillRect(x, y, pixel_size, pixel_size);


        }
    }

    public int getPixelSize() {
        return pixel_size;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }
}