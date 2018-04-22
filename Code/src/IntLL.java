
public class IntLL {
	
	public static IntNode addFront(IntNode front, int data) {
		IntNode node = new IntNode(data, front);
		return node;
	}
	public static void traverse (IntNode front) {
		IntNode ptr = front;
		while (ptr != null) {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		}
		System.out.println("\\");
	}
	public static IntNode removeFront(IntNode front) {
		if (front == null) {
			System.out.println("empty");
			return front;			
		}
		else {return front.next;}
	}
	
	public static boolean Search(IntNode front, int target) {
		for(IntNode ptr = front; ptr !=null; ptr=ptr.next) {
			if(ptr.data == target) {
				return true;
			}
		} return false;
	}
	
	public static IntNode addToBack (IntNode front, int data) {
		if (front == null) {
			return addFront(front, data);
		} else {
			IntNode ptr = front;
			while (ptr.next != null) {
				ptr = ptr.next;
			}
			// ptr is pointing to the last element of the LL
			IntNode node = new IntNode (data, null);
			ptr.next = node; // make the last element point to the new last element (node)
			return front;
		}
	}

	
	public static boolean addAfter(IntNode front, int target, int data) {
		for(IntNode ptr = front; ptr != null; ptr=ptr.next) {
			if(ptr.data==target) {
				//found target
				IntNode node = new IntNode(data, ptr.next);
				ptr.next = node;
				return true;
			}
		}
		return false;
	}
	
	public static IntNode delete(IntNode front, int target) {
		IntNode ptr = front;
		IntNode prev = null;
		
		while(ptr != null && ptr.data != target) {
			prev = ptr; //prev point to the same node ptr is pointing to, move ptr ahead 
			ptr = ptr.next;
			
		}
		
		if(ptr == null) {
			//target not fount
			return front;
		} else if (ptr == front){
			//target is the front
			return front.next;
		} else {
			//delete from the middle
			prev.next = ptr.next;
			return front;
		}
		
	}
	
	public static IntNode addBefore(IntNode front, int target, int newItem) {
		IntNode prev = null;
		IntNode ptr = front;
		while (ptr != null && ptr.data != target) {
			prev = ptr;
			ptr = ptr.next;
		}
		if (ptr == null) {
			//not found
			return front;
		}
		IntNode node = new IntNode (newItem, ptr);
		if(prev == null) {
			return node;
		}
		prev.next = node;
		return front;
		
	}
	
	public static IntNode addBeforeLast(IntNode front, int item) {
		IntNode prev = front;
		IntNode ptr = front;
		
		if(front == null) {
			return null;
		}
		while (ptr.next != null) {
			prev = ptr;
			ptr =ptr.next;
		}
		IntNode node = new IntNode(item, ptr);
		if(prev == null) {
			return node;
		}
		prev.next = node;
		return front;
	}
	
	public static void main(String[] args) {
		
		IntNode F =  new IntNode(5, null);
		traverse(F);
		F = removeFront(F);
		F = removeFront(F);
		F = addFront(F, 3);
		traverse(F);
		F = addFront(F, 2);
		traverse(F);
		F = addFront(F,1);
		System.out.println(Search(F, 2));
		System.out.println(Search(F, 9));
		F = addToBack(F, 17);
		traverse(F);
		addAfter(F, 3, 5);
		traverse(F);
		addBefore(F,5,9);
		traverse(F);
		addBeforeLast(F,4);
		traverse(F);
	}

}
