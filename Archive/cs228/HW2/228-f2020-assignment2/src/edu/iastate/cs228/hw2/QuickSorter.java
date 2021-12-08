package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs quick sort
 * to sort the list.
 *
 * @author Charles Yang
 */
public class QuickSorter extends Sorter {
    @Override
    public void sort(WordList toSort, Comparator<String> comp) {
        quickSortRec(toSort, comp, 0, toSort.length() - 1);
    }

    /**
     * Recursive Quick Sorting
     * @param list WordList being sorted
     * @param comp Comparator to use
     * @param start start of range to partition
     * @param end end of range to partition
     */
    private void quickSortRec(WordList list, Comparator<String> comp, int start, int end) {
        if (start < end) {
            int pivot = partition(list, comp, start, end);
            quickSortRec(list, comp, start, pivot - 1);
            quickSortRec(list, comp, pivot + 1, end);
        }
    }

    /**
     * Finds the pivot and does a partitioning
     *
     * @param list WordList being sorted
     * @param comp Comparator to use
     * @param start start of range to partition
     * @param end end of range to partition
     * @return Pivot index
     */
    private int partition(WordList list, Comparator<String> comp, int start, int end) {
        int i = start - 1;
        String temp;
        for (int j = start; j < end; j++) {
            if (comp.compare(list.get(j), list.get(end)) <= 0) {
                temp = list.get(++i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        temp = list.get(++i);
        list.set(i, list.get(end));
        list.set(end, temp);

        return i;
    }
}
