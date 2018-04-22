package Apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class EpisodeList {
	
	public static ArrayList<String> makeList(String series) throws FileNotFoundException {
		
		ArrayList<String> episodeList = new ArrayList<String>();
		
		Scanner sc = new Scanner(new File("/Users/Kylelitton/Code/Episodes/"+series.toLowerCase()));
		
		while(sc.hasNext()) {
			episodeList.add(sc.nextLine());
		}
		
		sc.close();	
	return episodeList;	
	}

}
