
public class DLL <T>{

	DLLNode<T> front;
	int size;
	DLL () {
		front = null;
		size = 0;
	}
	
	public void addToFront( T data) {
		DLLNode<T> node = new DLLNode<T>(data, front, null);
		
		if(front != null) {
			//LL not empty, make current front point to node
			front.prev = node;
		}
		front = node;
		size+=1;
	}
	public void traverse() {
		for(DLLNode<T> ptr = front; ptr != null; ptr = ptr.next) {
			System.out.print(ptr.data + "->");
		}
		System.out.println();
	}
	
	//implement a delete method
	
	
	public static void main(String[] args) {
		DLL<String> dll = new DLL<String>();
		
		dll.addToFront("watermelon");
		dll.addToFront("banana");
		dll.addToFront("apple");
		dll.addToFront("tomato");
		dll.traverse();

	}

}
