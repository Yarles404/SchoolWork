package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 *  @author Charles Yang
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		grid = new TownCell[length][width];
		this.length = length;
		this.width = width;
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simply throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File f = new File(inputFileName);
		Scanner s = new Scanner(f);

		this.length = s.nextInt();
		this.width = s.nextInt();
		grid = new TownCell[length][width];
		s.nextLine();

		for (int r = 0; r < length; r++) {
			char[] row = s.nextLine().toCharArray();
			for (int c = 0; c < width; c++) {
				switch(row[c*2]) {
					case 'C':
						grid[r][c] = new Casual(this, r, c);
						break;
					case 'S':
						grid[r][c] = new Streamer(this, r, c);
						break;
					case 'R':
						grid[r][c] = new Reseller(this, r, c);
						break;
					case 'E':
						grid[r][c] = new Empty(this, r, c);
						break;
					case 'O':
						grid[r][c] = new Outage(this, r, c);
						break;
				}
			}
		}
		s.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cells with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for (int r = 0; r < length; r++) {
			for (int c = 0; c < width; c++) {
				switch(rand.nextInt(5)) {
					case 0:
						grid[r][c] = new Reseller(this, r, c);
						break;
					case 1:
						grid[r][c] = new Empty(this, r, c);
						break;
					case 2:
						grid[r][c] = new Casual(this, r, c);
						break;
					case 3:
						grid[r][c] = new Outage(this, r, c);
						break;
					case 4:
						grid[r][c] = new Streamer(this, r, c);
						break;
				}
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (int r = 0; r < length; r++) {
			for (int c = 0; c < width; c++) {
				switch(grid[r][c].who()) {
					case RESELLER:
						s.append("R ");
						break;
					case EMPTY:
						s.append("E ");
						break;
					case CASUAL:
						s.append("C ");
						break;
					case OUTAGE:
						s.append("O ");
						break;
					case STREAMER:
						s.append("S ");
						break;
				}
			}
			s.append("\n");
		}

		return s.toString();
	}
}
