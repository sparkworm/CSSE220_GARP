package simulation;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Selection {
    Fitness fitness;

    public Selection (Fitness fitness) {
        this.fitness = fitness;
    }

    public abstract ArrayList<Chromosome> makeSelection(ArrayList<Chromosome> population, double surviveRatio);
}
