package GUI;

import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;
import java.awt.*;

public class PathPhenotypeComponent extends JComponent {
    public static final int DEFAULT_FONT_SIZE = 9;
    private PathPhenotype phenotype;
    private TerrainGrid terrain;
    private Font font;
    private int tileSize;

    public PathPhenotypeComponent(PathPhenotype phenotype, TerrainGrid terrain, int tileSize) {
        this.phenotype = phenotype;
        this.terrain = terrain;
        this.tileSize = tileSize;
        this.font = new Font("SansSerif", Font.PLAIN, DEFAULT_FONT_SIZE);
        this.setPreferredSize(new Dimension(tileSize*terrain.getWidth(),tileSize*terrain.getHeight()));
    }

    public void setPhenotype(PathPhenotype newPhenotype) {
        this.phenotype = newPhenotype;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(font);
        for (int x=0; x<terrain.getWidth(); x++) {
            for (int y=0; y<terrain.getHeight(); y++) {

                g2.setColor(calculateHSBForDifficulty(terrain.getDifficultAtCoord(x,y), 10));
                g2.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
                g2.setColor(Color.BLACK);
                g2.drawString(terrain.getDifficultAtCoord(x,y) + "", x*tileSize + tileSize/2, y*tileSize + tileSize/2);
            }
        }
        g2.setColor(Color.BLACK);
        MultiLine ml = new MultiLine(terrain.getPositionArray(phenotype.getPathArray()), tileSize);
        ml.drawOn(g2);
    }

    /**
     * Creates a Color from the difficulty, using the ratio of difficulty to maxDifficulty as a value for the H value of
     * HSB.
     * TODO: make color decisions less hardcoded
     * @param difficulty the difficulty of a given cell
     * @param maxDifficulty the maximum theoretical difficulty.  CANNOT BE ZERO, but doesn't have to be
     *                      below difficulty.
     * @return
     */
    protected Color calculateHSBForDifficulty(float difficulty, float maxDifficulty) {
        return Color.getHSBColor(-0.4F*difficulty/maxDifficulty + 0.4F, 1, 1);
    }

}
