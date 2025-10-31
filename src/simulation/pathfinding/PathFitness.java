package simulation.pathfinding;

import simulation.Chromosome;
import simulation.Fitness;
import utility.Vector2Int;

import java.util.ArrayList;

public class PathFitness extends Fitness {
    private TerrainGrid terrain;

    public PathFitness(TerrainGrid terrain) {
        this.terrain = terrain;
    }

    /**
     * Calculates the fitness of a Chromosome expressed on a Terrain grid by observing the path length and distance to
     * the target position.  Prioritizes nearness to goal over price of movement.
     * @param chromosome the Chromosome whose fitness is to be calculated
     * @return
     */
    @Override
    public double calculateFitness(Chromosome chromosome) {
        PathPhenotype phenotype = new PathPhenotype(chromosome);
        return calculateFitness(phenotype);
    }

    public double calculateFitness(PathPhenotype phenotype) {
        ArrayList<Vector2Int> posArray = terrain.getPositionArray(phenotype.getPathArray());
        int pathCost = terrain.calculatePathCost(phenotype.getPathArray());
        int distToTarget = posArray.getLast().manhattanDistance(terrain.getTargetPos());
        System.out.println("Path Cost: " + pathCost);
        System.out.println("Dist to target: " + distToTarget);
        if (distToTarget==0) {
            return 1000.0 / pathCost; // pathCost cannot be 0 as long as there aren't 0 tiles, which there shouldn't be
        }
        return 100.0 / distToTarget / pathCost;
    }
}
