package mainApp;

import GUI.PathPhenotypeViewer;
import simulation.Chromosome; // for testing purposes
import simulation.pathfinding.PathPhenotype;
import simulation.pathfinding.TerrainGrid;
import utility.Vector2Int;

import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author Team F25R301
 * <br>Purpose: Top level class for CSSE220 Project containing main method
 * <br>Restrictions: None
 */
public class MainApp {
	
	
	private void runApp() {
		System.out.println("Write your cool final project here!");
        System.out.println("Second Print statement");
		
		//Launch image demo in a Frame
		//imageDemo();
		Chromosome c = new Chromosome(100, true);
        System.out.println(c);
        phenotypeViewer();
	} // runApp
	
	
	private void imageDemo() {
		JFrame frame = new JFrame("CSSE220 Final Project Demo");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add( new DemoImageComponent() );		
		frame.setVisible(true);
	}
	

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();		
	} // main

    public void phenotypeViewer() {
        Chromosome testChromo = new Chromosome(100,true);
        System.out.println("test chromo: \n" + testChromo);
        PathPhenotype testPheno = new PathPhenotype(testChromo);
        //System.out.println("test pheno: \n" + testPheno);
        TerrainGrid testGrid = new TerrainGrid();
        testGrid.getPositionArray(testPheno.getPathArray());
        System.out.println(testGrid);
        PathPhenotypeViewer pathPhenotypeViewer = new PathPhenotypeViewer(testPheno);
    }
}