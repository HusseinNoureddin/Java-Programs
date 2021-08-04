// SneakyQueens.java
// Hussein Noureddine

// This program checks whether the queens on a given
// board can attack each other in O(n) linear runtime.

import java.util.*;
import java.io.*;

public class SneakyQueens
{
	public static int power(int base, int exp)
	{
		if (exp == 0)
			return 1;
		if (exp == 1)
			return base;

		return base * power(base, exp - 1);
	}

	// instead of Math.pow or recursive power
	// this is Horner's rule
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

	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		String[] list = coordinateStrings.toArray(new String[0]);
		String[] digits = new String[list.length];
		String[] letters = new String[list.length];
		int[] cols = new int[list.length];
		int[] rows = new int[list.length];
		int[] rDiagonal = new int[list.length];
		int[] cDiagonal = new int[list.length];
		HashMap<Integer, Integer> rowHash = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> colsHash = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> rDiag = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> cDiag = new HashMap<Integer,Integer>();

		int res = 0, i;

		for (i = 0; i < list.length; i++)
		{
			digits[i] = list[i].replaceAll("[^0-9]", "");
			letters[i] = list[i].replaceAll("[^a-z]", "");
			
			cols[i] = toNumber(letters[i]);
			rows[i] = Integer.parseInt(digits[i]);

			rowHash.put(rows[i], 0);
			colsHash.put(cols[i], 0);

			rDiagonal[i] = rows[i] - cols[i];
			rDiag.put(rDiagonal[i], 0);

			cDiagonal[i] = rows[i] + cols[i];
			cDiag.put(cDiagonal[i], 0);
		}

		if (rowHash.size() < rows.length)
			return false;
		if (colsHash.size() < cols.length)
			return false;	
		if (rDiag.size() < rDiagonal.length)
			return false;
		if (cDiag.size() < cDiagonal.length)
			return false;


		return true;
	}

}

