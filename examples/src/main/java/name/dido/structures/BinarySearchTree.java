package name.dido.structures;

import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
	
	private class BinarySearchTreeNode<N extends Comparable<N>> extends Node<N> {

		BinarySearchTreeNode<N> left;
		BinarySearchTreeNode<N> right;
		
		public BinarySearchTreeNode(N value) {
			super(value);
		}
		
	}	
	
	private BinarySearchTreeNode<T> root;	
	
	private BinarySearchTree() {
		super();
	}
	
	public static <T extends Comparable<T>> BinarySearchTree<T> emptyTree() {
		return new BinarySearchTree<T>();
	}
	
	/**
	 * 
	 * @param value
	 * @return true if inserted, false otherwise
	 */
	public boolean insert(T value) {
		return insertAt(root, value, treeNode -> root = treeNode) != null;
	}
	
	public void traverse(Visitor<T> visitor) {
		traverse(root, visitor);
	}

	private void traverse(BinarySearchTree<T>.BinarySearchTreeNode<T> node, Visitor<T> visitor) {
		if (node == null) {
			return;
		}
		traverse(node.left, visitor);
		visitor.accept(node.getValue());
		traverse(node.right, visitor);
	}

	/**
	 * Finds an insertion point under the parent and inserts the value
	 * @param parent
	 * @param value
	 * @return the insertion point or null if the value exists
	 */
	private BinarySearchTreeNode<T> insertAt(final BinarySearchTreeNode<T> node, final T value,
			Consumer<BinarySearchTreeNode<T>> parentReferenceSetter) {
		if (node == null) {
			BinarySearchTreeNode<T> newNode = new BinarySearchTreeNode<T>(value);
			parentReferenceSetter.accept(newNode);
			return newNode;
		} else {
			int compareResult = value.compareTo(node.getValue());
			if (compareResult == 0) {
				return null;
			} else {
				BinarySearchTreeNode<T> child;
				Consumer<BinarySearchTreeNode<T>> childReferenceSetter;
				if (compareResult < 0) {
					child = node.left;
					childReferenceSetter = treeNode -> node.left = treeNode;
				} else {
					child = node.right;
					childReferenceSetter = treeNode -> node.right = treeNode;
				}
				return insertAt(child, value, childReferenceSetter);
			}
		}
	}
	
}
