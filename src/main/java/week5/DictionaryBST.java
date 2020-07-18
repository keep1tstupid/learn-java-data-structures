package week5;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 * Completed by Aleksandra Globa
 * Date: July 15, 2020
 */

public class DictionaryBST implements Dictionary {
   private TreeSet<String> dict;

    public DictionaryBST() {
        dict = new TreeSet<String>();
    }

    // Add this word to the dictionary.  Convert it to lowercase first for the assignment requirements.
    public boolean addWord(String word) {
        String newWord = word.toLowerCase();
        if (!dict.contains(newWord)) {
            dict.add(newWord);
            return true;
        }
        return false;
    }

    public int size() {
        return dict.size();
    }

    public boolean isWord(String s) {
        String word = s.toLowerCase();
        return dict.contains(word);
    }
}
