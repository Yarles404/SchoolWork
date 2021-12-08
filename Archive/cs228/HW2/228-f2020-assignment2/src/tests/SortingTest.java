import edu.iastate.cs228.hw2.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    @Test
    @DisplayName("Insertion Sort Test")
    void insertion() {
        Alphabet a = new Alphabet(new char[]{'c', 'a', 'b', '#'});
        WordList list = new WordList(new String[]{"#abc", "cab#", "cab", "bac", "abc"});
        InsertionSorter sorter = new InsertionSorter();
        sorter.sort(list, new AlphabetComparator(a));

        assertArrayEquals(new String[]{"cab", "cab#", "abc", "bac", "#abc"}, list.getArray());
    }

    @Test
    @DisplayName("Merge Sort Test")
    void merge() {
        Alphabet a = new Alphabet(new char[]{'c', 'a', 'b', '#'});
        WordList list = new WordList(new String[]{"#abc", "cab#", "cab", "bac", "abc"});
        MergeSorter sorter = new MergeSorter();
        sorter.sort(list, new AlphabetComparator(a));

        assertArrayEquals(new String[]{"cab", "cab#", "abc", "bac", "#abc"}, list.getArray());
    }

    @Test
    @DisplayName("Quick Sort Test")
    void quick() {
        Alphabet a = new Alphabet(new char[]{'c', 'a', 'b', '#'});
        WordList list = new WordList(new String[]{"#abc", "cab#", "cab", "bac", "abc"});
        QuickSorter sorter = new QuickSorter();
        sorter.sort(list, new AlphabetComparator(a));

        assertArrayEquals(new String[]{"cab", "cab#", "abc", "bac", "#abc"}, list.getArray());
    }

    @Test
    @DisplayName("sortWithStats Output")
    void stats() throws IOException {
        Alphabet a = new Alphabet("1000.alphabet.txt");
        WordList list = new WordList("1000.wordlist.txt");
        QuickSorter sorter = new QuickSorter();

        sorter.sortWithStatistics(list, new AlphabetComparator(a), 1000);

        assertArrayEquals(new WordList("1000.sortedlist.txt").getArray(), new WordList("quicksorter.txt").getArray());
    }
}