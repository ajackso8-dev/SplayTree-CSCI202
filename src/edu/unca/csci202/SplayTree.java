package edu.unca.csci202;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * SplayTree implementation, implements the BinarySearchTreeADT interface.
 * @author Aaron Jackson
 * @version 1.5
 *
 * @param <T> generic type.
 */
public class SplayTree<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
	
	/**
	 * private sub-class to handle SplayTree nodes and their behavior.
	 * @author Aaron Jackson
	 *
	 * @param <N> genetic type.
	 */
	private class Node<N extends Comparable<N>> {
		/* instance variables */
		private N data;
		private Node<N> left, right, parent;
		
		/* constructor */
		public Node(N data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
		}
	}
	
	/* instance variables */
	private Node<T> root;
	private int size;
	
	/* constructors */
	public SplayTree() {
		this.root = null; // set to empty SplayTree
		this.size = 0;
	}

	
	public SplayTree(T data) {
		this.root = new Node<T>(data);
		this.size = 1;
	}

	
	@Override
	public T getRootElement() {
		return this.root.data;
	}

	
	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	
	@Override
	public boolean contains(T targetElement) {
		return find(targetElement) != null;
	}

	
	@Override
	public T find(T targetElement) {
		if(!isEmpty()) {
			Node<T> node = this.find(this.root, targetElement);
			try {
				return node.data;
			} catch (NullPointerException e) {
				return null; // the element was not in the tree.
			}
		}
		return null; // tree is empty.
	}
	
	/**
	 * private subroutine finder method that recurses through the SplayTree to find the targetElement.
	 * It will play the splay method to the found node.
	 * @param node initial node to start searching the tree.
	 * @param targetElement the element to search for.
	 * @return node that equals the targetElement in the tree.
	 */
	private Node<T> find(Node<T> node, T targetElement) {
		if(node.data.equals(targetElement)) {
			splay(node); // splay method.
			return node; // found it, return
		}
		
		Node<T> temp;
		if(node.left != null) {
			temp = this.find(node.left, targetElement); // recurse left
			if(temp != null) {
				return temp;
			}
		}
		
		if(node.right != null) {
			temp = this.find(node.right, targetElement); // recurse right
			if(temp != null) {
				return temp;
			}
		}
		
		return null; // not found in our sub tree
	}

	
	@Override
	public Iterator<T> iterator() {
		return iteratorInOrder();
	}

	
	@Override
	public Iterator<T> iteratorInOrder() {
		ArrayList<T> list = new ArrayList<T>();
		traverseInOrder(this.root, list);
		return list.iterator();
	}
	
	/**
	 * Traverses the SplayTree in a InOrder fashion, and adds the visited nodes into a ArrayList
	 * @param node initial node to start traversing.
	 * @param list ArrayList to add the vistited nodes too.
	 */
	private void traverseInOrder(Node<T> node, ArrayList<T> list) {
		if(node != null) {
			this.traverseInOrder(node.left, list); // recurse left
			list.add(node.data); // visit node
			this.traverseInOrder(node.right, list); // recurse right
		}
	}

	
	@Override
	public int insert(T element) {
		this.size++;
		int numOfEdges = 0;
		Node<T> node = new Node<T>(element); // insert node.
		Node<T> tmp = this.root;
		Node<T> parent = null; // parent pointer.
		while(tmp != null) {
			parent = tmp;
			if(node.data.compareTo(tmp.data) < 0) {
				tmp = tmp.left; // insert < node.
				numOfEdges++;
			} else {
				tmp = tmp.right; // insert > node.
				numOfEdges++;
			}
		}
		node.parent = parent; // set insert's new parent to the parent pointer.
		if(parent == null) {
			this.root = node; // parent was root.
		} else if(node.data.compareTo(parent.data) < 0) {
			parent.left = node; // parent's left child.
		} else {
			parent.right = node; // parent's right child.
		}
		
		splay(node); // splay method.
		return numOfEdges;
	}

	
	@Override
	public int height() {
		if(isEmpty()) {
			return 0;
		}
		return height(this.root, 1);
	}
	
	/**
	 * private helper height method, 
	 * counts nodes down the left/right sub-trees until it reaches the end of the SplayTree 
	 * and returns the total height of the SplayTree.
	 * @param node initial node to start the count.
	 * @param count the count vaule to be used my the recursive method calls.
	 * @return the maximum tree height.
	 */
	private int height(Node<T> node, int count) {
		Node<T> left = node.left, right = node.right;
		
		if(left == null && right == null) {
			return count; // at the end of tree.
		}
		
		if(left != null) {
			return this.height(node.left, count+1); // recurse left
		}
		
		if(right != null) {
			return this.height(node.right, count+1); // recurse right
		}
		
		return 0; // tree is empty.
	}

	
	@Override
	public T maximum() {
		if(isEmpty()) {
			return null;
		}
		Node<T> max = maximum(this.root);
		if(max != null) {
			splay(max); // splay method.
			return max.data;
		}
		return null; // max not found.
	}
	
	
	private Node<T> maximum(Node<T> node) {
		while(node.right != null) {
			node = node.right; // loop right sub-tree.
		}
		return node;
	}

	
	@Override
	public T minimum() {
		if(isEmpty()) {
			return null;
		}
		Node<T> min = minimum(this.root);
		if(min != null) {
			splay(min); // splay tree
			return min.data;
		}
		return null; // min not found.
	}
	
	/**
	 * private helper method that loops through the far left sub-tree.
	 * @param node node to find the minimum value of.
	 * @return the minimum node to the far left of the left sub-tree.
	 */
	private Node<T> minimum(Node<T> node) {
		while(node.left != null) {
			node = node.left; // loop left sub-tree. 
		}
		return node;
	}

	
	@Override
	public void delete(T element) {
		Node<T> node = find(this.root, element); // check if element exists in tree.
		if(isEmpty()) {
			System.out.println("error: tree is empty.");
		} else if(node == null){
			System.out.println("error: element does not exist.");
		} else {
			delete(node);
			size--;
		}
	}
	
	/**
	 * private helper method that removes and re-arranges the SplayTree.
	 * @param node node to delete from the SplayTree.
	 */
	private void delete(Node<T> node) {
		if(node.left == null) { // case 1: node has no left child.
			transplant(node, node.right);
		} else if(node.right == null) { // case 2: node has no right child.
			transplant(node, node.left);
		} else { // case 3: node has both children.
			Node<T> successor = minimum(node.right);
			if(successor.parent != node) { // case 3b: successor is elsewhere
				transplant(successor, successor.right);
				successor.right = node.right;
				successor.right.parent = successor;
			}
			// case 3a: successor is right child.
			transplant(node, successor);
			successor.left = node.left;
			successor.left.parent = successor;
		}
	}
	
	/**
	 * Replaces/switches a specified node with another while maintaining their respective sub-trees.
	 * @param a node to be replaced.
	 * @param b replacement node.
	 */
	private void transplant(Node<T> a, Node<T> b) {
		if(a.parent == null) { // a is root.
			this.root = b;
		} else if(a.parent.left == a) { // a is left child.
			a.parent.left = b;
		} else { // a is right child.
			a.parent.right = b;
		}
		if(b != null) { // set b's parent links
			b.parent = a.parent;
		}
	}
	
	/**
	 * Specific to SplayTree, it is invoked whenever a node from the tree is accessed. It takes a specified node and moves it
	 * to the top of the tree; re-arranging the tree through rotations.
	 * @param node node to move to top of SplayTree.
	 */
	private void splay(Node<T> node) {
		while(node.parent != null) {
			if(node.parent == this.root) { // root is one edge away.
				if(this.root.left == node) { // node is left child.
					rightRotate(this.root);
				} else { // node is right child.
					leftRotate(this.root);
				}
			} else {
				Node<T> parent = node.parent;
				Node<T> grandParent = parent.parent;
				if(grandParent.left == parent) { // node's parent is a left child
					if(parent.left == node) { // node is a left child
						/* left-left case */
						rightRotate(grandParent);
						rightRotate(parent);
					} else if(parent.right == node){ // node is a right child
						/* left-right case */
						leftRotate(parent);
						rightRotate(node.parent);
					}
				} else if (grandParent.right == parent) { // node's parent is a right child
					if(parent.right == node) { // node is a right child
						/* right-right case */
						leftRotate(grandParent);
						leftRotate(parent);
					} else if(parent.left == node){ // node is a left child
						/* right-left case */
						rightRotate(parent);
						leftRotate(grandParent);
					}
				}
			}
		}
    }
	
	/**
	 * Performs a left rotation on a specified node.
	 * @param node node to perform the left rotation on.
	 */
	private void leftRotate(Node<T> node) {
		Node<T> tmp = node.right; 
		node.right = tmp.left; // move tmp's left subtree to become node's right.
		if(tmp.left != null) {
			tmp.left.parent = node;
		}
		tmp.parent = node.parent; // node's old parent becomes tmp's new parent.
		if(node.parent == null) {
			this.root = tmp; // set tmp to root.
		} else if (node == node.parent.left) {
			node.parent.left = tmp;
		} else {
			node.parent.right = tmp;
		}
		// tmp becomes parent of node, node is now left child.
		tmp.left = node;
		node.parent = tmp;
	}
	
	/**
	 * Performs a right rotation on a specified node.
	 * @param node node to perform the left rotation on.
	 */
	private void rightRotate(Node<T> node) {
		Node<T> tmp = node.left;
		node.left = tmp.right; // move tmp's left subtree to become node's left.
		if(tmp.right != null) {
			tmp.right.parent = node;
		}
		tmp.parent = node.parent; // node's old parent becomes tmp's new parent.
		if(node.parent == null) {
			this.root = tmp; // set tmp to root.
		} else if (node == node.parent.right){
			node.parent.right = tmp;
		} else {
			node.parent.left = tmp;
		}
		// tmp becomes parent of node, node is now right child.
		tmp.right = node;
		node.parent = tmp;
	}

	
	@Override
	public int size() {
		return this.size();
	}
	
	
	public String toString() {
		return print(this.root, 0);
	}
	
	/**
	 * Returns a String of the binary tree in 'tree' format.
	 * @param node starting node.
	 * @param level the level the starting node is located within.
	 * @return a String of the binary tree in 'tree' format.
	 */
	private String print(Node<T> node, int level) {
		String ret = "";
		if(node != null) {
			for(int i = 0; i < level; i++) { // indent based on level.
				ret += "\t";
			}
			/* add in node's data. */
			ret += node.data + "\n"; // return to next line.
			ret += this.print(node.right, level + 1); // recurse right
			ret += this.print(node.left, level + 1); // recurse left
		}
		return ret;
	}
	
	/**
	 * Main method used for testing purposes.
	 * @param args main arguments.
	 */
	public static void main(String[] args) {
		SplayTree<Integer> bst = new SplayTree<Integer>();
		
		int[] numbers = {8, 5, 9, 4, 3, 10, 5, 7};
		for(int i=0;i<numbers.length;i++) {
			bst.insert(numbers[i]);
		}
	}

	
	@Override
	public Iterator<T> iteratorPreOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<T> iteratorPostOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<T> iteratorLevelOrder() {
		// TODO Auto-generated method stub
		return null;
	}
}
