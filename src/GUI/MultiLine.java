package GUI;

import utility.Vector2Int;
import javax.swing.*;
import java.awt.*;

/**
 * Responsible for drawing line segments connecting each point to the next in its positionArray
 */
public class MultiLine {
    public static final float DEFAULT_WIDTH = 2.0F;
    public static final Color DEFAULT_COLOR = new Color(0.0F,0.25F,0.75F,0.5F);
    private Vector2Int[] positionArray;
    private int lengthMultplier;
    private Stroke stroke;
    private Color color;

    public MultiLine(Vector2Int[] positionArray, int lengthMultiplier, float width, Color color) {
        this.positionArray = positionArray;
        this.lengthMultplier = lengthMultiplier;
        this.stroke = new BasicStroke(width);
        this.color = color;
    }
    public MultiLine(Vector2Int[] positionArray, int lengthMultiplier) {
        this(positionArray, lengthMultiplier, DEFAULT_WIDTH, DEFAULT_COLOR);
    }

    public void drawOn(Graphics2D g2) {
        g2.setStroke(stroke);
        g2.setColor(color);
        for (int i=0; i<positionArray.length-1; i++) {
            int x1 = positionArray[i].getX() * lengthMultplier + lengthMultplier/2;
            int x2 = positionArray[i+1].getX() * lengthMultplier + lengthMultplier/2;
            int y1 = positionArray[i].getY() * lengthMultplier + lengthMultplier/2;
            int y2 = positionArray[i+1].getY() * lengthMultplier + lengthMultplier/2;
            //System.out.println(String.format("Drawing (%d,%d) to (%d,%d)", x1, y1, x2, y2));
            g2.drawLine(x1, y1, x2, y2);
        }
    }
}
