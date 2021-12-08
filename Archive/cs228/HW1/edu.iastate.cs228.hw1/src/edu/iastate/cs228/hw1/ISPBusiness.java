package edu.iastate.cs228.hw1;

import java.util.Scanner;

/**
 * @author Charles Yang
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for(TownCell cells[] : tOld.grid) {
			for (TownCell cell : cells) {
				tNew.grid[cell.row][cell.col] = cell.next(tNew);
			}
		}

		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return Count of CASUAL users
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for(TownCell cells[] : town.grid) {
			for (TownCell cell : cells) {
				if (cell.who() == State.CASUAL) profit++;
			}
		}

		return profit;
	}
	

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should only print the integer part (Just print the 
	 *  integer value. Example if profit is 35.56%, your output should be just: 35).
	 *  
	 * Note that this method does not throws any exception, thus you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		Town town = null;
		int totalProfit = 0;

		// User interaction to initiate ISPBusiness
		Scanner s = new Scanner(System.in);
		while(true) {
			System.out.println("Welcome! Please select an option:\n\t1: Specify starting grid\n\t2: Start with random grid");
			int input = s.nextInt();
			if (input == 1) {
				System.out.println("Please enter the file name/path containing your starting grid:");
				try {
					town = new Town(s.next());
				} catch (Exception e) {
					System.out.println("Invalid file! Aborting\n");
					e.printStackTrace();
					System.exit(-1);
				}

				break;
			}
			else if (input == 2)
			{
				System.out.println("Please enter a random seed:");
				int seed = s.nextInt();
				System.out.println("Please enter a length");
				int length = s.nextInt();
				System.out.println("Please enter a width:");
				int width = s.nextInt();

				town = new Town(length, width);
				town.randomInit(seed);

				break;
			}
			else {
				System.out.println(input + " is not a valid option! Please enter 1 or 2.");
			}
		}

		// Run 12 iterations
//		System.out.println(0); // Test
//		System.out.println(town);
		totalProfit += getProfit(town); // Profit for initial grid
		for (int cycle = 1; cycle <= 11; cycle++) {
			town = updatePlain(town);
			totalProfit += getProfit(town);

			// Tests
//			System.out.println(cycle);
//			System.out.println(town);
		}

		// Output
		System.out.println(/*(int)*/(totalProfit / (double)(town.getWidth() * town.getLength() * 12) * 100));
	}
}
