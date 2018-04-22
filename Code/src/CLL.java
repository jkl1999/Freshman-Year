//Circular linked list
public class CLL <T>{
	
	Node<T> tail;//referance to end of the linked list
	
	int size;

	CLL (){
		tail = null;
		size = 0;
	}
	
	public void addToFront(T data) {
		
		Node<T> node = new Node(data, null);
		
		if(tail == null) {
			//empty LL
			node.next = node; // only node points to itself
			tail = node;
		} else {
			node.next = tail.next; //make new node point to the current first node
			tail.next = node; //make tail point to the new front
			
		}
		size +=1;
		
		
	}
	
	public void traverse() {
		if(tail == null) {
			System.out.println("list is empty");
		} else {
			Node<T> ptr = tail.next; // start ptr at front of the list
			do {
				System.out.print(ptr.data + " ");
				ptr = ptr.next;
			} while (ptr != tail.next);
		}
		
	}
	public T removeFront() {
		
		if(tail == null || tail == tail.next) {
			//empty list or one node
			size = 0;
			T data = null;
			if(tail != null) {
				//one element
				data = tail.data;
				}
			tail = null;
			return data;
		}
		else {
			T data = tail.next.data;
			tail.next = tail.next.next;
			size -= 1;
			return data;
		}
		
	}
	
	
	public static void main(String [] args) {
		CLL<Integer> cll = new CLL<Integer>();
		cll.addToFront(3);
		cll.addToFront(2);
		cll.addToFront(1);
		cll.addToFront(0);
		cll.traverse();
		cll.removeFront();
		cll.traverse();
	}
	
}
