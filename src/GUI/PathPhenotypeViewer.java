package GUI;

import simulation.pathfinding.PathFitness;
import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;
import java.awt.*;

/**
 * Window for viewing phenotype.
 * <br><br>
 * Might eventually place functionality in a PathPhenotypePanel which will be displayed both in PathPhenotypeViewer and
 * perhaps in some sort of population viewer, which would display multiple PathPhenotypePanels
 */
public class PathPhenotypeViewer extends JFrame {
    public static final int DEFAULT_TILE_SIZE = 100;
    private PathPhenotype phenotype;
    private TerrainGrid terrain;
    private PathPhenotypeComponent pathPhenotypeComponent;
    private PathFitness fitnessCalculator;
    private JLabel infoLabel;
    // The length of one edge of any square tile
    private int tileSize;

    public PathPhenotypeViewer(PathPhenotype phenotype, TerrainGrid terrain) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        this.fitnessCalculator = new PathFitness(terrain);
        this.tileSize = DEFAULT_TILE_SIZE;

        // setup
        this.pathPhenotypeComponent = new PathPhenotypeComponent(phenotype, terrain, tileSize);
        this.infoLabel = new JLabel();
        updateInfoLabelText();
        displaySetup();
    }
    public PathPhenotypeViewer(PathPhenotype phenotype) {
        this(phenotype, new TerrainGrid());
    }

    /**
     * Sets title, adds components, and packs.
     */
    private void displaySetup() {
        setTitle("Phenotype Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display path cost.
        add(infoLabel, BorderLayout.NORTH);
        // Display terrain and path therethrough
        add(pathPhenotypeComponent, BorderLayout.CENTER);

        pack();
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
