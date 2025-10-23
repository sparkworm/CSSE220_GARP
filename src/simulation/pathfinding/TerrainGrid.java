package simulation.pathfinding;

import utility.Vector2Int;

public class TerrainGrid {

    // Note that this would actually appear be inverted such that row=column and column=row
    private static final int[][] DEFAULT_COST_GRID = {
            {1, 3, 6, 3, 6},
            {5, 2, 7, 2, 5},
            {5, 6, 1, 8, 9},
            {8, 3, 1, 7, 8},
            {7, 2, 4, 8, 4}
    };

    /**
     * The integer array providing the cost of travel on a given tile.  Technically the costs can be any number, but
     * numbers under 1 are counter-intuitive. <br>
     * NOTE that 'rows' here will actually act as the x coordinates, and columns the y coordinates.
     */
    int[][] costGrid;


    /**
     * The position from which the path should start.
     */
    Vector2Int startingPos;
    /**
     * The position of the target on the terrain grid.  Used here because once a path reaches the target, its cost
     * calculation is terminated.
     */
    Vector2Int targetPos;

    public TerrainGrid(Vector2Int startingPos, Vector2Int targetPos) {
        this.costGrid = DEFAULT_COST_GRID;
        this.startingPos = startingPos;
        this.targetPos = targetPos;
    }
    public TerrainGrid() {
        this(new Vector2Int(0,0), new Vector2Int(DEFAULT_COST_GRID.length, DEFAULT_COST_GRID[0].length));
    }

    /**
     * Calculates the sum of every grid tile's value that the path must travel through.
     * @param path the path taken through the array
     * @return sum of the difficulty of every terrain grid tile
     */
    public int calculatePathCost(Vector2Int[] path) {
        int cost = 0;
        Vector2Int currentPos = startingPos.clone();
        for (Vector2Int dir : path) {
            currentPos.increaseBy(dir);
            boundPos(currentPos);
            // Note that if the currentIndex hasn't changed (do to going out of bounds) the cost is still added
            cost += getDifficultAtCoord(currentPos);
        }
        return cost;
    }

    /**
     * Creates the array of positions that the provided path would result in.
     * <br> <br>Note that it is calculated here and not Phenotype for three reasons: The boarders must be known,
     * startingPos must be known, and most critically, if intraversable obstacles are ever introduced, they must be
     * accessed in TerrainGrid
     * @param path the path that is attempted
     * @return the positions that the path leads to
     */
    public Vector2Int[] getPositionArray(Vector2Int[] path) {
        Vector2Int[] posArray = new Vector2Int[path.length]; // posArray will be 1 greater in length than path
        Vector2Int currentPos = startingPos.clone();
        posArray[0] = currentPos.clone();
        for (int i=1; i<posArray.length; i++) {
            currentPos.increaseBy(path[i-1]);
            boundPos(currentPos);
            posArray[i] = currentPos.clone();
        }
        return posArray;
    }

    /**
     * Finds the
     * @param coord
     */
    public int getDifficultAtCoord(Vector2Int coord) {
        return costGrid[coord.getX()][coord.getY()];
    }

    /**
     * Alters the value of the passed argument such that it could be a proper location on grid, within borders.
     * @param pos the position that needs to be bound to the proper domain
     */
    private void boundPos(Vector2Int pos) {
        if (pos.getX() < 0) pos.setX(0);
        else if (pos.getX() >= costGrid.length) pos.setX(costGrid.length - 1);
        if (pos.getY() < 0) pos.setY(0);
        else if (pos.getY() >= costGrid[0].length) pos.setY(costGrid[0].length - 1);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("Terrain Grid with terrain of size %d X %d",
                costGrid.length, costGrid[0].length));
        for (int i=0; i<costGrid[0].length; i++) {
            builder.append("\n");
            for (int j=0; j<costGrid.length; j++) {
                builder.append(costGrid[j][i] + " ");
            }
        }
        return builder.toString();
    }
}
