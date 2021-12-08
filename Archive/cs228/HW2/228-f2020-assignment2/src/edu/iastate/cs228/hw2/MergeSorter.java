package edu.iastate.cs228.hw2;


import java.util.Comparator;
import java.util.Arrays;


/**
 * An implementation of {@link Sorter} that performs merge sort
 * to sort the list.
 *
 * @author Charles Yang
 */
public class MergeSorter extends Sorter {
    @Override
    public void sort(WordList toSort, Comparator<String> comp) {
        mergeSortRec(toSort, comp, 0, toSort.length() - 1);
    }

    /**
     * Main sorting function
     *
     * @param list WordList to sort
     * @param comp Comparator to be used
     * @param start starting index of partition
     * @param end ending index of partition
     */
    private void mergeSortRec(WordList list, Comparator<String> comp, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSortRec(list, comp, start, mid);
            mergeSortRec(list, comp, mid + 1, end);

            merge(list, comp, start, mid, end);
        }
    }

    /**
     * Merge split array in the correct order
     *
     * @param list original WordList to sort
     * @param comp Comparator to use
     * @param start starting index for this merge
     * @param mid middle index
     * @param end ending index
     */
    private void merge(WordList list, Comparator<String> comp, int start, int mid, int end) {
        WordList left = new WordList(Arrays.copyOfRange(list.getArray(), start, mid + 1));
        WordList right = new WordList(Arrays.copyOfRange(list.getArray(), mid + 1, end + 1));

        int i = 0, j = 0, k = start;
        while (i < left.length() && j < right.length()) {
            if (comp.compare(left.get(i), right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            }
            else {
                list.set(k++, right.get(j++));
            }
        }

        while (i < left.length()) list.set(k++, left.get(i++));
        while (j < right.length()) list.set(k++, right.get(j++));
    }
}
