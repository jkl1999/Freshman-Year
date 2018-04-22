/*
 * generic Node Class
 */
public class Node <T> {

	T data; //generic data (String, Integer, Float)
	
	Node <T> next; // points to next node in the linked list
	
	Node (T data, Node<T> next){
		this.data = data;
		this.next = next;
	}
	
	
}
