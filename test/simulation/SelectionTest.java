package simulation;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
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

//        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chroms1, 0.5);

        Population selectedPop = new Population(selected);

//        System.out.println("Selected Pop:\n" + selectedPop);


//        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chroms2, 0.5);
        selectedPop = new Population(selected);

//        System.out.println("Truncation Selected Pop:\n" + selectedPop);

    }

    // NOTE: only uses visual verification because assertion would be a pain here.  TODO?
    public void testSelectionRoulette() {
        Selection selection = new SelectionRoulette(new FitnessMaxOnes(), new Random());

        Population pop = new Population(chroms1);

//        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chroms1, 0.5);

        Population selectedPop = new Population(selected);

//        System.out.println("Selected Pop:\n" + selectedPop);

//        System.out.println("Population:\n" + pop);
        selected = selection.makeSelection(chroms2, 0.5);
        selectedPop = new Population(selected);

//        System.out.println("Roulette Selected Pop:\n" + selectedPop);
    }

    public void testFindIdxFromDepth() {
        SelectionRanked selection = new SelectionRanked(new FitnessMaxOnes(), new Random());

        // Values don't actually matter.  Would be equivalent to
        // {1, 2, 3, 4, 5} in SelectionRoulette
        // {15, 14, 12, 9, 5}
        Double[] fitArray = {2.5, 3.0, 6.5, 7.0, 10.0};
        ArrayList<Double> sortedFitnesses = new ArrayList<>(Arrays.asList(fitArray));

        assertEquals(4, selection.findIdxFromDepth(4.4, sortedFitnesses.size()));
        assertEquals(3, selection.findIdxFromDepth(5.4, sortedFitnesses.size()));
        assertEquals(2, selection.findIdxFromDepth(10, sortedFitnesses.size()));
        assertEquals(1, selection.findIdxFromDepth(13.9, sortedFitnesses.size()));
        assertEquals(0, selection.findIdxFromDepth(15, sortedFitnesses.size()));
    }

    public void testMaxDepth() {
        SelectionRanked selection = new SelectionRanked(new FitnessMaxOnes(), new Random());

        assertEquals(1, selection.maxDepth(1));
        assertEquals(3, selection.maxDepth(2));
        assertEquals(6, selection.maxDepth(3));
        assertEquals(10, selection.maxDepth(4));
        assertEquals(15, selection.maxDepth(5));
        assertEquals(21, selection.maxDepth(6));
        assertEquals(28, selection.maxDepth(7));
        assertEquals(36, selection.maxDepth(8));

        assertEquals(3160, selection.maxDepth(79));

        assertEquals(500500, selection.maxDepth(1000));
    }

    // NOTE: only uses visual verification because assertion would be a pain here.  TODO?
    public void testSelectionRanked() {
        Selection selection = new SelectionRanked(new FitnessMaxOnes(), new Random());

        Population pop = new Population(chroms1);

        System.out.println("Population:\n" + pop);

        ArrayList<Chromosome> selected = selection.makeSelection(chroms1, 0.5);

        Population selectedPop = new Population(selected);

        System.out.println("Rank Selected Pop:\n" + selectedPop);

        selected = selection.makeSelection(chroms2, 0.5);
        selectedPop = new Population(selected);
        System.out.println("Rank Selected Pop:\n" + selectedPop);
    }
}