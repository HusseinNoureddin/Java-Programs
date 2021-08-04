// SneakyKnights Project 
// Hussein Noureddine, 

// This program checks the surrounding of each knight
// on a given board and returns distinct results based
// on the safety of each knight. It runs in O(n) linear
// runtime.

import java.util.*;
import java.io.*;
import java.lang.*;

public class SneakyKnights
{
	// This method returns the value of the given 
	// string as an int, which is the column's  
	// position using Horner's rule (faster).
	public static int toNumber(String letters)
	{
		int res = 0;

		for (int i = 0; i < letters.length(); i++)
		{
			res *= 26;
			res += letters.charAt(i) - 'a' + 1;
		}

		return res;
	}

	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		String[] list = coordinateStrings.toArray(new String[0]);
		String[] digits = new String[list.length];
		String[] letters = new String[list.length];
		int[] cols = new int[list.length];
		int[] rows = new int[list.length];
		HashMap<Integer, Integer> rowHash = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> colsHash = new HashMap<Integer,Integer>();

		int res = 0, i, x = 0, y = 0;

		for (i = 0; i < list.length; i++)
		{
			digits[i] = list[i].replaceAll("[^0-9]", ""); // stores all char digits
			letters[i] = list[i].replaceAll("[^a-z]", ""); // stores all char letters
			
			cols[i] = toNumber(letters[i]); // char letters to int
			rows[i] = Integer.parseInt(digits[i]); // char numbers to int

			rowHash.put(rows[i], i); 
			colsHash.put(cols[i], i);
		}

		// This loop checks the surrounding of each Knight and
		// returns false if there is a potential attack from other
		// Knights, else the function returns true after the loop
		for (i = 0; i < list.length; i++)
		{
			x = rows[i] + 2; // checking 2 rows above
			y = cols[i] - 1; // checking 1 column below 

			// Checks if x and y exist
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				// Checks if x and y have the same value,
				// if so, return false
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}
			
			y  = cols[i] + 1; 

			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			x = rows[i] - 2;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			y = cols[i] - 1;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			y = cols[i] + 2;
			x = rows[i] + 1;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			x = rows[i] - 1;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			y = cols[i] - 2;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}

			x = rows[i] + 1;
			
			if (rowHash.containsKey(x) && colsHash.containsKey(y))
			{
				if (rowHash.get(x) == colsHash.get(y))
				return false;
			}
		}
		
		return true; 
	}
}


