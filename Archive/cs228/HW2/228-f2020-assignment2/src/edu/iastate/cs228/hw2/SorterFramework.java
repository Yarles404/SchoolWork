package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;


/**
 * An class that compares various methods of sorting.
 *
 * @author Charles Yang
 */
public class SorterFramework {
    /**
     * Loads data necessary to run the sorter statistics output, and runs it.
     * The only logic within this method should be that necessary to use the
     * given file names to create the {@link AlphabetComparator},
     * {@link WordList}, and sorters to use, and then using them to run the
     * sorter statistics output.
     *
     * @param args an array expected to contain two arguments:
     *             - the name of a file containing the ordering to use to compare
     *             characters
     *             - the name of a file containing words containing only characters in the
     *             other file
     */
    public static void main(String[] args) {
        // Check arguments
        if (args.length != 2) throw new IllegalArgumentException("Please include two file names separated by a space.");

        Alphabet alphabet = null;
        try {
            alphabet = new Alphabet(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AlphabetComparator comparator = new AlphabetComparator(alphabet);
        WordList words = null;
        try {
            words = new WordList(args[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sorter[] sorters = new Sorter[3];

        sorters[0] = new InsertionSorter();
        sorters[1] = new MergeSorter();
        sorters[2] = new QuickSorter();

        // TODO totalToSort?
        SorterFramework toRun = new SorterFramework(sorters, comparator, words, 1000000);
        toRun.run();
    }


    /**
     * The comparator to use for sorting.
     */
    private Comparator<String> comparator;

    /**
     * The words to sort.
     */
    private WordList words;

    /**
     * The array of sorters to use for sorting.
     */
    private Sorter[] sorters;

    /**
     * The total amount of words expected to be sorted by each sorter.
     */
    private int totalToSort;


    /**
     * Constructs and initializes the SorterFramework.
     *
     * @param sorters     the array of sorters to use for sorting
     * @param comparator  the comparator to use for sorting
     * @param words       the words to sort
     * @param totalToSort the total amount of words expected to be sorted by each sorter
     * @throws NullPointerException     if any of {@code sorters}, {@code comparator}, {@code words}, or
     *                                  elements of {@code sorters} are {@code null}
     * @throws IllegalArgumentException if {@code totalToSort} is negative
     */
    public SorterFramework(Sorter[] sorters, Comparator<String> comparator, WordList words, int totalToSort)
            throws NullPointerException, IllegalArgumentException {
       this.sorters = sorters;
       this.comparator = comparator;
       this.words = words;
       this.totalToSort = totalToSort;
    }


    /**
     * Runs all sorters using
     * {@link Sorter#sortWithStatistics(WordList, Comparator, int)
     * sortWithStatistics()}, and then outputs the following information for each
     * sorter:
     * - the name of the sorter
     * - the length of the word list sorted each time
     * - the total number of words sorted
     * - the total time used to sort words
     * - the average time to sort the word list
     * - the number of elements sorted per second
     * - the total number of comparisons performed
     */
    public void
    run() {
        // Run sortWithStats on all 3 sorters
        for (Sorter s : sorters) {
            try {
                s.sortWithStatistics(words, comparator, totalToSort);
            } catch (Exception e) {
                System.out.println("A sort was canceled manually");
                continue;
            }

            System.out.println(s.getName());
            System.out.println("\tList Length: "+words.length());
            System.out.println("\tTotal Words Sorted: "+s.getTotalWordsSorted());
            System.out.println("\tTime Taken, in milliseconds: "+(s.getTotalSortingTime()/(double)1000000));
            System.out.println("\tAverage Time per Sort, in milliseconds/sort: "+((s.getTotalSortingTime()/1000000)/(s.getTotalWordsSorted()/(double)words.length())));
            System.out.println("\tElements Sorted per Second: "+(s.getTotalWordsSorted()/(s.getTotalSortingTime()/(double)1000000000)));
            System.out.println("\tTotal Comparisons: "+s.getTotalComparisons());
        }
    }
}
