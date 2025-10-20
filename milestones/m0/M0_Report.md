# CSSE 220 Milestone 0 Report
*Use this template for your Milestone 0 (M0) report. For future milestones, adapt the [M1 report template](../m1/M1_Report.md).*

**Project Title**: GARP: Engineering

**Team Name**: F25R301

**Team ID**: F25R301

**Team Members**: 
- Baruni Cherukuri (cherukbj)
- David Clutter (clutteda)

---
## Section 1: System Description
Most of the program is managed from EvolutionSimulatorGUI, which takes user input and runs an instance of EvolutionSimulator, the class actually responsible for the various steps that a population goes through in a generation.  Each of these steps (Selection, Crossover, and Mutation) has its own class to contain related functionality.  (Note that Replacement doesn't get a class since we assumed that the newly generated generation becomes the current generation without much nuance.)  EvolutionSimulatorGUI makes use of a timer so that the user can watch the evolution in real time, however a timer is not used in EvolutionSimulator as a headless simulation should simply try to run as fast as possible.

Population is the class holding the various Chromosomes, each of which stores a genotype, which is represented as a BitSet.  Chromosomes also have functionality related to altering their genotype.  Note that Chromosomes do _not_ contain functionality related to calculating Fitness or Crossover, since these functionalities might have multiple implementations, and Chromosome should be as lightweight as possible to allow for the many operations it undergoes.

FileIO is the class responsible for saving and loading chromosome data (and perhaps population data if that optional functionality is implemented).

## Section 2: Outline of Remaining Milestones
*TODO: Add tasks/deliverables you have planned for each milestone below. If you took the option of having a genAI tool create an initial draft of milestones, include a link to your conversation at the top of this section.*
### M1
(specified by document)
1. Rename project.
2. Load example chromosomes from text files. 
3. Throw and Handle Exceptions   
4. Visualize chromosomes genotype  
5. Apply mutations with user-defined rates  
6. Enable runtime Save and Load functionality   
7. Provide a user-editable chromosome interface   
8. Create a visualization for a “phenotype”  
9. Mutation Rate Validation  


### M2
(specified by document)
1. Generate a (seeded) random population of chromosomes  
2. Generate a special initial population of chromosomes with specific fitness values (0/1/3/6)  
3. Implement and display fitness functions  
4. Implement an evolutionary loop that uses truncation selection  
5. Create a fitness plot of the evolving population  
6. Implement the ability to include mutation into the evolutionary loop  
7. Implement elitism in the evolutionary process  
8. Provide a termination condition based on fitness  
9. Visualize the fittest chromosome during evolution  
10. Implement and validate crossover  
11. Select data structures for Roulette Wheel Selection  

### M3
(specified by document)
1. Create a GUI to specify parameters and run the evolutionary loop.  
2. Visualize the entire population during evolution  
3. Implement crossover in the evolutionary process  
4. Implement Roulette Wheel Selection  
5. Implement Ranked Selection  
6. Measure and visualize population diversity  
7. Validate Evolutionary Algorithm.  
8. Optionally, analyze the computational complexity of key algorithm components

### M4
(specified by document)
1. Submit a final UML diagram reflecting the actual code. (10%)
2. Conduct a set of experiments and analyze results. (40%)
3. Complete your selected goal system (Science, Engineering, Art) (50%)
4. BONUS: Open-Ended Extensions (Inverse Kinematics??)

## Section 3: Potential Classes
GA
- Population  
- Chromosome
- EvolutionSimulator
- _evolutionary step_  
  - Fitness
  - Selection
  - Crossover
  - Mutation
UI
- PhenotypeViewer
- ChromosomeEditor
- ChromosomeDisplay (for drawing chromosome in editor and anywhere else)
- PhenotypeViewer  

Utility
- FileIO

## Section 4: Draft of UML for M1
@startuml

class Main {
}
Main --> PhenotypeViewer
Main --> EvolutionSimulatorGUI
Main --> ChromosomeEditor

class Population {
mutateChromosomes(): void
calculateDiversity(): double
}
Population -->"*" Chromosome

class Chromosome {
genotype: BitSet
mutate(mutator: Mutation): void
setBit(bitIdx: int): void
getBit(bitIdx: int): int?
}

class Fitness {
evaluateFitness(chromosome: Chromosome): double
}


enum SelectionType{
Roulette
Truncation
Ranked
}

class Selection {
selectChromosomes(pop: Population): ArrayList<Chromosome>
}
Selection --> SelectionType

class Crossover {
createOffspring(c1: Chromosome, c2: Chromosome): Chromosome
}

class Mutation {
mutateChromosome(c: Chromosome): void
}

class ChromosomeEditor extends JFrame{
loadButton: JButton
saveButton: JButton
toggleBit(idx: int): void
}
ChromosomeEditor --> ChromosomeDisplay
ChromosomeEditor --> FileIO
ChromosomeEditor --> Chromosome

class ChromosomeDisplay extends JPanel{
drawChromosome(): void
}
ChromosomeDisplay --> Chromosome

class PhenotypeViewer extends JFrame{
loadButton: JButton
}
PhenotypeViewer --> FileIO

class EvolutionSimulator {
generation: int
crossoverActive: boolean
initializePopulation(): void
runSimulation(generations: int): void
simulateGeneration(): void
}
EvolutionSimulator --> Selection
EvolutionSimulator --> Crossover
EvolutionSimulator --> Fitness
EvolutionSimulator --> Mutation
EvolutionSimulator --> Population

class EvolutionSimulatorGUI extends JFrame {
timer: Timer
startSimulation(): void
pauseSimulation(): void
setSimulationGenerations(gen: int): void
setSimulationTime(time: double): void
plotGeneration(): void
}
EvolutionSimulatorGUI --> EvolutionSimulator

class FileIO {
loadChromosome(filename: String): Chromosome
saveChromosome(chromosome: Chromosome filepath: String): void
}

@enduml
