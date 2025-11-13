package GUI;

import simulation.Chromosome;
import simulation.pathfinding.PathFitness;
import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;
import java.awt.*;

public class PathPhenotypePanel extends JPanel {
    public static final int DEFAULT_TILE_SIZE = 20;
    private PathPhenotype phenotype;
    private TerrainGrid terrain;
    private PathPhenotypeComponent pathPhenotypeComponent;
    private PathFitness fitnessCalculator;
    private JLabel infoLabel;
    // The length of one edge of any square tile
    private int tileSize;

    public PathPhenotypePanel() {
        this.terrain = new TerrainGrid();


        this.fitnessCalculator = new PathFitness(terrain);
        this.tileSize = DEFAULT_TILE_SIZE;
    }

    public PathPhenotypePanel(PathPhenotype phenotype) {
        this(phenotype, new TerrainGrid());
    }

    public PathPhenotypePanel(PathPhenotype phenotype, TerrainGrid terrain) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        this.fitnessCalculator = new PathFitness(terrain);
        this.tileSize = DEFAULT_TILE_SIZE;

        // setup
        displaySetup();
    }

    public void updateWithNewPhenotype(PathPhenotype newPheno) {
        this.phenotype = newPheno;
        ///setPhenotype(newPheno);

        if (pathPhenotypeComponent == null) {
            System.out.println("setting up display");
            displaySetup();

        }
        setPhenotype(newPheno); // redundant, but I don't particularly care atp
        revalidate();
        pathPhenotypeComponent.repaint();
        repaint();
    }

    /**
     * Sets title, adds components, and packs.
     */
    private void displaySetup() {
        this.pathPhenotypeComponent = new PathPhenotypeComponent(phenotype, terrain, tileSize);
        this.infoLabel = new JLabel();
//        updateInfoLabelText();

        // Display path cost.
//        add(infoLabel, BorderLayout.NORTH);
        // Display terrain and path therethrough
        add(pathPhenotypeComponent, BorderLayout.CENTER);
//        setPreferredSize(pathPhenotypeComponent.getPreferredSize());
//        Dimension preferredSize = new Dimension((int) pathPhenotypeComponent.getPreferredSize().width,
//                (int) (pathPhenotypeComponent.getPreferredSize().getHeight()+ infoLabel.getPreferredSize().getHeight()));
        setSize(getPreferredSize());
        System.out.println("Size: " + getSize());
        setVisible(true);
    }

    public void setPhenotype(PathPhenotype newPhenotype) {
        this.phenotype = newPhenotype;
        this.pathPhenotypeComponent.setPhenotype(phenotype);
        updateInfoLabelText();
    }

    public void updateInfoLabelText() {
        infoLabel.setText(String.format("Cost: %d        Fitness: %.2f",
                terrain.calculatePathCost(phenotype.getPathArray()),
                fitnessCalculator.calculateFitness(phenotype)
        ));
    }


}
