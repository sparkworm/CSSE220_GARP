package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

//import static sun.jvm.hotspot.oops.CellTypeState.top;

public class FitnessPlotComponent extends JComponent {
    private ArrayList<Integer> generations;
    private ArrayList<Double> bestFitness;
    private ArrayList<Double> avgFitness;
    private ArrayList<Double> worstFitness;

    private static final int MARGIN_LEFT = 60;
    private static final int MARGIN_RIGHT = 100;
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 60;

//    private final Color low_color = new Color(220, 20, 60);    // red
//    private final Color Average_color = new Color(255, 255, 0);   // blue
//    private final Color Best_color = new Color(34, 139, 34);    //

    public FitnessPlotComponent() {

        setPreferredSize(new Dimension(800, 600));

        generations = new ArrayList<>();
        bestFitness = new ArrayList<>();
        avgFitness = new ArrayList<>();
        worstFitness = new ArrayList<>();
    }

    public void clear() {
        generations.clear();
        bestFitness.clear();
        avgFitness.clear();
        worstFitness.clear();
        repaint();
    }

    public void addData(int gen, double best, double avg, double worst) {
        generations.add(gen);
        bestFitness.add(best);
        avgFitness.add(avg);
        worstFitness.add(worst);
        repaint();
    }
    public void updateData(ArrayList<Double> best, ArrayList<Double> avg, ArrayList<Double> worst) {
        this.bestFitness = new ArrayList<>(best);
        this.avgFitness = new ArrayList<>(avg);
        this.worstFitness = new ArrayList<>(worst);
        repaint();
    }


//    public void saveDataToFile() {
//        try (FileWriter writer = new FileWriter("fitness_data.csv")) {
//            writer.write("Generation,Best,Average,Low\n");
//            for (int i = 0; i < generations.size(); i++) {
//                writer.write(generations.get(i) + "," + bestFitness.get(i) + "," +
//                        avgFitness.get(i) + "," +
//                        worstFitness.get(i) + "\n");
//            }
//            System.out.println("Fitness data saved!");
//        } catch (Exception e) {
//            System.err.println("Error saving: " + e.getMessage());
//        }
//    }


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
//            g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Fitness of Generations", width / 2 - 90, 20);

//        drawTitle(g2);

        drawAxes(g2, width_plot, height_plot);

        drawAxisLabels(g2, width_plot, height_plot);

        drawFitnessLines(g2, width_plot, height_plot);

