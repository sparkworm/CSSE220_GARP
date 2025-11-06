
package simulation;

import GUI.FitnessPlotComponent;

import javax.swing.*;
        import java.awt.*;

public class EvolutionViewer {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(EvolutionViewer::launch);
//    }

    private static void launch() {
        // --- Frame ---
        JFrame frame = new JFrame("GA Runner (Task 5)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- Controls (simple) ---
        JPanel controls = new JPanel(new GridLayout(0, 2, 6, 6));

        JTextField popSize    = new JTextField("50");
        JTextField chromLen   = new JTextField("100");
        JTextField elites     = new JTextField("2");
        JTextField survive    = new JTextField("0.5");
        JTextField mutRate    = new JTextField("0.01");
        JTextField maxGens    = new JTextField("200");
        JButton     startBtn  = new JButton("Start");
        JButton     stopBtn   = new JButton("Stop");
        JButton     saveBtn   = new JButton("Save CSV");

        controls.add(new JLabel("Population Size:")); controls.add(popSize);
        controls.add(new JLabel("Chromosome Length:")); controls.add(chromLen);
        controls.add(new JLabel("Elite Survivors:")); controls.add(elites);
        controls.add(new JLabel("Survive Ratio:")); controls.add(survive);
        controls.add(new JLabel("Mutation Rate:")); controls.add(mutRate);
        controls.add(new JLabel("Generations:")); controls.add(maxGens);
        controls.add(startBtn); controls.add(stopBtn);
        controls.add(saveBtn);  controls.add(new JLabel());

        frame.add(controls, BorderLayout.SOUTH);

        // --- Plot panel (your component) ---
        FitnessPlotComponent plot = new FitnessPlotComponent();
        frame.add(plot, BorderLayout.EAST);

        // --- Center info (optional) ---
        JTextArea info = new JTextArea(8, 40);
        info.setEditable(false);
        frame.add(new JScrollPane(info), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        // --- Create simulator (stays null until Start is pressed) ---
        final Timer[] timerBox = new Timer[1];
        final EvolutionSimulator[] simBox = new EvolutionSimulator[1];
        final int[] currentGen = new int[1];
        final int[] maxGenTarget = new int[1];

        // Start
        startBtn.addActionListener(e -> {
            // parse inputs
            try {
                int ps   = Integer.parseInt(popSize.getText().trim());
                int cl   = Integer.parseInt(chromLen.getText().trim());
                int el   = Integer.parseInt(elites.getText().trim());
                double sr= Double.parseDouble(survive.getText().trim());
                double mr= Double.parseDouble(mutRate.getText().trim());
                int maxG = Integer.parseInt(maxGens.getText().trim());

                // create simulator (uses your package-private ctor, OK here in simulation/)
                EvolutionSimulator sim = new EvolutionSimulator(cl, ps, el, mr, sr);
                simBox[0] = sim;

                // attach plot & reset counters
                plot.clear();
                currentGen[0]   = 0;
                maxGenTarget[0] = maxG;

                // timer tick: one generation per tick
                if (timerBox[0] != null && timerBox[0].isRunning()) {
                    timerBox[0].stop();
                }
                timerBox[0] = new Timer(40, ae -> {
                    if (currentGen[0] >= maxGenTarget[0]) {
                        ((Timer) ae.getSource()).stop();
                        info.append("Done.\n");
                        return;
                    }

                    sim.simulateGeneration(); // advances sim + generation

                    // compute best/avg/worst using sim’s fitness & population
                    double best  = Double.NEGATIVE_INFINITY;
                    double worst = Double.POSITIVE_INFINITY;
                    double sum   = 0.0;

                    for (Chromosome c : sim.getPopulation().getChromosomes()) {
                        double f = sim.getFitness().calculateFitness(c);
                        if (f > best)  best = f;
                        if (f < worst) worst = f;
                        sum += f;
                    }
                    double avg = sum / sim.getPopulation().getSize();

                    // push to plot
                    plot.addData(sim.getGeneration(), best, avg, worst);

                    // small log
                    info.append(String.format("Gen %d  best=%.2f avg=%.2f worst=%.2f%n",
                            sim.getGeneration(), best, avg, worst));
                    currentGen[0]++;
                });
                timerBox[0].start();

                info.append("Started.\n");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Stop
        stopBtn.addActionListener(e -> {
            if (timerBox[0] != null) timerBox[0].stop();
            info.append("Stopped.\n");
        });

        // Save CSV
        saveBtn.addActionListener(e -> plot.saveDataToFile());
    }
}
