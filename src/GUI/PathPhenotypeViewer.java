package GUI;

import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;

/**
 * Window for viewing phenotype.
 * <br><br>
 * Might eventual place functionality in a PathPhenotypePanel which will be displayed both in PathPhenotypeViewer and
 * perhaps in some sort of population viewer, which would display multiple PathPhenotypePanels
 */
public class PathPhenotypeViewer extends JFrame {
    private PathPhenotype phenotype;
    private TerrainGrid terrain;

    public PathPhenotypeViewer(PathPhenotype phenotype, TerrainGrid terrain) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        display();
    }
    public PathPhenotypeViewer(PathPhenotype phenotype) {
        this(phenotype, new TerrainGrid());
    }

    private void display() {
        setTitle("Phenotype Viewer");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // TODO: draw terrain backdrop

        // Draw path lines
        add(new MultiLineComponent(terrain.getPositionArray(phenotype.getPathArray()), 50));
        setVisible(true);
    }
}
