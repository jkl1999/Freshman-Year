
public class TestingGenericLinkedList {

	public static void main(String[] args) {
		
		LinkedList<String> songs = new LinkedList<String>();
		
		songs.addToFront("Zombies");
		songs.addToFront("linger");
		songs.addToFront("Shape of You");
		songs.addToFront("Thriller");
		songs.addToFront("gods plan");
		songs.traverse();
		
		LinkedList<Integer> numbers = new LinkedList<Integer>();
		
		//numbers.deleteFront();
		numbers.addToFront(6);
		numbers.addToFront(4);
		numbers.addToFront(2);
		numbers.traverse();
		
		

	}

}
