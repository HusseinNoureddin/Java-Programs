// RunLikeHell.java
// Hussein Noureddine

// This program returns the maximum number a person
// can return with without using two consecutive indices
// of an array. It solves this problem in an O(n) Bottom-up
// Dynamic Programming approach.

import java.util.*;
import java.io.*;

public class RunLikeHell
{
	// This method takes in an array "block" and returns the
	// max knowledge (max total) it can return without using 2
	// consecutive indices, as an O(n) Bottom-up DP solution. 
	public static int maxGain(int [] blocks)
	{
		// variable and array declarations
		int n = blocks.length;
		int [] matrix = new int[3];

		// if blocks is empty, return 0
		if (n == 0)
			return 0;
		
		// if blocks has 1 number, return it
		if (n == 1)
			return blocks[0];

		// if blocks length is 2, return the greater of the 2
		if (n == 2)
			return Math.max(blocks[0], blocks[1]);

		// if blocks length is 3, return # in index 1 OR #
		// of index 0 plus # of index 2, whichever is greater
		if (n == 3)
			return Math.max(blocks[0] + blocks[2], blocks[1]);

		// setting first 2 indices = to those in blocks
		matrix[0] = blocks[0];
		matrix[1] = blocks[1];

		// This loop starts at index 2 and loops till the end.
		// At each index, it checks left and takes the greater
		// option and adds it with the current value in blocks[i]
		for (int i = 2; i < n; i++)
		{
			// checks if it's at i = 2, if so, then we only
			// have 1 option which is to add the nbr 2 indices left
			if (i == 2)
			{
				matrix[i] = blocks[i] + blocks[i - 2];
				continue;
			}

			// otherwise add the greater value (2 indices or 3 indices) 
			// of matrix to the left of the current block[i] with block[i]
			matrix[i % 3] = blocks[i] + Math.max(matrix[(i - 2) % 3], matrix[(i - 3) % 3]);
		}

		// returns the greater value between index n - 1
		// or n - 2 of the matrix
		return Math.max(matrix[(n - 1)%3], matrix[(n-2)%3]);
	}

	public static void main(String [] args)
	{
		int [] blocks = new int[] {40,30,25,48,30,25,40};
		int ans = 135;
		int res;

		// int ans = 135;
		long start, end;

		// check linear runtime
		start = System.nanoTime();
		int result = RunLikeHell.maxGain(blocks);
		end = System.nanoTime();

		if (result != ans) System.out.println("Fail, result = " + result);
		else System.out.println("Success");
		System.out.println("Time = " + (end - start)) ;
	}

}
