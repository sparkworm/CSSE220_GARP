package simulation;

import java.util.ArrayList;

import junit.framework.TestCase;

public class CrossoverDuplicateTest extends TestCase {

    public void testCrossover() {
        Selection selection = new SelectionTruncation(new FitnessMaxOnes());
        Crossover crossover = new CrossoverDuplicate();

        ArrayList<Chromosome> chromArray = new ArrayList<>();
        chromArray.add(new Chromosome("00101011"));
        chromArray.add(new Chromosome("00101111"));

        Population pop = new Population(chromArray);

        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chromArray, 0.9);

        Population selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

        Population newPop = new Population(crossover.repopulate(selectedPop.getChromosomes(), pop.getSize()));
        System.out.println("New pop:\n" + newPop);

        chromArray.add(new Chromosome("00111111"));
        chromArray.add(new Chromosome("00001011"));
        chromArray.add(new Chromosome("00101011"));
        chromArray.add(new Chromosome("00101011"));


        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chromArray, 0.2);
        selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

        newPop = new Population(crossover.repopulate(selectedPop.getChromosomes(), pop.getSize()));
        System.out.println("New pop:\n" + newPop);

    }
}
