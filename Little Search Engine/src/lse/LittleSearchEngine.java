package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile)
	throws FileNotFoundException {
		
		
		
		HashMap<String, Occurrence> loadKeyWords = new HashMap <String, Occurrence>(1000,2.0f);
	
	try {	
		Scanner test = new Scanner(new File(docFile));
	} catch(Exception e) {
		throw new FileNotFoundException();
	}
	
		Scanner sc = new Scanner(new File(docFile));
		
		while (sc.hasNext()) {
			String word = sc.next();
			
		if(getKeyword(word)!=null) {
			
			String putWord = getKeyword(word); //stores word without punctuation or caps
				
			if(loadKeyWords.containsKey(putWord.toLowerCase())) {
				
				Occurrence tempFreq = loadKeyWords.get(putWord.toLowerCase());
				
				tempFreq.frequency++; //if words is in hasmap itll update its frequency
				
				
			}else{
					
				Occurrence freq = new Occurrence(docFile, 1); //makes new occurrence object since its the first time the word appears
				 
				loadKeyWords.put(putWord.toLowerCase(), freq);
			}
					
		}
			
	}
		sc.close();
		
		return loadKeyWords;
			
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		
		for(String keyCurr : kws.keySet()) {
			
		
			if(keywordsIndex.get(keyCurr)!=null) {// not the first time this word is being entered
				
				ArrayList<Occurrence> arrIndex = keywordsIndex.get(keyCurr);
				
				arrIndex.add(kws.get(keyCurr)); //updates master hashmap with keyword freq
				insertLastOccurrence(arrIndex);
				
				
				
			}
			else { //first time certain key is entered
				
				ArrayList<Occurrence> freqIndex = new ArrayList<Occurrence>();
				
				freqIndex.add(kws.get(keyCurr));
				
				keywordsIndex.put(keyCurr, freqIndex);
			}
			
		}
		
		
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		String NewWord = justWord(word);  //checks if the word is a noiseword or not
		
		
		if(!noiseWords.contains(NewWord.toLowerCase()) && isAlpha(NewWord.toLowerCase())) {  //not a noise word
			return NewWord;
		}
		else {
			return null;
		}
		
			
		
	}
	
	private static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	
	private String justWord(String word) { //removes word of any punctuation
		
		String tempWord = word;
		
		String delims = ".,?:;!\"'-"; //check for numbers?

		
		StringTokenizer str = new StringTokenizer(tempWord, delims,true);
		
		String finalWord = str.nextToken();
		
		if(finalWord.equals("\"")) { // prevents "quotes" from being entered as a keyword
			return "what";
		}
		
		else if(str.hasMoreTokens()) {
			String check = str.nextToken();
			
		while(str.hasMoreTokens()) {
			String check2 = str.nextToken();
			if(delims.contains(check) && isAlpha(check2)) {
				return "what"; //prevents words like equi-distant and what,ever
			}
			else if(str.hasMoreTokens()) {
				check = check2;
			}
		}
		
	}
			
		return finalWord;
		
		
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		if(occs.size()==1) {
			return null;
		}
		
		ArrayList<Integer> midIndexes = new ArrayList<Integer>();
		
		int low = 0;
		int target = occs.get((occs.size()-1)).frequency;      //saves data of the new addition, 
		String tempString = occs.get((occs.size()-1)).document;
		occs.remove(occs.size()-1);
		int high = occs.size()-1;
		int mid = 0;
		while(low<=high) {
			
			mid = (low+high)/2;
			System.out.println(mid);
			
			if(occs.get(mid).frequency == target) { //look into case where input list size is only 1
				midIndexes.add(mid);
				break;
			}
			
			else if(occs.get(mid).frequency > target) {
				midIndexes.add(mid);
				low = mid+1;
				
			}
			else {
				midIndexes.add(mid);
				high = mid-1;
				
			}
			
		}
		
		
		
		Occurrence newOcc = new Occurrence(tempString, target);
		
		occs.add(low, newOcc);
		
		
		
		
		
		return midIndexes;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
		
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<String> top5 = new ArrayList<String>();
		
		if(keywordsIndex.containsKey(kw1)==false && keywordsIndex.containsKey(kw2)==false) { //check if neither word is in master index
			return null;
		}
		
		ArrayList<Occurrence> word1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> word2 = keywordsIndex.get(kw2);
		HashMap<String, Occurrence> dupCheck = new HashMap <String, Occurrence>(1000,2.0f);
		
		if(word1!= null && word2==null) {
			int count = 0;
			
			while(count<5) {
				if(count<word1.size()) {
				top5.add(word1.get(count).document);}
				count++;
			}
			
			return top5;
			
		}
		
		if(word2!= null && word1==null) {
			int count = 0;
			
			while(count<5) {
				if(count<word2.size()) {
				top5.add(word2.get(count).document);}
				count++;
			}
			
			return top5;		
		}
		
		
		else { //both words in master index
			int count = 0;
			int word1Size = word1.size();
			int word2Size = word2.size();
			
	while(count<5) {
			 
		 if(count<word1Size) { //prevents error
			 
				 top5.add(word1.get(count).document);
				 dupCheck.put(word1.get(count).document, word1.get(count));
					
			 }
				
				count++;
			}
	
	count = 0;
		
	while(count<5) {
			
		if(count<word2Size) {
				
			if(!top5.contains(word2.get(count).document) || (top5.contains(word2.get(count).document) && dupCheck.get(word2.get(count).document).frequency
						< word2.get(count).frequency)) { //checks for duplicate, only adds if this freq is higher
				
				dupCheck.remove(word2.get(count).document);
				dupCheck.put(word2.get(count).document, word2.get(count));//updates dup hash map, later can be used to sort??
					
				}
				
				
			}
			
			count++;
		}
	
		ArrayList<String> finalTop5 = new ArrayList<String>();
		ArrayList<Integer> arrTemp = new ArrayList<Integer>();
		
		HashMap<Integer, String> final5 = new HashMap<Integer,String>(1000,2.0f);
		
		
		
		for(String key : dupCheck.keySet()) {
			Occurrence temp = dupCheck.get(key);
			
			arrTemp.add(temp.frequency);
			
			final5.put(temp.frequency, key);
			
		}
		
		 Collections.sort(arrTemp);
		 Collections.reverse(arrTemp);
		 int count2 = 0;
		 while(count2<arrTemp.size()) {
			 if(count2<5) {
			 finalTop5.add(final5.get(arrTemp.get(count2)));}
			 count2++;
		 }
			
			
			return finalTop5;
		}
				
		
	
	}
}
