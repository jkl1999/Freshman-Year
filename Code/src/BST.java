import java.util.NoSuchElementException;

/*
 * Binary Search Tree (BST)
 * Duplicates are not allowed for this example
 * 
 * 
 * Comparable (is and interface) requires T class to implement
 * compareTo method
 * 
 */
public class BST <T extends Comparable <T>> {
	
	BSTNode<T> root;
	int size;
	
	BST (){ //Initializes BST to be an empty tree
		root = null;
		size = 0;
	}
	
	/*
	 * searches for a key in the BST, returns the entire node
	 * that contains the key
	 */
	
	public BSTNode<T> search(T target){
		BSTNode<T> ptr = root;
		
		while(ptr != null) {
			int c =  target.compareTo(ptr.key);
			
			if(c==0) {
				return ptr; //found target
			}
			else if(c<0) {
				ptr = ptr.left; //target is smaller then ptr.key
			}
			else if(c>0) {
				ptr = ptr.right; //target is greater than ptr.key
			}
		}
		return null; //target not found
	}
	
	public void insert (T key) {
		// 1. search to fail
		BSTNode<T> ptr = root;
		BSTNode<T> prev = null;
		int c = 0;
		
		while (ptr!=null) {
			c = key.compareTo(ptr.key);
			if(c==0) {
				// no duplicates allowed so we must throw an exception
				throw new IllegalArgumentException(key + "already in BST");
			}
			prev = ptr;
			
			if(c<0) { //key is smaller than ptr.key
				ptr = ptr.left;
			} else { // key is larger than ptr.key
				ptr = ptr.right;
			}
		}
		//2. insert key as one of prev children
		BSTNode<T> node = new BSTNode<T>(key, null, null);
		if(prev == null) {
			// empty tree
			root = node;
		} else if(c<0) {
			prev.left = node;
		} else {
			prev.right = node;
		}
		
		size++;
		
	}
	
	public static <T extends Comparable<T>>void inOrderTraversal(BSTNode<T> root) {
			if(root==null){
				return;
			}
			inOrderTraversal(root.left);
			System.out.print(root.key + " ");
			inOrderTraversal(root.right);
	}
	
	public void delete(T key) {
		//1. find the node to delete: x
		
		BSTNode<T> x = this.root;
		BSTNode<T> p = null;
		
		int c =0;
		while(x!=null) {
			c = key.compareTo(x.key);
			if(c==0) {
				break; //found x
			}
			p = x;
			x = c < 0 ? x.left : x.right; //if c<0 then x = x.left, otherwise x = x.right
			
		}
		//2. if x is not found
		if(x == null) {
			throw new NoSuchElementException(key + "not found");
		}
		//3. check case 3 where x has two children
		//find x in order predesesor (largest value in left subtree
		BSTNode<T> y = null;
		if(x.left !=null && x.right != null) {
			y = x.left; // take a left
			p = x;
			while(y.right != null) {
				//go all the way to the right
				p = y;
				y = y.right;
			}
			//copy y data and key into x
			x.key = y.key;
			
			//prepare to delete y
			
			x = y;
			
		}
		// 4. check if p is null if so x is the root
		// and has only one or no child
		if(p == null) {
			root = x.left != null ? x.left : x.right;
			size --;
			return;
		}
		//5. handle case 1 and 2 in the same code
		//also handle removing y from case 3
		BSTNode<T> tmp = (x.right != null) ? x.right : x.left;
		
		if(x == p.left) {
			p.left = tmp;
		} else {
			p.right = tmp;
		}
		size -= 1;
		
	}
	
	public static void main (String[] args) {
		BST<Integer> bst = new BST<Integer>();
		bst.insert(20);
		bst.insert(10);
		bst.insert(30);
		bst.insert(5);
		bst.insert(35);
		
		int key = 15;
		
		BSTNode <Integer> node = bst.search(key);
		if(node == null) {
			System.out.println(key+"not in bst");
		}else {
			System.out.println(key+"in bst");
		}
	}

}
