// SkipList.java
// Hussein Noureddine

// This program takes care of all changes occuring
// to a SkipList. It creates, searches, adds, deletes, 
// and grows and shrinks depending on changes. It runs
// in O(logn) runtime.

import java.util.*;
import java.io.*;

class Node<Any>
{
	int height;
	Any value;
	ArrayList<Node<Any>> refs;

	// This constructor creates a new node of height
	// "height" and initializes its references to null.
	Node(int height)
	{
		this.height = height;
		refs = new ArrayList<Node<Any>>();
		for (int i = 0; i < height; i++)
			refs.add(null);
	}

	// This constructor creates a new node of height "height",
	// initializes its refs to null, and initializes the node's 
	// value to the given "value".
	Node(Any data, int height)
	{
		this.height = height;
		this.value = data;
		refs = new ArrayList<Node<Any>>();
		for (int i = 0; i < height; i++)
			refs.add(null);
	}

	// This method returns the value stored in
	// the given node.
	public Any value()
	{
		return this.value;
	}

	// This method returns the height stored in 
	// the given node.
	public int height()
	{
		return this.height;
	}

	// This method returns the next node if the
	// given level is valid, otherwise it will
	// return null.
	public Node<Any> next(int level)
	{
		if (level < 0 || level > height - 1)
			return null;

		return refs.get(level);
	}

}

public class SkipList<Any extends Comparable<Any>>
{
	private int n = 0, mHeight = 0;
	private Node<Any> head, node;
	private boolean flag = false;

	// This constructor creates a new Skiplist,
	// since height is not given, it is automatically
	// initialized to a height of 1.
	SkipList()
	{
		head = new Node<Any>(1);
		mHeight = 1;
	}

	// This constructor creates a new Skiplist and
	// initializes its height to the given height. If
	// height < 1, it auto initializes the height to 1.
	SkipList(int height)
	{
		if (height < 1)
		{
			head = new Node<Any>(1);
			mHeight = 1;
		}

		else 
		{
			head = new Node<Any>(height);
			mHeight = height;
		}
	}

	// This method returns the size of the
	// skiplist (# of Nodes in the skiplist).
	public int size()
	{
		return n;
	}

	// This method returns height of the 
	// skiplist at the moment it's called. 
	public int height()
	{
		return mHeight;
	}

	// This method returns the head of the skiplist.
	public Node<Any> head()
	{
		return head;
	}

	// This method creates a new node of value "data"
	// and random height <= to the current height of the 
	// Skiplist and inserts it into the Skiplist.
	public void insert(Any data)
	{		
		int h = generateRandomHeight(mHeight); // generate random height
		
		node = new Node<Any>(data, h); // create new node

		// Grow size of skiplist (# of nodes) by 1.
		n++; 
		
		// If Skiplist is empty
		if (flag == false)
		{
			// point rest of head refs that are null to the new node,
			// point the new node's refs to null.
			for (int i = h - 1; i >= 0; i--)
				if (head.next(i) == null)
				{
					head.refs.set(i , node);
					node.refs.set(i, null);
				}

			// Skiplist is no longer empty.
			flag = true;
		}

		// Skiplist contains a node
		else 
		{	
			Node<Any> current = head;
			int i = mHeight - 1; // current height

			// This loop goes thru the Skiplist in O(logn) expected 
			// runtime until it finds the right place to insert node.
			while (i >= 0)
			{
				Node<Any> temp = current.next(i);

				// checks if the next node is null
				if (current.next(i) == null)
				{
					// checks if the current height is <=
					// to the new node's height and inserts 
					// the new node before the null reference.
					if (i <= h - 1)
					{
						node.refs.set(i, current.next(i));
						current.refs.set(i, node);
					}
					
					// Drop down one level
					i--;
				}

				// checks if data is <= to the next node 
				// within the list
				else if (data.compareTo(temp.value) <= 0)
				{
					// checks if the current height is <=
					// to the new node's height and inserts 
					// the new node after the current node.
					if (i <= h - 1)
					{
						node.refs.set(i, current.next(i));
						current.refs.set(i, node);
					}
					
					i--; // drop 1 level 
				}

				else // data > temp.value, go to the next node.
				{
					current = current.next(i);
				}
			}
		}

		int logHeight = getMaxHeight(n); // logbase2(n)
		
		// If logbase2(n) > current head node height,
		// grow the Skiplist by 1.
		if (logHeight > mHeight)
			growSkipList();
		
		return;
	}

