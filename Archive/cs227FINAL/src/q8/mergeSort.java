package q8;

import java.util.Arrays;

public class mergeSort {
    private static void merge(int[] sorted, int[] a1, int[] a2, int[] a3){ }


    private static void mergeSortRec(int[] a)
    {
        int thirdSize = (a.length + 2) / 3;

        int[] a1 = Arrays.copyOfRange(a, 0, thirdSize);

        if (a.length < 1)
        {
            return;
        }

        int[] a2 = Arrays.copyOfRange(a, thirdSize, 2*thirdSize);
        int[] a3 = Arrays.copyOfRange(a, 2*thirdSize, a.length);

        mergeSortRec(a1);
        mergeSortRec(a2);
        mergeSortRec(a3);
    }
} // I really don't know what I'm doing.
