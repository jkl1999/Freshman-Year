import java.util.NoSuchElementException;

public class ParentMatch {
	
	public static boolean parentMatch(String expression) {
		Stack<Character> stack = new Stack<Character>();
		
		// Scan the expression: push '(' and pop ')'
		
		for(int i= 0; i < expression.length(); i++) {
			char ch = expression.charAt(i);
			if(ch == '(' ) {
				stack.push(ch);
			} else if ( ch == ')') {
				
				try {
				stack.pop(); // may throw exception
				} catch (NoSuchElementException e) {
					//how to handle exception
					return false; //expr doesnt have matching parenthesis
				}
			}
		}
		return stack.isEmpty();
		
	}

	public static void main(String[] args) {
		
		String expr = "(a + b) * c";
		String expr2 = "(a + b *c";
		String expr3 = "(a+b * c))";
		
		if(parentMatch(expr3)) {
			System.out.println(expr3 + " match");		
		} else {
			System.out.println(expr3 + " not matched");
		}
		
		
		
	}

}
