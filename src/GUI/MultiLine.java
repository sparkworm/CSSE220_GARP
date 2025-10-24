package GUI;

import utility.Vector2Int;
import javax.swing.*;
import java.awt.*;

/**
 * Responsible for drawing line segments connecting each point to the next in its positionArray
 */
public class MultiLine {
    private Vector2Int[] positionArray;
    private int lengthMultplier;

    public MultiLine(Vector2Int[] positionArray, int lengthMultiplier) {
        this.positionArray = positionArray;
        this.lengthMultplier = lengthMultiplier;
    }

    public void drawOn(Graphics2D g2) {
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
