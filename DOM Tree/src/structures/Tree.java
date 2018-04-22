package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		Stack <TagNode> tags = new Stack <TagNode>();
		TagNode ptr = null;
		String nextTag = null;
		
		while(sc.hasNextLine()) {
			nextTag = sc.nextLine();
			
			if(root==null) {
				nextTag = nextTag.substring(1, nextTag.length()-1);
				root = new TagNode(nextTag,null,null);
				ptr = root;
				tags.push(root);
				
			}
			
			else if(nextTag.charAt(0)=='<' && nextTag.charAt(1) != '/') {
				nextTag = nextTag.substring(1, nextTag.length()-1);
				TagNode open = new TagNode (nextTag, null, null);
				TagNode temp = tags.pop();
				
				if(temp.firstChild == null) {
					temp.firstChild = open;
					ptr = open;
					tags.push(temp);
					tags.push(open);
				}
				else if(temp.firstChild != null){
					ptr.sibling = open;
					ptr = open;
					tags.push(temp);
					tags.push(open);
				}		
			}
			else if(nextTag.charAt(0)=='<' && nextTag.charAt(1) == '/') {
				ptr = tags.pop();
			}
			else if(nextTag.charAt(0)!='<') {
				TagNode other = new TagNode(nextTag,null,null);
				TagNode temp = tags.pop();
				
				if(temp.firstChild == null) { 
					temp.firstChild = other;
					ptr = other;
					tags.push(temp);
					
				}
				else if(temp.firstChild !=null ) {
					ptr.sibling = other;
					ptr = other;
					tags.push(temp);
				}
				
			}
				
			
			
		}
		
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		recReplace(oldTag, newTag, root, 1);
		
	}
	
	private void recReplace(String oldTag, String newTag, TagNode root, int level) { //look at print method 
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if(ptr.tag.equals(oldTag)) { //checks for target string
				ptr.tag = newTag;
			}
			if(ptr.firstChild !=null) {
				recReplace(oldTag, newTag, ptr.firstChild, level+1); 
			}
		}
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		TagNode table = recTable(root);
		TagNode tableRow = table.firstChild;
		
		int currRow = 1;
		
		while(currRow != row) { //finds row to bold
			tableRow = tableRow.sibling;
			currRow++;
		}
		TagNode tableColumn = tableRow.firstChild;
		
		while(tableColumn != null) { // adds b tag to each column of the proper row
			TagNode bold = new TagNode("b", tableColumn.firstChild, null);
			
			tableColumn.firstChild = bold;
			
			tableColumn = tableColumn.sibling;
		}
		
		
	}
	
	private TagNode recTable(TagNode root) {
		if(root == null) { //empty tree
			return null;
		}
		if(root.tag.equals("table")) { //location of table tag
			return root;
		}
		
		TagNode firstChild = recTable(root.firstChild); //will go through children and siblings recurively to find the table
		TagNode sibling = recTable(root.sibling);
		
		if(firstChild != null) {  //process will continue until table is found, only one table can be in any file according to instructions
			return firstChild;
		}
		if(sibling != null) {
			return sibling;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		
		recRemove(tag, root, null);
		
	}
	
	private void recRemove(String tag, TagNode root, TagNode prev) {
		
		
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			
		if(tag.equals("p") || tag.equals("em") || tag.equals("b")) {	
			
			if(root.tag.equals(tag)) {
				
				if(prev == null) {      //checks prev pointer 
				root = root.firstChild;
				root.sibling = ptr.sibling;
				ptr = root;
				}
			
				else if(prev.firstChild.equals(root)) { //makes prev pointer have new first child
					prev.firstChild = root.firstChild;
					root = root.firstChild;
					root.sibling = ptr.sibling;
					ptr = root;
				}
				
			}
		}
		
		if(tag.equals("ol") || tag.equals("ul")) {
			
			if(root.tag.equals(tag)) {
				
			  if(prev == null) {      //checks prev pointer 
				root = root.firstChild;
				root.firstChild.tag = "p";
			  
			  if(root.sibling == null) {	
				root.sibling.tag = "p";
				root.sibling = ptr.sibling;
				}
			  
			  if(root.sibling != null) {
				  TagNode sibPtr = root; //keeps track of sibilngs
				  
				  while(sibPtr.sibling != null) {
					  sibPtr.tag = "p";
					  sibPtr = sibPtr.sibling;
				  }
				  
				  if(sibPtr.tag != "p") {
					  sibPtr.tag = "p";
				  }
				  
				  sibPtr.sibling = ptr.sibling;
			  }
			  
				ptr = root;
			}
			
			else if(prev.firstChild.equals(root)) { //makes prev pointer have new first child
					
					prev.firstChild = root.firstChild;
					root.firstChild.tag = "p";
					root = root.firstChild;
				
				if(root.sibling == null) {
					root.sibling.tag = "p";
					root.sibling = ptr.sibling;
				}
				
				if(root.sibling != null) {
					  TagNode sibPtr = root; //keeps track of sibilngs
					  
					  while(sibPtr.sibling != null) {
						  sibPtr.tag = "p";
						  sibPtr = sibPtr.sibling;
					  }
					  
					  if(sibPtr.tag != "p") {
						  sibPtr.tag = "p";
					  }
					  
					  sibPtr.sibling = ptr.sibling;
				  }
					ptr = root;
				}
		
				
			}
		}
			
		if(ptr.tag.equals("p") || ptr.tag.equals("em") || ptr.tag.equals("b")) {
	
				
				if(ptr.tag.equals(tag) && prev.firstChild == null) {
					prev.sibling = ptr.firstChild; //prevents loss of data of ptrs children
					ptr.firstChild.sibling = ptr.sibling;
					ptr = ptr.firstChild;
				}
				
				else if(ptr.tag.equals(tag) && prev.firstChild != null && prev.firstChild.equals(ptr)) { //checks if prev is a parent or sibilng
					prev.firstChild = ptr.firstChild;
					ptr.firstChild.sibling = ptr.sibling;
					ptr = ptr.firstChild;
				}
				
				else if(ptr.tag.equals(tag) && prev.firstChild != null && !prev.firstChild.equals(ptr)) {
					prev.sibling = ptr.firstChild; 
					ptr.firstChild.sibling = ptr.sibling;
					ptr = ptr.firstChild;
				}
				
			}
			
	 if(ptr.tag.equals("ol") || ptr.tag.equals("ul")) { //delete tag and make child p tag
				
				if(ptr.tag.equals(tag) && prev.firstChild == null) {
					prev.sibling = ptr.firstChild; //prevents loss of data of ptrs children
					ptr.firstChild.tag = "p";

					TagNode sibPtr = ptr.firstChild.sibling; //keeps track of sibilngs
					  
					  while(sibPtr.sibling != null) {
						  sibPtr.tag = "p";
						  sibPtr = sibPtr.sibling;
					  }
					  
					  if(sibPtr.tag != "p") {
						  sibPtr.tag = "p";
					  }
					  
					sibPtr.sibling = ptr.sibling;
					  
					ptr = ptr.firstChild;
				}
				
				else if(ptr.tag.equals(tag) && prev.firstChild != null && prev.firstChild.equals(ptr)) { //checks if prev is a parent or sibilng
					prev.firstChild = ptr.firstChild;
					ptr.firstChild.tag = "p";

					TagNode sibPtr = ptr.firstChild.sibling; //keeps track of sibilngs
					  
					  while(sibPtr.sibling != null) {
						  sibPtr.tag = "p";
						  sibPtr = sibPtr.sibling;
					  }
					  
					  if(sibPtr.tag != "p") {
						  sibPtr.tag = "p";
					  }
					  
					sibPtr.sibling = ptr.sibling;
					  

					ptr = ptr.firstChild;
				}
				
				else if(ptr.tag.equals(tag) && prev.firstChild != null && !prev.firstChild.equals(ptr)) {
					prev.sibling = ptr.firstChild; //prevents loss of data of ptrs children
					ptr.firstChild.tag = "p";
					
					TagNode sibPtr = ptr.firstChild.sibling; //keeps track of sibilngs
					  
					  while(sibPtr.sibling != null) {
						  sibPtr.tag = "p";
						  sibPtr = sibPtr.sibling;
					  }
					  
					  if(sibPtr.tag != "p") {
						  sibPtr.tag = "p";
					  }
					  
					sibPtr.sibling = ptr.sibling;
					  
					ptr = ptr.firstChild;
				}
				
				
				
			}
			
			prev = ptr; //prev will allow LL to repair the gap of deleting a node
			
			if(ptr.firstChild!=null) {
				recRemove(tag,ptr.firstChild, prev);
			}
			
			
			
		}
		
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		recAddTag(word, tag, root, null);
	}
	
	private void recAddTag(String word, String tag, TagNode root, TagNode prev) { //will recursively find each instance of the word and add the tag
		
		
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			
			String tagLower = ptr.tag.toLowerCase();
			
			TagNode temp = new TagNode(tag, null,null);
			
			if(root == null){
				return;
			}
			
			if(ptr.tag.equalsIgnoreCase(word) || (ptr.tag.substring(0, ptr.tag.length()-1).equalsIgnoreCase(word) && ( ptr.tag.charAt(ptr.tag.length()-1) == ',' 
					|| ptr.tag.charAt(ptr.tag.length()-1) == '.' || ptr.tag.charAt(ptr.tag.length()-1) == '!' || ptr.tag.charAt(ptr.tag.length()-1) == '?' ||
					ptr.tag.charAt(ptr.tag.length()-1) == ':' || ptr.tag.charAt(ptr.tag.length()-1) == ';'))){  //target is only word
				
				if(prev.firstChild == null) {
					
					prev.sibling = temp;
					temp.firstChild = ptr;
					temp.sibling = ptr.sibling;
					ptr.sibling = null;
				
					if(temp.sibling!=null) {	//prevents null pointer exception
					ptr = temp.sibling;
					}
				}
				
				if(prev.firstChild != null && prev.firstChild.equals(ptr)) {
					
					prev.firstChild = temp;
					temp.firstChild = ptr;
					temp.sibling = ptr.sibling;
					ptr.sibling = null;
					
					if(temp.sibling!=null) {	
						ptr = temp.sibling;
						}
					
				}
							
			}
			
			
			
			else if(wordInSentence(ptr.tag,word)) { // target is part of a sentence
				
				int targetStart = wordBegins(ptr.tag, word);      
				int targetEnd = wordBegins(ptr.tag, word)+word.length();
				
				if(ptr.tag.charAt(0) == ' ') {
					
				}
				
				else if(targetStart == 0) { //first word in sentence
					
					TagNode firstWord = new TagNode(ptr.tag.substring(0, targetEnd),null,null);
					TagNode restSentence = new TagNode(ptr.tag.substring(targetEnd, ptr.tag.length()),null, ptr.sibling);
					
					if(prev.firstChild == null) {
						
						prev.sibling = temp;
						temp.firstChild = firstWord;
						temp.sibling = restSentence;
						ptr = temp.sibling;
						
					}
					
					if(prev.firstChild != null && prev.firstChild.equals(ptr)) {
						
						prev.firstChild = temp;
						temp.firstChild = firstWord;
						temp.sibling = restSentence;
						ptr = temp.sibling;
					}
					
				}
				
				else if(targetStart > 0 && targetStart < (ptr.tag.length()-word.length()-1)) { //target is in middle of sentence
					
					TagNode frontSentence = new TagNode ((ptr.tag.substring(0, targetStart)),null,temp);
					TagNode targetWord = new TagNode(ptr.tag.substring(targetStart, targetEnd),null,null);
					TagNode restSentence = new TagNode(ptr.tag.substring(targetEnd, ptr.tag.length()),null, ptr.sibling);
					
					if(prev.firstChild == null) {
						prev.sibling = frontSentence;
						temp.firstChild = targetWord;
						temp.sibling = restSentence;
						ptr = temp.sibling;
						
					}
					
					if(prev.firstChild != null && prev.firstChild.equals(ptr)) {
						prev.firstChild = frontSentence;
						temp.firstChild = targetWord;
						temp.sibling = restSentence;
						ptr = temp.sibling;
						
					}
					
				}
				
				else { //its the last word in a sentence
					TagNode frontSentence = new TagNode ((ptr.tag.substring(0, targetStart)),null,temp);
					TagNode targetWord = new TagNode(ptr.tag.substring(targetStart, ptr.tag.length()),null,null);
					
					if(prev.firstChild == null) {
						
						prev.sibling = frontSentence;
						temp.firstChild = targetWord;
						temp.sibling = ptr.sibling;
						if(temp.sibling!=null) {	
							ptr = temp.sibling;
							}
						
					}
					
					if(prev.firstChild != null && prev.firstChild.equals(ptr)) {
						prev.firstChild = frontSentence;
						temp.firstChild = targetWord;
						temp.sibling = ptr.sibling;
						if(temp.sibling!=null) {	
							ptr = temp.sibling;
							}				
						
					}
					
					
					
					
				}
				
				
				
				
			}
			
			prev = ptr;
			
			if(ptr.firstChild!=null) {
				recAddTag(word, tag, ptr.firstChild, prev);
			}
			
		}
		
		
	}
	
	private int wordBegins(String sentence, String target) {
		
		String delims = " .,!?:;";
		
		StringTokenizer str = new StringTokenizer(sentence.toLowerCase(), delims, true);
		
		int spaceCount = 0;
		
		int indexCount = -1;
		
		int spaceCount2 = 0;
		
		String nextToken = str.nextToken();
		
		
		
		while(!nextToken.equals(target) || !nextToken.equals(target.toLowerCase())) {
			
			if(nextToken.equals(" ")) {
				spaceCount++;
			}
			
			nextToken = str.nextToken();
			
		}
				
		for(int i = 0; spaceCount2 < spaceCount; i++ ) {
			
			if(sentence.charAt(i) == ' ') {
				spaceCount2++;
			}
			
			indexCount++;
				
			}
			
			return indexCount+1;
		}
		
	private boolean wordInSentence(String sentence, String target) {
		String delims = " .,!?:;";
		StringTokenizer str = new StringTokenizer(sentence.toLowerCase(), delims, true);
		String targetLow = target.toLowerCase();
		String tok = str.nextToken();
		
		
		while(tok != null) {
			if(tok.equals(targetLow)) {
				return true;
			}
			if(!str.hasMoreTokens()) {
				tok = null;
			}
			if(str.hasMoreTokens()) {
				tok = str.nextToken();
			}
			
			
		}
		
		return false;
		
	}
		
	
	
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
