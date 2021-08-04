// Pathogen.java
// Hussein Noureddine

// This backtracking algorithm checks and returns all
// possible paths out of a given maze.

import java.io.*;
import java.util.*;

public class Pathogen
{
	// Declaring hashset paths, original maze array, stringbuilder path
	private static HashSet<String> paths = new HashSet<String>();
	private static char [][] original;
	private static StringBuilder path = new StringBuilder("");

	// Used to toggle "animated" output on and off (for debugging purposes).
	private static int track = 0;
	private static boolean animationEnabled = false;

	// "Animation" frame rate (frames per second).
	private static double frameRate = 4.0;

	// Setters for amnimation
	public static void enableAnimation() { Pathogen.animationEnabled = true; }
	public static void disableAnimation() { Pathogen.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathogen.frameRate = fps; }

	// Maze constants.
	private static final char WALL       = '#';
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char BREADCRUMB = '.';  // visited
	private static final char SPACE      = ' ';  // unvisited
	private static final char COVID		 = '*';  

	// Takes a 2D char maze and returns a hashset of strings 
	// containing all paths as a string from start to exit.
	// If there is no path, an empty hashset is returned.
	public static HashSet<String> findPaths(char [][] maze)
	{
		// Saving # of rows and cols in height, width
		int height = maze.length;
		int width = maze[0].length;

		// Declaring visited array
		char [][] visited = new char[height][width];

		// used for original maze
		original = new char[height][width];

		// Initializing visited array
		for (int i = 0; i < height; i++)
			Arrays.fill(visited[i], SPACE);

		// Find starting position
		int startRow = -1;
		int startCol = -1;

		// Looks for location of '@'
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (maze[i][j] == PERSON)
				{
					startRow = i;
					startCol = j;
				}
			}
		}

		// Saving original maze in array original
		storeOriginal(maze);
		
		// calling method findPaths which returns a boolean result
		boolean result = findPaths(maze, visited, startRow, startCol, height, width);

		// After going thru the maze and changing its states,
		// restore it to its original state
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[0].length; j++)
				maze[i][j] = original[i][j];

		// return HashSet Paths after filling it with string paths
		// from recursive findPaths method
		return paths;
	}

	// This method goes through the maze and finds all possible paths from
	// start to exit, saves each path in a string, and adds it to the HashSet
	// paths. It returns a boolean result, true or false, if path(s) found.
	private static boolean findPaths(char [][] maze, char [][] visited,
	                                 int currentRow, int currentCol,
	                                 int height, int width)
	{
		// This conditional block prints the maze when a new move is made
		if (Pathogen.animationEnabled)
		{
			printAndWait(maze, height, width, "Searching...", Pathogen.frameRate);
		}

		// Checks if the exit is found in the current row & col
		if (visited[currentRow][currentCol] == 'e')
		{
			// for animation
			if (Pathogen.animationEnabled)
			{
				char [] widgets = {'|', '/', '-', '\\', '|', '/', '-', '\\',
				                   '|', '/', '-', '\\', '|', '/', '-', '\\', '|'};

				for (int i = 0; i < widgets.length; i++)
				{
					maze[currentRow][currentCol] = widgets[i];
					printAndWait(maze, height, width, "Hooray!", 12.0);
				}

				maze[currentRow][currentCol] = PERSON;
				printAndWait(visited, height, width, "Hooray!", Pathogen.frameRate);
			}

			// remove extra space character from end of stringbuilder
			path.delete(path.length() - 1, path.length());

			// save stringbuilder in a string "newString"
			String newString = path.toString();

			// add newString to the hashset "paths"
			paths.add(newString);

			// get rid of extra character
			path.delete(path.length() - 1, path.length());

			return true;
		}

		// Moves: left, right, up, down
		int [][] moves = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

		// This loop tries all the moves listed above until there
		// there are no more moves
		for (int i = 0; i < moves.length; i++)
		{
			int newRow = currentRow + moves[i][0]; // next row
			int newCol = currentCol + moves[i][1]; // next col
			
			// check move is in bounds, not a wall, not COVID, 
			// and not marked as visited.
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
				continue;

			// check if overwriting exit, if so, 
			// save it in visited array
			if (maze[newRow][newCol] == EXIT)
				visited[newRow][newCol] = EXIT;

			// mark currentRow & currentCol as visited
			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;

			// move person to newRow, newCol
			maze[newRow][newCol] = PERSON;

			// add direction taken with stringbuilder "path"
			if (i == 0) path.append("l ");
			if (i == 1) path.append("r ");
			if (i == 2) path.append("u ");
			if (i == 3) path.append("d ");

			// Perform recursive descent
			if (findPaths(maze, visited, newRow, newCol, height, width))
			{
				// if exit found, mark currentRow and col with a dash so 
				// that we can still find another path using this row & col
				visited[currentRow][currentCol] = '-';
				
				// check if there are still more moves remaining, if so,
				// continue and try those moves
				if (i <= 2)
				{
					// set maze back to original state
					for (int a = 0; a < maze.length; a++)
						for (int b = 0; b < maze[0].length; b++)
							maze[a][b] = original[a][b];

					// go to next move
					continue;
				}	

				// found
				return true;
			}

			// replace breadcrumb with dash so that we can still visit
			// row and column
			visited[currentRow][currentCol] = '-';

			// if backtracking, remove the added direction
			// from stringbuilder path
			if (path.length() != 0)
				path.delete(path.length() - 2, path.length());

			// undo state change
			maze[newRow][newCol] = BREADCRUMB;
			maze[currentRow][currentCol] = PERSON;

			// check if it backtracks to the start position, if so, 
			// start a completely new path
			if (original[currentRow][currentCol] == PERSON)
				path = new StringBuilder("");

			// this conditional block prints the maze when a move gets undone
			if (Pathogen.animationEnabled)
			{
				printAndWait(maze, height, width, "Backtracking...", frameRate);
			}
		}

		// not found
		return false;
	}

	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, it's not a wall, not a virus).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	                                   int row, int col, int height, int width)
	{
		// checks if row/col are in bounds of the maze
		if (row < 0  || row > height - 1 || col < 0 || col > width - 1)
			return false;

		// checks if row/col is a wall, breadcrumb, or virus
		if (maze[row][col] == WALL || visited[row][col] == BREADCRUMB || maze[row][col] == COVID)
			return false;

		// otherwise, it is a valid move
		return true;
	}

	// This effectively pauses the program for waitTimeInSeconds seconds
	private static void wait(double waitTimeInSeconds)
	{
		long startTime = System.nanoTime();
		long endTime = startTime + (long)(waitTimeInSeconds * 1e9);

		while (System.nanoTime() < endTime)
			;
	}

	// Prints maze and waits. frameRate is given in frames per second
	private static void printAndWait(char [][] maze, int height, int width,
	                                 String header, double frameRate)
	{
		if (header != null && !header.equals(""))
			System.out.println(header);

		if (height < 1 || width < 1)
			return;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(maze[i][j]);
			}

			System.out.println();
		}

		System.out.println();
		wait(1.0 / frameRate);
	}

	// This method stores the original state of the maze
	// in a 2D array called "original"
	private static void storeOriginal(char [][] maze)
	{
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[0].length; j++)
				original[i][j] = maze[i][j];
	}

	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above
	private static char [][] readMaze(String filename) throws IOException
	{
		// declaring scanner "in"
		Scanner in = new Scanner(new File(filename));

		// reading # of rows/cols from file
		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}
}
