/*
 * a node for a binary search tree (BST)
 */
public class BSTNode <T>{
 
		T key; /* This nodes key */
		BSTNode<T> left; //node to the left
		BSTNode<T> right; //node to the right
		
		BSTNode (T key, BSTNode<T> left, BSTNode<T> right) {
			this.key = key;
			this.left = left;
			this.right = right;
		}
		
		
}
