package simulation;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

public class SelectionTest extends TestCase {
    ArrayList<Chromosome> chroms1;
    ArrayList<Chromosome> chroms2;


    @Override
    public void setUp() throws Exception {
        super.setUp();

        chroms1 = new ArrayList<>(2);
        chroms1.add(new Chromosome("00101011"));
        chroms1.add(new Chromosome("00101111"));

        chroms2 = new ArrayList<>(6);
        chroms2.addAll(chroms1);
        chroms2.add(new Chromosome("00111111"));
        chroms2.add(new Chromosome("00001011"));
        chroms2.add(new Chromosome("00101011"));
        chroms2.add(new Chromosome("00101011"));
    }

    // NOTE: only uses visual verification because assertion would be a pain here.  TODO?
    public void testSelectionTruncation() {
        Selection selection = new SelectionTruncation(new FitnessMaxOnes());

        Population pop = new Population(chroms1);

        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chroms1, 0.5);

        Population selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);


        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chroms2, 0.5);
        selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

    }


    public void testSelectionRoulette() {
        Selection selection = new SelectionRoulette(new FitnessMaxOnes(), new Random());

        Population pop = new Population(chroms1);

        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chroms1, 0.5);

        Population selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);

        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chroms2, 0.5);
        selectedPop = new Population(selected);

        System.out.println("Selected Pop:\n" + selectedPop);
    }
}