package simulation.pathfinding;

import simulation.Chromosome;
import simulation.Phenotype;

import java.util.BitSet;

/**
 * Class responsible for parsing the information of a chromosome into a pathArray, which represents a course through a
 * TerrainGrid.
 */
public class PathPhenotype extends Phenotype {
    /**
     * Stores a sequence of Directions sequentially, with each indicating which cardinal direction should be travelled
     * in.
     */
    private Direction[] pathArray;

    public PathPhenotype(Chromosome chromosome) {
        super(chromosome);
        pathArray = new Direction[chromosome.getLength()/2];
        calculatePathArray(chromosome);
    }

    /**
     * Should generate this object's pathArray from the specified Chromosome
     * @param chromosome
     */
    private void calculatePathArray(Chromosome chromosome) {
        BitSet[] fragments = chromosome.getGenotypeFragments(2);
        for (int i=0; i<fragments.length; i++) {
            pathArray[i] = directionFromData(fragments[i].get(0), fragments[i].get(1));
        }
    }

    /**
     * Should provide a direction based on two bits.
     * 00 = Directions.UP
     * 01 = Directions.RIGHT
     * 10 = Directions.DOWN
     * 11 = Directions.LEFT
     * @returns Direction corresponding to direction
     */
    private Direction directionFromData(boolean bit1, boolean bit2) {
        if (bit1) {
            if (bit2) { // 11
                return Direction.LEFT;
            }
            else { // 10
                return Direction.DOWN;
            }
        }
        else {
            if (bit2) { // 01
                return Direction.RIGHT;
            }
            else { // 00
                return Direction.UP;
            }
        }
    }

    public Direction[] getPathArray() {
        return this.pathArray;
    }
}
