// Hussein Noureddine
// ===========================
// GenericBST: GenericBST.java
// ===========================
// Basic generic binary search tree (BST) implementation that supports insert() and
// delete() operations.

import java.io.*;
import java.util.*;

class Node<Something>
{
	Something data;
	Node<Something> left, right;

	Node(Something data)
	{
		this.data = data;
	}
}

public class GenericBST<Any extends Comparable<Any>>
{
	private Node<Any> root;

	public void insert(Any data)
	{
		root = insert(root, data);
	}

	// This method inserts value stored in data into BST, 
	// returns the root of the BST.
	private Node<Any> insert(Node<Any> root, Any data)
	{
		if (root == null)
		{
			return new Node<Any>(data);
		}

		// compares what's stored in data to root.data,
		// if -(ive), insert data in left side of BST,
		// if +(ive), insert data in right side of BST.
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}
		
		return root;
	}

	public void delete(Any data)
	{
		root = delete(root, data);
	}

	// This method deletes a node (if present) from the
	// BST, depending on the case, it returns null or
	// the BST's root.
	private Node<Any> delete(Node<Any> root, Any data)
	{
		if (root == null)
		{
			return null;
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}

		// if data = root.data, make root.right or root.left or 
		// root = null or (next)root.right or (next)root.left 
		// by returning them depending on if/else cases below.
		else
		{
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private Any findMax(Node<Any> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	public boolean contains(Any data)
	{
		return contains(root, data);
	}

	// This method looks for a node containing the value in
	// data, if it does, it returns true, otherwise returns false.
	private boolean contains(Node<Any> root, Any data)
	{
		if (root == null)
		{
			return false;
		}
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		} 
	}

	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// This method prints the contents of the BST
	// in inorder traversal.
	private void inorder(Node<Any> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// This method prints the contents of the BST
	// in preorder traversal.
	private void preorder(Node<Any> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// This method prints the contents of the BST
	// in postorder traversal.
	private void postorder(Node<Any> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
}
