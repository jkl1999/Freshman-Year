
public class TestingStringLL {

	public static void main(String[] args) {
		
		StringLL todo = new StringLL();
		
		todo.addToFront("laser");
		todo.addToFront("assignment CS111");

		StringLL grocery = new StringLL();
		
		grocery.addToFront("vegetables");
		grocery.addToFront("fruit");
		grocery.addToFront("milk");
		
		todo.traverse();
		grocery.traverse();
		
		
	}

}
