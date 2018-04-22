package Apps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner stdin = new Scanner(System.in);
		System.out.println("Enter Series Name ");
		String Series = stdin.nextLine();
		
		ArrayList<String> episodes = new ArrayList<String>();
		try {
			episodes = EpisodeList.makeList(Series);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("want to add another series? Yes or No");
		String check = stdin.nextLine();
		while(check.toLowerCase().equals("yes")) {
			System.out.println("Enter Series: ");
			String Series2 = stdin.nextLine();
			ArrayList<String> episodes2 = EpisodeList.makeList(Series2);
			
			episodes.addAll(episodes2);
			
			System.out.println("want to add another series? Yes or no");
			
			check = stdin.nextLine();
		}
		
		if(check.toLowerCase().equals("no")) {
			int randIndex = 0 + (int)(Math.random() * ((episodes.size()) + 1));
			
			System.out.println("Random Episode URL: " + episodes.get(randIndex));
			
			System.out.println("Shuffle Again? Yes or No");
			String Check = stdin.nextLine().toLowerCase();
			
			while(Check.equals("yes")) {
				 randIndex = 0 + (int)(Math.random() * ((episodes.size()) + 1));
				
				System.out.println("Random Episode URL: " + episodes.get(randIndex));
				
				System.out.println("Shuffle Again? Yes or No");
				 Check = stdin.nextLine().toLowerCase();
			}
			
		}
		
		
		else {
			throw new IllegalArgumentException("Enter yes or no");
		}
		
		

	}

}
