
public class DLLNode <T>{
	
	T data;
	DLLNode<T> next; //points to next node in LL
	DLLNode<T> prev; //points to previous node in the LL
	
	DLLNode(T data, DLLNode<T> next, DLLNode <T> prev){
		this.data = data;
		this.next = next;
		this.prev = prev;
	}
	
	
	
	
	

}
