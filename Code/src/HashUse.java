import java.util.HashMap;

class Person {
	String firstName, lastName;
	String email;
	
	Person(String firstName, String lastName, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Person)) {
			return false;
		}
		Person other = (Person)o;
		return email.equals(other.email);
	}
	
	public String toString() {
		return "(" + firstName + " " + lastName + "-" + email;
	}
	
}

class Point{
	int x,y;
	Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals (Object o) {
		if(o == null || !(o instanceof Point)) {
			return false;
		}
		Point other = (Point) o;
		return this.x == other.x && this.y == other.y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public int hashCode () {
		return  ("" + x + y).hashCode();
	}
	
	
	
	
}

public class HashUse {
	
	public static void main(String [] args) {
		/*
		 * hash map maps a unique key to a value
		 * We ll use email as the key and Person object as the value <key, value>
		 */
		
		HashMap<String,Person> people = new HashMap <String, Person>(500,2.5f);
		
		String email = "anapaula@cs,rutgers.edu";
		
		Person p1 = new Person ("ana Paula", "centeno", email);
		
		//hashmap put method will insert into HT
		
		//put method calls the string class hashcode method on the email 
		//before inserting p1 into the HashTable
		
		people.put(email,p1);
		
		String email2 = "seshemail";
		Person p2 = new Person("sesh", "venugopaul", email2);
		
		people.put(email2, p2);
		
		//to retrieve
		
		System.out.println(people.get(email));
		
		for(Person p : people.values()) {
			System.out.println(p);
		}
		
		//hash table for lines <key, value> <==> <start point, end point>
		
		HashMap<Point, Point> lines = new HashMap<Point, Point>();
		
		Point a = new Point(3,5);
		Point b = new Point(5,8);
		Point c = new Point(-1, 4);
		Point d = new Point(12,55);
		
		lines.put(a, b);
		lines.put(c, d);
		
		for(Point p : lines.keySet()) {
			System.out.println("key: " + p + " value:" + lines.get(p));
		}
		
		for(Point p: lines.values()) {
			System.out.println("value : " + p);
		}
		
		System.out.println(lines.containsKey(c));
		
	}
	

	
	
}
