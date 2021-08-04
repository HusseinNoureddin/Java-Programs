// ConstrainedTopoSort.java
// Hussein Noureddine

// This program verifies whether the given graph contains
// a valid topological sort in which x can come before y.

import java.util.*;
import java.io.*;

public class ConstrainedTopoSort
{
	// global class variable (# of vertices) & array "matrix"
	int [][] matrix;
	int n = 0;

	// This constructor reads the graph from the input file as an 
	// adjacency list and stores it in an adjacency matrix "matrix"
	public ConstrainedTopoSort(String filename) throws IOException
	{
		// using scanner "input" to read from file
		Scanner input = new Scanner(new File(filename));

		n = input.nextInt(); // # of vertices
		matrix = new int[n][n];
		int k = 0;

		for (int i = 0; i < n; i++)
		{
			// reads how many vertices each vertex is adjacent to
			k = input.nextInt();

			if (k > 0)
			{
				// store k vertices in matrix at row i
				for (int j = 0; j < k; j++)
				{
					int vertex = input.nextInt();
					matrix[i][vertex - 1] = 1; 
				}
			}
		}
	}

	// This method returns true if there exists a single
	// valid topological sort where vertex x can come
	// before vertex y.
	public boolean hasConstrainedTopoSort(int x, int y) 
	{
		int cnt = 0, temp = 0, j = 0;

		// if x or y are out of bounds
		if (x < 1 || x > n || y < 1 || y > n)
			return false;

		// if there is a cycle in the graph, return false
		if (checkCycle()) 
			return false;
		
		// if vertices x,y are the same, return false
		if (x == y)
			return false;

		// if vertex x is adjacent (points to) to vertex y
		if (matrix[x - 1][y - 1] == 1)
			return true;

		// if vertex y is adjacent (points to) vertex x
		if (matrix[y - 1][x - 1] == 1)
			return false;

		// calls validSort method, if there exists a sort
		// where x can come before y, return result = true
		boolean result = validSort(x - 1, y - 1);

		return result;
	}

	// Slightly modified BFS from Graph.java.
	// This method does a BFS starting from vertex start (y - 1),
	// returns false if x comes after y, else returns true.
	public boolean validSort(int x, int start)
	{
		// declaring a queue "q" of LinkedList 
		// and boolean array "visited"
		Queue<Integer> q = new LinkedList<Integer>();
		boolean [] visited = new boolean[matrix.length];
		
		// adds "start" to the q and marks it as visited
		q.add(start);
		visited[start] = true;
		
		// this loop does a BFS while the q is not empty,
		// starting at vertex start (y - 1)
		while (!q.isEmpty())
		{
			// remove node from q
			int node = q.remove();

			// if x = node then that means that x comes after
			// start (y), meaning that x can never come before y
			if (x == node)
				return false;
			
			// this loop iterates thru the matrix and adds
			// non-visited vertices that are adjacent to node
			// to the q
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] == 1 && !visited[i])
				{
					visited[i] = true;
					q.add(i);
				}
		}

		// returns true if x never comes after y, x-> y
		return true;
	}

	// Slightly modified topological sort from toposort.java.
	// This method completes one topological sort
	// on the current graph and returns true if there is a cycle.
	public boolean checkCycle()
	{
		// declaring array "incoming" & cnt variable
		int [] incoming = new int[matrix.length];
		int cnt = 0;

		// this loop iterates thru the matrix and stores
		// the # of incoming vertices to each vertex
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				incoming[j] += matrix[i][j];

		// declaring queue "q"
		Queue<Integer> q = new ArrayDeque<Integer>();

		// this loop adds all source vertices to the q
		for (int i = 0; i < matrix.length; i++)
			if (incoming[i] == 0)
				q.add(i);
	
		// this loop removes a node from the q and 
		// keeps looping while the q is not empty
		while (!q.isEmpty())
		{
			// remove vertex from q and store in "node"
			int node = q.remove();

			// keeps track of # of nodes 
			++cnt;

			// this loop iterates thru the matrix and adds
			// each vertex that is adjacent to "node" AND 
			// has no more incoming edges to the q
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] == 1 && --incoming[i] == 0)
					q.add(i);
		}

		// if cnt != # of vertices, return true that there
		// exists a cycle in the graph
		if (cnt != matrix.length)
			return true;

		// returns false if there is no cycle in the graph
		return false;
	}
}
