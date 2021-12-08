package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A class representing an ordering of characters that can be queried to know
 * the position of a given character.
 *
 * @author Charles Yang
 */
public class Alphabet {


  /**
   * A lookup table containing characters and their positions.
   * Sorted by the character of each entry.
   */
  private CharAndPos[] lookup;


  /**
   * Constructs and initializes the ordering to have exactly the ordering of
   * the elements in the given array.
   *
   * @param ordering the array containing the characters, in the ordering desired
   * @throws NullPointerException if {@code ordering} is {@code null}
   */
  public Alphabet(char[] ordering) throws NullPointerException {
    lookup = new CharAndPos[ordering.length];
    int i = 0;
    for (char c : ordering) lookup[i++] = new CharAndPos(c, i);

    alphabetInsertionSort();
  }

  /**
   * Constructs and initializes the ordering by reading from the indicated
   * file. The file is expected to have a single character on each line, and
   * the ordering in the file is the order that will be used.
   *
   * @param filename the name of the file to read
   * @throws NullPointerException  if {@code filename} is {@code null}
   * @throws FileNotFoundException if the file cannot be found
   */
  public Alphabet(String filename) throws NullPointerException, FileNotFoundException {
    ArrayList<CharAndPos> temp = new ArrayList<>();
    Scanner s = new Scanner(new File(filename));
    int i = 0;

    // Reading and adding chars from file
    while (s.hasNext()) {
      temp.add(new CharAndPos(s.nextLine().charAt(0), i++));
    }
    lookup = temp.toArray(new CharAndPos[]{});
    s.close();

    alphabetInsertionSort();
  }

  private void alphabetInsertionSort() {
    for (int i = 1; i < lookup.length; i++) {
      for (int j = i - 1; j >= 0 && lookup[j + 1].character < lookup[j].character; j--) {
          swap(j + 1, j);
      }
    }
  }

  /**
   * Helper for swapping values in lookup array
   *
   * @param i
   * @param j
   */
  private void swap(int i, int j) {
    CharAndPos temp = lookup[i];
    lookup[i] = lookup[j];
    lookup[j] = temp;
  }

  /**
   * Returns true if and only if the given character is present in the
   * ordering.
   *
   * @param c the character to test
   * @return true if and only if the given character is present in the ordering
   */
  public boolean isValid(char c) {

    return getPosition(c) != -1;
  }

  /**
   * Returns the position of the given character in the ordering.
   * Returns a negative value if the given character is not present in the
   * ordering.
   *
   * @param c the character of which the position will be determined
   * @return the position of the given character, or a negative value if the given
   * character is not present in the ordering
   */
  public int getPosition(char c) {
    return binarySearch(c);
  }

  /**
   * Returns the index of the entry containing the given character within the
   * lookup table {@link #lookup}.
   * Returns a negative value if the given character does not have an entry in
   * the table.
   *
   * @param toFind the character for which to search
   * @return the index of the entry containing the given character, or a negative
   * value if the given character does not have an entry in the table
   */
  private int binarySearch(char toFind) {
    int left = 0;
    int right = lookup.length - 1;

    while (left <= right) {
      int M = left + ((right - left) / 2);
      if (lookup[M].character > toFind) right = M - 1;
      else if (lookup[M].character < toFind) left = M + 1;
      else if (lookup[M].character == toFind) return lookup[M].position;
    }

    return -1;
  }

  /**
   * toString for testing purposes
   *
   * @return Stringified lookup
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();

    for (CharAndPos c : lookup) {
      s.append(c.toString());
      s.append(' ');
    }

    return s.toString();
  }


  /**
   * A PODT class containing a character and a position.
   * Used as the entry type within {@link Alphabet#lookup lookup}.
   */
  /* already completed */
  private static class CharAndPos {
    /**
     * The character of the entry.
     */
    public char character;

    /**
     * The position of the entry in the ordering.
     */
    public int position;


    /**
     * Constructs and initializes the entry with the given values.
     *
     * @param character the character of the entry
     * @param position  the position of the entry
     */
    public CharAndPos(char character, int position) {
      this.character = character;
      this.position = position;
    }

    /**
     * Checks equality of this and other object
     *
     * @param obj object to check equality with
     * @return equality
     */
    @Override
    public boolean
    equals(Object obj) {
      if (null == obj || this.getClass() != obj.getClass()) {
        return false;
      }

      CharAndPos o = (CharAndPos) obj;

      return this.character == o.character && this.position == o.position;
    }

    @Override
    public int hashCode() {
      return character ^ position;
    }

    @Override
    public String toString() {
      return "{" + character + ", " + position + "}";
    }
  }
}
