package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class FitnessPlotComponent extends JComponent {
    private ArrayList<Integer> generations;
    private ArrayList<Double> bestFitness;
    private ArrayList<Double> avgFitness;
    private ArrayList<Double> worstFitness;

    private final Color low_color = new Color(220, 20, 60);    // red
    private final Color Average_color = new Color(255, 255, 0);   // blue
    private final Color Best_color = new Color(34, 139, 34);    //

    public FitnessPlotComponent() {
//        setTitle("Fitness over Generations");
//        setSize(400, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
        setPreferredSize(new Dimension(400, 600));

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


    public void saveDataToFile() {
        try (FileWriter writer = new FileWriter("fitness_data.csv")) {
            writer.write("Generation,Best,Average,Low\n");
            for (int i = 0; i < generations.size(); i++) {
                writer.write(generations.get(i) + "," + bestFitness.get(i) + "," +
                        avgFitness.get(i) + "," +
                        worstFitness.get(i) + "\n");
            }
            System.out.println("Fitness data saved!");
        } catch (Exception e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        int left = 50;
        int right = 12;
        int top = 24;
        int bottom = 36;
        int width_plot = w - left - right;
        int height_plot = h - top - bottom;


        g2.setColor(Color.BLACK);
//            g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Fitness of Generations", w / 2 - 90, 20);


        g2.setColor(new Color(230, 230, 230));
        g2.fillRect(left, top, width_plot, height_plot);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(left, top, width_plot, height_plot);

        if (generations.isEmpty()) {
            drawAxes(g2, width_plot, height_plot, 0, 1, 0, 1);
            return;
        }

        int min_genr = generations.get(0);
        int max_genr = generations.get(generations.size() - 1);

        double maximum_Y = 0.0;


        double minVal = Double.POSITIVE_INFINITY;
        double maxVal = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < generations.size(); i++) {
            double b = bestFitness.get(i), a = avgFitness.get(i), wv = worstFitness.get(i);
            if (b < minVal) minVal = b;
            if (a < minVal) minVal = a;
            if (wv < minVal) minVal = wv;
            if (b > maxVal) maxVal = b;
            if (a > maxVal) maxVal = a;
            if (wv > maxVal) maxVal = wv;
        }
        drawAxes(g2, width_plot, height_plot, min_genr, max_genr, minVal, maxVal);

        double xSpan = max_genr - min_genr;
        double ySpan = maxVal - minVal;

        drawSeries(g2, bestFitness, Best_color, min_genr, minVal, xSpan, ySpan, width_plot, height_plot);
        drawSeries(g2, avgFitness, Average_color, min_genr, minVal, xSpan, ySpan, width_plot, height_plot);
        drawSeries(g2, worstFitness, low_color, min_genr, minVal, xSpan, ySpan, width_plot, height_plot);

        int lx = left + 10, ly = top + 16;
        legendLine(g2, Best_color, lx, ly, "Best");
        legendLine(g2, Average_color, lx, ly + 18, "Average");
        legendLine(g2, low_color, lx, ly + 36, "Worst");
    }
//        // Draw axes
//        g2.setColor(Color.BLACK);
//        g2.drawLine(margin, h - margin, w - margin, h - margin); // x-axis
//        g2.drawLine(margin, margin, margin, h - margin);         // y-axis
//
//        // Axis labels
//        g2.drawString("Generation", w / 2 - 30, h - 10);
//        g2.drawString("Fitness", 5, h / 2);

    private void drawAxes(Graphics2D g2, int width, int height,
                          double xMin, double xMax, double yMin, double yMax) {
        int x0 = 50, y0 = 24 + height;

        g2.setColor(Color.BLACK);
        g2.drawLine(x0, y0, 50 + width, y0);  // x axis
        g2.drawLine(x0, y0, x0, 24);

        // Draw ticks
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = 0; i <= 6; i++) {
            double t = i / (double) 6;
            int x = 50 + (int) Math.round(t * width);
            g2.drawLine(x, y0, x, y0 + 4);
            int gen = (int) Math.round(xMin + t * (xMax - xMin));
            String s = Integer.toString(gen);
            g2.drawString(s, x - 10, y0 + 18);
        }
        for (int i = 0; i <= 6; i++) {
            double t = i / (double) 6;
            int y = 50 + height - (int) Math.round(t * height);
            g2.drawLine(x0 - 4, y, x0, y);
            double val = yMin + t * (yMax - yMin);
            String s = String.format("%.2f", val);
            g2.drawString(s, x0 - 8, y + 5);

            g2.setColor(new Color(235, 235, 235));
            g2.drawLine(50, y, 50 + width, y);
            g2.setColor(Color.BLACK);
        }

    }

//    String xLab = "Generation", yLab = "Fitness";
//        g2.drawString(xLab,50 +(wid -g2.getFontMetrics().
//
//    stringWidth(xLab))/2,TOP +PH +40);
//
//    Graphics2D gy = (Graphics2D) g2.create();
//        gy.rotate(-Math.PI /2);
//        gy.drawString(yLab,-(TOP +(PH +g2.getFontMetrics().
//
//    stringWidth(yLab))/2),22);
//        gy.dispose();


    private void drawSeries(Graphics2D g2, ArrayList<Double> series, Color color,
                            double minGen, double minVal, double xSpan, double ySpan,
                            int PW, int PH) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f));
        int px = -1, py = -1;

        for (int i = 0; i < generations.size(); i++) {
            double xn = (generations.get(i) - minGen) / xSpan;        // 0..1
            double yn = (series.get(i) - minVal) / ySpan;      // 0..1
            int x = 50 + (int) Math.round(xn * PW);
            int y = 24 + PH - (int) Math.round(yn * PH);
            if (i > 0) g2.drawLine(px, py, x, y);
            px = x; py = y;
        }
    }
    private void legendLine(Graphics2D g2, Color c, int x, int y, String label) {
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(x, y, x + 22, y);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(label, x + 30, y + 4);
    }


}