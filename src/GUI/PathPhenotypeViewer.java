package GUI;

import simulation.Chromosome;
import simulation.pathfinding.PathFitness;
import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;

import javax.swing.*;
import java.awt.*;

/**
 * Window for viewing phenotype.
 * <br><br>
 * Might eventually place functionality in a PathPhenotypePanel which will be displayed both in PathPhenotypeViewer and
 * perhaps in some sort of population viewer, which would display multiple PathPhenotypePanels
 */
public class PathPhenotypeViewer extends JFrame {

    public PathPhenotypeViewer(PathPhenotype pheno) {
//        PathPhenotypePanel phenoPanel = new PathPhenotypePanel(pheno);
        PathPhenotypePanel phenoPanel = new PathPhenotypePanel();

        add(phenoPanel);

        phenoPanel.updateWithNewPhenotype(pheno);

        setTitle("Phenotype Viewer");
        setSize(phenoPanel.getPreferredSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
//        pack();
    }



    /**
     * Purely for testing
     */
    public static void main(String[] args) {
        Chromosome testChromo = new Chromosome(100,true);
        PathPhenotype testPheno = new PathPhenotype(testChromo);
        PathPhenotypeViewer pathPhenotypeViewer = new PathPhenotypeViewer(testPheno);
    }
}
