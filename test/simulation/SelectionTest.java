package simulation;

import junit.framework.TestCase;

import java.util.ArrayList;

public class SelectionTest extends TestCase {

    // NOTE: only uses visual verification because assertion would be a pain here.  TODO?
    public void testSelection() {
        Selection selection = new SelectionTruncation(new FitnessMaxOnes());

        ArrayList<Chromosome> chromArray = new ArrayList<>();
        chromArray.add(new Chromosome("00101011"));
        chromArray.add(new Chromosome("00101111"));

        Population pop = new Population(chromArray);

        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chromArray, 0.9);

        Population selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

        chromArray.add(new Chromosome("00111111"));
        chromArray.add(new Chromosome("00001011"));
        chromArray.add(new Chromosome("00101011"));
        chromArray.add(new Chromosome("00101011"));


        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chromArray, 0.9);
        selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

    }

}