	// This method creates a new node of value "data"
	// and of height "height" and inserts it into the Skiplist.
	public void insert(Any data, int height)
	{
		int h = height;
		
		node = new Node<Any>(data, h); // create new node

		// Grow size of skiplist (# of nodes) by 1.
		n++;
		
		// If Skiplist is empty
		if (flag == false)
		{
			// point rest of head refs that are null to the new node,
			// point the new node's refs to null.
			for (int i = h - 1; i >= 0; i--)
				if (head.next(i) == null)
				{
					head.refs.set(i , node);
					node.refs.set(i, null);
				}

			// Skiplist is no longer empty.
			flag = true;
		}

		// Skiplist contains a node
		else 
		{	
			Node<Any> current = head;
			int i = mHeight - 1;

			// This loop goes thru the Skiplist in O(logn) expected 
			// runtime until it finds the right place to insert node.
			while (i >= 0)
			{
				Node<Any> temp = current.next(i);

				// checks if the next node is null
				if (current.next(i) == null)
				{
					// checks if the current height is <=
					// to the new node's height and inserts 
					// the new node before the null reference.
					if (i <= h - 1)
					{
						node.refs.set(i, current.next(i));
						current.refs.set(i, node);
					}
					
					// drop down one level
					i--;
				}

				// checks if data is <= to the next node within
				// the list
				else if (data.compareTo(temp.value) <= 0)
				{
					// checks if the current height is <=
					// to the new node's height and inserts 
					// the new node after the current node.
					if (i <= h - 1)
					{
						node.refs.set(i, current.next(i));
						current.refs.set(i, node);
					}
					
					i--; // drop 1 level
				}

				else // data > temp.value, go to the next node.
				{
					current = current.next(i);
				}
			}
		}

		int logHeight = getMaxHeight(n); // logbase2(n)

		// If logbase2(n) > current head node's height,
		// grow the Skiplist by 1.
		if (logHeight > mHeight)
			growSkipList();

		return;	
	}
	
	// This method deletes the leftmost node containing
	// the value "data" in O(logn) expected runtime. If there
	// is no node containing "data", no change will occur.
	public void delete(Any data)
	{
		int i = mHeight - 1;

		// nothing has been deleted yet
		boolean deleted = false; 

		// this node calls the get method and sends "data" as a parameter.
		Node<Any> check = get(data);

		Node<Any> prev = head, temp = head.next(i);

		// if the Skiplist is empty or the node containing
		// data is not found, return.
		if (n == 0 || check == null)
			return;

		// store height of the node to be deleted in h.
		int h = check.height - 1; 

		// This loop goes thru the Skiplist in expected O(logn) runtime
		// until it finds the right node to be deleted, deletes it.
		while (i >= 0)
		{
			// checks if the next node is null OR if data < temp node's value
			if (temp == null || data.compareTo(temp.value) < 0)
			{
				// checks if i is on level 0
				if (i == 0)
					temp = prev.next(i); 

				else 
				{
					i--; // drop 1 level

					// move to next node on new level i
					temp = prev.next(i); 
				}
			}

			// checks if data is found in temp node
			else if (data.compareTo(temp.value) == 0)
			{
				// current level i > 0 and i > check node's height
				if (i > 0 && i > h)
				{
					i--; // drop 1 level

					// move to next node on new level i
					temp = prev.next(i);
					continue;
				}

				// checks if level i <= check node's height
				if (i <= h)
				{
					// move to next node
					temp = temp.next(i); 

					// point prev node to temp node
					prev.refs.set(i, temp);

					if (i > 0)
					{
						i--;

						// move to next node on new level i
						temp = prev.next(i);
					}

					// Successfully deleted node containing data
					else
					{
						deleted = true;
						break;
					}
				}
			}

			// checks if data > temp node's value
			else // > 0
			{
				// move nodes to next nodes on level i
				prev = temp; 
				temp = temp.next(i);
			}
		}

		// checks if a node is deleted
		if (deleted)
		{
			int logHeight = 1;
			n--; // Size - 1

			if (n > 1)
				 logHeight = getMaxHeight(n); // logbase2(n)

			// keep trimming Skiplist until logHeight is achieved
			while (logHeight < mHeight)
				trimSkipList();
		}

		return;
	}

	// This method searches the Skiplist for a node containing
	// "data" in O(logn) expected runtime and returns true if 
	// found, otherwise returns false.
	public boolean contains(Any data)
	{
		int i = mHeight - 1;

		Node<Any> prev = head;
		Node<Any> temp = head.next(i);

		// checks if list empty
		if (n == 0)
			return false; 
		
		// this loop goes thru the Skiplist until it either finds
		// the left most node containing "data" or until it falls 
		// of the skiplist (i = -1).
		while (i >= 0)
		{
			// checks if current node is null OR if data is smaller
			// than temp node's value
			if (temp == null || data.compareTo(temp.value) < 0)
			{
				// drop down 1 level
				i--;

				// checks if level is < 0, fall off
				if (i < 0)
					break;

				// move to next node on new level i
				temp = prev.next(i);
			}

			// checks if data is found in temp node
			else if (data.compareTo(temp.value) == 0)
			{
				// checks if i level is still > 0
				if (i > 0)
				{
					i--; // drop down 1

					// move to next node on new level i
					temp = prev.next(i);
				}

				// node containing "data" is found
				else return true;
			}

			// checks if data > temp node's value
			else // > 0
			{
				// move the nodes to the next nodes
				prev = temp;
				temp = temp.next(i);
			}
		}
		
		// no node contains "data"
		return false;
	}

