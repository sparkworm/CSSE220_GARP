package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class FitnessPlotComponent extends JComponent {
    private ArrayList<Integer> generations;
    private ArrayList<Double> best_Fit;
    private ArrayList<Double> avg_fit;
    private ArrayList<Double> worst_fit;
    private ArrayList<Double> diversityScores;

    private static final int MARGIN_LEFT = 60;
    private static final int MARGIN_RIGHT = 100;
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 60;

//    private final Color worst_color = new Color(220, 20, 60);    // red
//    private final Color Average_color = new Color(255, 255, 0);   // blue
//    private final Color Best_color = new Color(34, 139, 34);    //

    public FitnessPlotComponent() {

        setPreferredSize(new Dimension(800, 600));
        generations = new ArrayList<>();
        best_Fit = new ArrayList<>();
        avg_fit = new ArrayList<>();
        worst_fit = new ArrayList<>();
    }

    public void clear() {
        generations.clear();
        best_Fit.clear();
        avg_fit.clear();
        worst_fit.clear();
        repaint();
    }

    public void addData(int gen, double best, double avg, double worst, double diversity) {
        generations.add(gen);
        best_Fit.add(best);
        avg_fit.add(avg);
        worst_fit.add(worst);
        diversityScores.add(diversity);
        repaint();
    }
    public void updateData(ArrayList<Double> best, ArrayList<Double> avg, ArrayList<Double> worst, ArrayList<Double> diversityHistory) {
        this.best_Fit = new ArrayList<>(best);
        this.avg_fit = new ArrayList<>(avg);
        this.worst_fit = new ArrayList<>(worst);
        this.diversityScores = new ArrayList<>(diversityHistory);
        repaint();
    }

    public void saveDataToFile() {
        try (FileWriter writer = new FileWriter("fitness_data.csv")) {
            writer.write("Generation,Best,Average,Low");
            for (int i = 0; i < generations.size(); i++) {
                writer.write(generations.get(i) + "," + best_Fit.get(i) + "," + avg_fit.get(i) + "," + worst_fit.get(i) + "\n");
            }
            System.out.println("Fitness saved");
        } catch (Exception e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        int width_plot = width - MARGIN_LEFT - MARGIN_RIGHT;
        int height_plot = height - MARGIN_TOP - MARGIN_BOTTOM;

        g2.setColor(Color.BLACK);
        g2.drawString("Fitness of Generations", width / 2 - 90, 20);

        drawAxes(g2, width_plot, height_plot);
        drawAxisLabels(g2, width_plot, height_plot);
        drawFitnessLines(g2, width_plot, height_plot);
        drawLegend(g2);
    }

    private void drawLegend(Graphics2D g2) {
        int legendX = getWidth() - MARGIN_RIGHT + 10;
        int legendY = MARGIN_TOP + 20;

        // Best Fit
        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Best", legendX + 20, legendY + 12);

        // Average Fit
        legendY += 25;
        g2.setColor(new Color(255, 150, 0));
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Average", legendX + 20, legendY + 12);

        // Worst Fit
        legendY += 25;
        g2.setColor(Color.RED);
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Worst", legendX + 20, legendY + 12);

        // Diversity
        legendY += 25;
        g2.setColor(Color.BLUE);
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Diversity", legendX + 20, legendY + 12);
    }

    private void drawFitnessLines(Graphics2D g2, int widthPlot, int heightPlot) {
        g2.setStroke(new BasicStroke(3));

        double maxFitness = findMaxFitness();
        int numGenerations = best_Fit.size();

        // lines connecting each generation
        for (int i = 0; i < numGenerations - 1; i++) {
            // Calculating x position
            int x1 = MARGIN_LEFT + (i * widthPlot) / Math.max(1, numGenerations - 1);
            int x2 = MARGIN_LEFT + ((i + 1) * widthPlot) / Math.max(1, numGenerations - 1);

            // Best (Green) FIT Line 
            g2.setColor(new Color(0, 200, 0));
//            int y1_green = MARGIN_TOP + heightPlot - (int)(best_Fit.get(i) / maxFitness) ;
            int y1_green = MARGIN_TOP + heightPlot - (int)((best_Fit.get(i) / maxFitness) * heightPlot);
            int y2_green = MARGIN_TOP + heightPlot - (int)((best_Fit.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_green, x2, y2_green);

            // Average (Orange) FIT Line
            g2.setColor(new Color(255, 150, 0));
            int y1_avg = MARGIN_TOP + heightPlot - (int)((avg_fit.get(i) / maxFitness) * heightPlot);
            int y2_avg = MARGIN_TOP + heightPlot - (int)((avg_fit.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_avg, x2, y2_avg);

            // Worst (Red) FIT Line
            g2.setColor(Color.RED);
            int y1_red = MARGIN_TOP + heightPlot - (int)((worst_fit.get(i) / maxFitness) * heightPlot);
            int y2_red = MARGIN_TOP + heightPlot - (int)((worst_fit.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_red, x2, y2_red);

            // DIVERSITY LINE (Blue)
            g2.setColor(Color.BLUE);
            int y1_div = MARGIN_TOP + heightPlot - (int)((diversityScores.get(i) / maxFitness) * heightPlot);
            int y2_div = MARGIN_TOP + heightPlot - (int)((diversityScores.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_div, x2, y2_div);
        }
      }

    private void drawAxisLabels(Graphics2D g2, int widthPlot, int heightPlot) {

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        double maxFitness = findMaxFitness();
        int y_ticks = 5;

        for (int i = 0; i <= y_ticks; i++) {
            int y = MARGIN_TOP + heightPlot - (i * heightPlot / y_ticks);
            double value = (maxFitness * i) / y_ticks;
            g2.drawLine(MARGIN_LEFT - 5, y, MARGIN_LEFT, y);
            double rounded = Math.round(value * 10) / 10.0;
            String label = "" + rounded;
            g2.drawString(label, MARGIN_LEFT - 40, y + 5);
        }
        int numGenerations = best_Fit.size();
        int x_ticks = Math.min(10, numGenerations);

        for (int i = 0; i <= x_ticks; i++) {
            if (numGenerations == 0) break;

            int genNumber = (i * (numGenerations - 1)) / x_ticks;
            int x = MARGIN_LEFT + (i * widthPlot) / x_ticks;

            g2.drawLine(x, MARGIN_TOP + heightPlot, x, MARGIN_TOP + heightPlot + 5);

            g2.drawString("" + genNumber, x - 10, MARGIN_TOP + heightPlot + 20);
        }

        // Axis label
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Generation", getWidth()/2 - 30, getHeight() - 10);
        g2.drawString("Fitness", 5, MARGIN_TOP - 10);
//      g2.drawLine(margin, h - margin, w - margin, h - margin);
//      g2.drawLine(margin, margin, margin, h - margin);
//
//        // Axis labels
//        g2.drawString("Generation", w / 2 - 30, h - 10);
//        g2.drawString("Fitness", 5, h / 2);

    }

    private double findMaxFitness() {
        double max = 1.0;
        for (double val : best_Fit) {
            if (val > max) max = val;
        }
        return max * 1.1;
    }

    private void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        g2.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, MARGIN_TOP + height);

        //  X-axis
        g2.drawLine(MARGIN_LEFT, MARGIN_TOP + height, MARGIN_LEFT + width, MARGIN_TOP + height);

        // border
        g2.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT + width, MARGIN_TOP);
        g2.drawLine(MARGIN_LEFT + width, MARGIN_TOP, MARGIN_LEFT + width, MARGIN_TOP + height);
    }
    //    private void legendLine(Graphics2D g2, Color c, int x, int y, String label) {
//        g2.setColor(c);
//        g2.setStroke(new BasicStroke(3f));
//        g2.drawLine(x, y, x + 22, y);
//        g2.setColor(Color.DARK_GRAY);
//        g2.drawString(label, x + 30, y + 4);
//    }

}