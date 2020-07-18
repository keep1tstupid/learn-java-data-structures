package week5;

/**
 * @author UC San Diego Intermediate Programming MOOC Team
 * No changes here
 */

public interface Dictionary {
	boolean addWord(String word);

	boolean isWord(String s);
	
	// Return the number of words in the dictionary
	int size();
}
