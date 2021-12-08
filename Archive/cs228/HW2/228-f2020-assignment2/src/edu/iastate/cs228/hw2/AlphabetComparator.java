package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * A string comparator that uses an ordering of an {@link Alphabet} to
 * determine how to compare individual characters.
 *
 * @author Charles Yang
 */
public class AlphabetComparator implements Comparator<String> {
    /**
     * The ordering used to compare characters.
     */

    private Alphabet alphabet;


    /**
     * Constructs and initializes the comparator to use the given ordering.
     *
     * @param ordering the ordering to use to compare characters
     * @throws NullPointerException if {@code ordering} is {@code null}
     */
    public AlphabetComparator(Alphabet ordering) throws NullPointerException {
        alphabet = ordering;
    }


    /**
     * Compares the two given strings based on the ordering used by this
     * comparator.
     * <p>
     * Returns a negative value if the first string is considered less than the
     * second, a positive value if greater than the second, and zero if the two
     * strings are equal. Note that an exception must be thrown if the strings
     * contain invalid characters, even if the two strings are equal.
     * <p>
     * For each character of the given strings, the ordering is consulted to
     * determine which of the two characters should go first, with the one with a
     * lesser position in the ordering being determined to be lesser. If the
     * position of the characters are the same, the next character is examined.
     * After the end of one of the strings is reached, the shorter string is
     * considered to be lesser than the other.
     *
     * @throws NullPointerException     if either of {@code a} or {@code b} are {@code null}
     * @throws IllegalArgumentException if either of {@code a} or {@code b} contain a character not found in
     *                                  this comparator's ordering
     */
    @Override
    public int compare(String a, String b) throws NullPointerException, IllegalArgumentException {
        // Checking validity of all chars
        for (char c : a.toCharArray()) {
            if (!alphabet.isValid(c)) throw new IllegalArgumentException();
        }
        for (char c : b.toCharArray()) {
            if (!alphabet.isValid(c)) throw new IllegalArgumentException();
        }
        int i = 0;
        while (i < Math.min(a.length(), b.length())) {
            // Waiting for chars in corresponding index to not be the same to compare
            if (a.charAt(i) != b.charAt(i)) return alphabet.getPosition(a.charAt(i)) - alphabet.getPosition(b.charAt(i));
            i++;
        }
        // if a & b are identical except for length, then return difference in length
        return a.length() - b.length();
    }
}