	// This method returns a reference to a node containing 
	// the value "data", if not found, it returns null.
	public Node<Any> get(Any data)
	{
		int i = mHeight - 1;

		Node<Any> prev = head;
		Node<Any> temp = head.next(i);

		// checks if list is empty
		if (n == 0)
			return null; 
		
		// this loop goes thru the Skiplist until it either finds
		// the left most node containing "data" or until it falls 
		// of the skiplist (i = -1).
		while (i >= 0)
		{
			// checks if current node is null OR if data is smaller
			// than temp node's value
			if (temp == null || data.compareTo(temp.value) < 0)
			{
				// drop down 1 level
				i--;

				// checks if level is -1, fall off
				if (i < 0)
					break;

				// move to next node on new level i
				temp = prev.next(i);
			}

			// checks if data is found in temp node
			else if (data.compareTo(temp.value) == 0)
			{
				if (i > 0)
				{
					i--; // drop down 1

					// move to next node on new level i
					temp = prev.next(i);
				}

				// reached the left most node containing "data"
				else return temp; // return it
			}

			else // checks if data > temp node's value
			{
				// move the nodes to the next nodes
				prev = temp;
				temp = temp.next(i);
			}
		}

		// no node containing "data" found, return null
		return null;
	}

	// This method returns the ceiling of logbase2(# of nodes in Skiplist).
	private static int getMaxHeight(int n)
	{
		return (int)Math.ceil(Math.log(n) / Math.log(2));
	}

	// This method generates heights randomly up to maxHeight and returns it. 
	// It generates these heights based on h = 1 having 50% probability, h = 2
	// having half the previous probabilty and so on...
	private static int generateRandomHeight(int maxHeight)
	{
		int height = 1;

		while ((int)(Math.random() * 2) == 1 && height < maxHeight)
			height++;

		return height;
	}

	// This method grows the Skiplist by a height of 1 (grows the
	// head node by a height of 1) and gives a 50% to nodes that
	// have the previous maxHeight to also grow by 1 level.
	private void growSkipList()
	{
		int i = mHeight; // previous maxHeight
		mHeight++; // new maxHeight
		Node<Any> temp = head.next(i - 1);

		// grow head node's height by 1
		head.height++; 

		// if there are no nodes at level i,
		// point the head at new maxHeight lvl to null
		if (temp == null)
			head.refs.add(head.height - 1, null);

		// there are nodes at level i
		else
		{
			Node<Any> prev = head;

			// add a new reference to null at new maxHeight level
			prev.refs.add(mHeight - 1, null);
		
			// traverse thru previous maxHeight level until the 
			// last node points to null 
			while (temp != null)
			{
				// give a 50% chance to each node at this level
				// to grow by a height of 1
				if (Math.random() > 0.5)
				{
					temp.height++; // grow by 1

					// point the prev node at Max level to current node
					// and point current node at Max level to null
					prev.refs.set(mHeight - 1, temp);
					temp.refs.add(mHeight - 1, null);
				}

				// move to next node at prev maxHeight level (i)
				temp = temp.next(i - 1); 

				// if no growth occurs continue to next iteration
				if (prev.next(head.height - 1) == null)
					continue;

				// move to next node at new maxHeight level (i)
				prev = prev.next(head.height - 1);
			}
		}

		return;
	}

	// This method trims the whole Skiplist by 1 level. 
	// It shortens nodes with current maxHeight by 1 level.
	private void trimSkipList()
	{
		int i = mHeight - 1; // current maxHeight
		mHeight--; // new maxHeight
		Node<Any> temp = head.next(i);

		// if there are no nodes at current maxHeight level,
		// decrease the head node's height
		if (temp == null)
		{
			head.height--;
			return;
		}

		// there are nodes at current maxHeight level
		else
		{
			Node<Any> current = temp;

			// while next node is not null,
			// make it null and reduce height by 1.
			while (temp != null)
			{
				temp = temp.next(i);
				current.refs.set(i, null);
				current.height--;
				head.refs.set(i, temp);
				current = temp;
			}

			// all nodes at maxHeight level are now 
			// null and less in height, decrease the 
			// head node's height by 1
			head.height--;
		}

		return;
	}

}
