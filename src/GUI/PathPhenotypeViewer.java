package GUI;

import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;

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
    // The length of one edge of any square tile
    private int tileSize;

    public PathPhenotypeViewer(PathPhenotype phenotype, TerrainGrid terrain) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        this.tileSize = DEFAULT_TILE_SIZE;
        display();
    }
    public PathPhenotypeViewer(PathPhenotype phenotype) {
        this(phenotype, new TerrainGrid());
    }

    private void display() {
        setTitle("Phenotype Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Draw terrain
        add(new PathPhenotypeComponent(phenotype, terrain, tileSize));

        pack();

        // Draw path lines
        setVisible(true);
    }
}
