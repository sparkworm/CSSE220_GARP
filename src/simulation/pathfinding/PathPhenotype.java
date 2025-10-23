package simulation.pathfinding;

import simulation.Chromosome;
import simulation.Phenotype;
import utility.Vector2Int;

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
    private Vector2Int[] pathArray;

    public PathPhenotype(Chromosome chromosome) {
        super(chromosome);
        pathArray = new Vector2Int[chromosome.getLength()/2];
        calculatePathArray(chromosome);
    }

    /**
     * Should generate this object's pathArray from the specified Chromosome
     * @param chromosome the chromosome that the path will be decoded from
     */
    private void calculatePathArray(Chromosome chromosome) {
        BitSet[] fragments = chromosome.getGenotypeFragments(2);
        for (int i=0; i<fragments.length; i++) {
            pathArray[i] = directionFromData(fragments[i].get(0), fragments[i].get(1));
        }
    }

    /**
     * Should provide a direction based on two bits.
     * 00: up: Vector2Int(0,-1)
     * 01: right: Vector2Int(1,0)
     * 10: down: Vector2Int(0,1)
     * 11: left: Vector2Int(-1,0)
     * @returns vector corresponding to direction
     */
    private Vector2Int directionFromData(boolean bit1, boolean bit2) {
        if (bit1) {
            if (bit2) { // 11
                return new Vector2Int(-1,0);
            }
            else { // 10
                return new Vector2Int(0,1);
            }
        }
        else {
            if (bit2) { // 01
                return new Vector2Int(1,0);
            }
            else { // 00
                return new Vector2Int(0,-1);
            }
        }
    }

    public Vector2Int[] getPathArray() {
        return this.pathArray;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Path Phenotype with path: ");
        for (Vector2Int v : pathArray) {
            builder.append(v + " ");
        }
        return builder.toString();
    }
}
