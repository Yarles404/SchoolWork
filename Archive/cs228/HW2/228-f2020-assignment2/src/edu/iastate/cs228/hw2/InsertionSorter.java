package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs insertion sort
 * to sort the list.
 *
 * @author Charles Yang
 */
public class InsertionSorter extends Sorter {
    @Override
    public void sort(WordList toSort, Comparator<String> comp) {
        for (int i = 1; i < toSort.length(); i++) {
            for (int j = i; j > 0; j--) {
                if (comp.compare(toSort.get(j), toSort.get(j - 1)) < 0) toSort.swap(j, j - 1);
                else {break;}
            }
        }
    }
}