        drawLegend(g2);

    }

    private void drawLegend(Graphics2D g2) {
        int legendX = getWidth() - MARGIN_RIGHT + 10;
        int legendY = MARGIN_TOP + 20;

        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        // Best Fitness
        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Best", legendX + 20, legendY + 12);

        // Average Fitness
        legendY += 25;
        g2.setColor(new Color(255, 150, 0));
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Average", legendX + 20, legendY + 12);

        // Worst Fitness
        legendY += 25;
        g2.setColor(Color.RED);
        g2.fillRect(legendX, legendY, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("Worst", legendX + 20, legendY + 12);
    }

    private void drawFitnessLines(Graphics2D g2, int widthPlot, int heightPlot) {
        g2.setStroke(new BasicStroke(3));

        double maxFitness = findMaxFitness();
        int numGenerations = bestFitness.size();

        // Draw lines connecting each generation to the next
        for (int i = 0; i < numGenerations - 1; i++) {
            // Calculate X positions (generation number)
            int x1 = MARGIN_LEFT + (i * widthPlot) / Math.max(1, numGenerations - 1);
            int x2 = MARGIN_LEFT + ((i + 1) * widthPlot) / Math.max(1, numGenerations - 1);

            // BEST FITNESS LINE (Green)
            g2.setColor(new Color(0, 200, 0));
            int y1_best = MARGIN_TOP + heightPlot -
                    (int)((bestFitness.get(i) / maxFitness) * heightPlot);
            int y2_best = MARGIN_TOP + heightPlot -
                    (int)((bestFitness.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_best, x2, y2_best);

            // AVERAGE FITNESS LINE (Yellow/Orange)
            g2.setColor(new Color(255, 150, 0));
            int y1_avg = MARGIN_TOP + heightPlot -
                    (int)((avgFitness.get(i) / maxFitness) * heightPlot);
            int y2_avg = MARGIN_TOP + heightPlot -
                    (int)((avgFitness.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_avg, x2, y2_avg);

            // WORST FITNESS LINE (Red)
            g2.setColor(Color.RED);
            int y1_worst = MARGIN_TOP + heightPlot -
                    (int)((worstFitness.get(i) / maxFitness) * heightPlot);
            int y2_worst = MARGIN_TOP + heightPlot -
                    (int)((worstFitness.get(i+1) / maxFitness) * heightPlot);
            g2.drawLine(x1, y1_worst, x2, y2_worst);
        }
      }

    private void drawAxisLabels(Graphics2D g2, int widthPlot, int heightPlot) {

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 11));

        double maxFitness = findMaxFitness();
        int numYTicks = 5;

        for (int i = 0; i <= numYTicks; i++) {
            int y = MARGIN_TOP + heightPlot - (i * heightPlot / numYTicks);
            double value = (maxFitness * i) / numYTicks;

            // Draw tick mark
            g2.drawLine(MARGIN_LEFT - 5, y, MARGIN_LEFT, y);

            // Draw number label
            String label = String.format("%.1f", value);
            g2.drawString(label, MARGIN_LEFT - 40, y + 5);
        }

        int numGenerations = bestFitness.size();
        int numXTicks = Math.min(10, numGenerations);

        for (int i = 0; i <= numXTicks; i++) {
            if (numGenerations == 0) break;

            int genNumber = (i * (numGenerations - 1)) / numXTicks;
            int x = MARGIN_LEFT + (i * widthPlot) / numXTicks;

            // Draw tick mark
            g2.drawLine(x, MARGIN_TOP + heightPlot,
                    x, MARGIN_TOP + heightPlot + 5);

            // Draw number label
            g2.drawString("" + genNumber, x - 10, MARGIN_TOP + heightPlot + 20);
        }

        // Draw axis titles
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Generation", getWidth()/2 - 30, getHeight() - 10);
        g2.drawString("Fitness", 5, MARGIN_TOP - 10);
//        g2.drawLine(margin, h - margin, w - margin, h - margin); // x-axis
//        g2.drawLine(margin, margin, margin, h - margin);         // y-axis
//
//        // Axis labels
//        g2.drawString("Generation", w / 2 - 30, h - 10);
//        g2.drawString("Fitness", 5, h / 2);

    }

    private double findMaxFitness() {
        double max = 1.0;

        for (double val : bestFitness) {
            if (val > max) max = val;
        }

        return max * 1.1;
    }

//    private void drawTitle(Graphics2D g2) {
//        g2.setColor(Color.BLACK);
//        g2.setFont(new Font("Arial", Font.BOLD, 16));
//        String title = "Fitness over Generations";
//        g2.drawString(title, 300, 30);
//    }

    private void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        g2.drawLine(MARGIN_LEFT, MARGIN_TOP,
                MARGIN_LEFT, MARGIN_TOP + height);

        // Draw X-axis
        g2.drawLine(MARGIN_LEFT, MARGIN_TOP + height,
                MARGIN_LEFT + width, MARGIN_TOP + height);

        // Draw top border
        g2.drawLine(MARGIN_LEFT, MARGIN_TOP,
                MARGIN_LEFT + width, MARGIN_TOP);

        // Draw right border
        g2.drawLine(MARGIN_LEFT + width, MARGIN_TOP,
                MARGIN_LEFT + width, MARGIN_TOP + height);
    }



//    private void drawSeries(Graphics2D g2, ArrayList<Double> series, Color color,
//                            double minGen, double minVal, double xSpan, double ySpan,
//                            int PW, int PH) {
//        g2.setColor(color);
//        g2.setStroke(new BasicStroke(2f));
//        int px = -1, py = -1;
//
//        for (int i = 0; i < generations.size(); i++) {
//            double xn = (generations.get(i) - minGen) / xSpan;        // 0..1
//            double yn = (series.get(i) - minVal) / ySpan;      // 0..1
//            int x = 50 + (int) Math.round(xn * PW);
//            int y = 24 + PH - (int) Math.round(yn * PH);
//            if (i > 0) g2.drawLine(px, py, x, y);
//            px = x; py = y;
//        }
//    }
//    private void legendLine(Graphics2D g2, Color c, int x, int y, String label) {
//        g2.setColor(c);
//        g2.setStroke(new BasicStroke(3f));
//        g2.drawLine(x, y, x + 22, y);
//        g2.setColor(Color.DARK_GRAY);
//        g2.drawString(label, x + 30, y + 4);
//    }


}