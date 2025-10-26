package utility;

import simulation.Chromosome;

import java.io.*;

public class FileIO {

    /**
     * Creates and returns a Chromosome object from a text file.  It is assumed that the text file is simply a string of the
     * Chromosome's genotype without any whitespace.
     * @param file the file to which the Chromosome was saved
     * @return a new Chromosome loaded from the specified file.
     */
    public static Chromosome readChromosomeFromFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String strGenotype = reader.readLine();
            return new Chromosome(strGenotype);
        }
        // Might consider throwing error instead of fully catching it so that a null Chromosome doesn't need to be
        // checked for, and instead the error can be handled higher up.
        catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND: " + file);
        }
        catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        // I could probably have gotten away with putting this in the try block, but from my understanding, using
        // finally is good practice
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Write the genotype of the specified Chromosome to a text file as a single-line String.
     * @param chromosome the Chromosome to save
     * @param file the File to save the Chromosome to
     */
    public static void writeChromosomeToFile(Chromosome chromosome, File file) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            // write the genotype onto a single line
            writer.write(chromosome.genotypeString(-1));
        }
        catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        // I could probably have gotten away with putting this in the try block, but from my understanding, using
        // finally is good practice
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    System.err.println(e);
                    e.printStackTrace();
                }
            }
        }
    }

}
