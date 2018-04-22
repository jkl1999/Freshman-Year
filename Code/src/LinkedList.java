import java.util.NoSuchElementException;

/*
 * Generic Linked List
 */
public class LinkedList <T> {
	
	Node<T> front; //points to first item in the linked list
	
	int size;

	LinkedList (){
		front = null;
		size = 0;
	}
	
	public void addToFront(T data) {
		front = new Node<T>(data, front);
		size += 1;
	}
	
	public void traverse () {
		for (Node<T> ptr = front; ptr != null; ptr=ptr.next) {
			System.out.print(ptr.data + " --> ");
		}
		System.out.println("\\");
	}
	
	public T deleteFront() {
		if (front == null) {
			//list is empty
			throw new NoSuchElementException("list is empty");
		}
		T frontData = front.data; // save data
		front = front.next; //removed first node
		size -= 1;
		return frontData;
	}
	
}
