package utility;

import java.util.Vector;

public class Vector2Int {
    private int x;

    private int y;

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Vector2Int() {
        this(0,0);
    }

    /**
     * Finds the opposite of this vector, such that both elements are opposite sign of the original
     * @return a new vector in the opposite direction of the original.
     */
    public Vector2Int opposite() {
        return new Vector2Int(-x,-y);
    }

    /**
     * Returns the sum of the provided vectors
     * @param vec1
     * @param vec2
     * @return sum of vec1 and 2
     */
    public static Vector2Int add(Vector2Int vec1, Vector2Int vec2) {
        return new Vector2Int(vec1.getX() + vec1.getX(), vec2.getY() + vec2.getY());
    }

    /**
     * Increases the value of the vector by other
     * @param other the vector being added to this one
     */
    public void increaseBy(Vector2Int other) {
        this.x += other.getX();
        this.y += other.getY();
    }

    /**
     * Returns the sum of the vertical and horizontal components of the vector.
     * @return absolute value of x + absolute value of y
     */
    public int manhattanLength() {
        return Math.abs(x) + Math.abs(y);
    }

    /**
     * Returns the sum of the magnitude of the x distance and y distance to other vector.
     * @param other vector to compare
     * @return value >= 0
     */
    public int manhattanDistance(Vector2Int other) {
        return Math.abs(x-other.getX()) + Math.abs(y-other.getY());
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Int clone() {
        return new Vector2Int(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Vector2Int other) {
        return other.getX() == x && other.getY() == y;
    }
}
