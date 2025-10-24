package GUI;

import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;
import java.awt.*;

public class PhenotypeComponent extends JComponent {
    private PathPhenotype phenotype;
    private TerrainGrid terrain;
    private int tileSize;

    public PhenotypeComponent(PathPhenotype phenotype, TerrainGrid terrain, int tileSize) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        this.tileSize = tileSize;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        // TODO: color code
        for (int x=0; x<terrain.getWidth(); x++) {
            for (int y=0; y<terrain.getHeight(); y++) {

                g2.setColor(calculateHSBForDifficulty(terrain.getDifficultAtCoord(x,y), 10));
                g2.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
                g2.setColor(Color.WHITE);
                g2.drawString(terrain.getDifficultAtCoord(x,y) + "", x*tileSize + tileSize/2, y*tileSize + tileSize/2);
            }
        }
        g2.setColor(Color.BLACK);
        MultiLine ml = new MultiLine(terrain.getPositionArray(phenotype.getPathArray()), tileSize);
        ml.drawOn(g2);
    }

    protected Color calculateHSBForDifficulty(float difficulty, float maxDifficulty) {
        return Color.getHSBColor(0.75F*difficulty/maxDifficulty + 0.33F, 1, 1);
    }

}
