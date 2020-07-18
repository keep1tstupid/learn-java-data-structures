package week6;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author UC San Diego Intermediate MOOC team
 * Completed by Aleksandra Globa
 * Date: July 18, 2020
 */

public class NearbyWords implements SpellingSuggest {
	Dictionary dict;

	public NearbyWords (Dictionary dict) {
		this.dict = dict;
	}

	public List<String> distanceOne(String s, boolean wordsOnly ) {
	   List<String> retList = new ArrayList<String>();
	   insertions(s, retList, wordsOnly);
	   substitution(s, retList, wordsOnly);
	   deletions(s, retList, wordsOnly);
	   return retList;
	}

	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		for(int i = 0; i < s.length(); i++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer newWord = new StringBuffer(s);
				newWord.setCharAt(i, (char)charCode);
				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(newWord.toString()) && (!wordsOnly || dict.isWord(newWord.toString()))
						&& !s.equals(newWord.toString())) {
					currentList.add(newWord.toString());
				}
			}
		}
	}

	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		for(int i = 0; i <= s.length(); i++) {
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer newWord = new StringBuffer(s);
				newWord.insert(i, (char) charCode);

				if (!currentList.contains(newWord.toString()) && (!wordsOnly||dict.isWord(newWord.toString()))) {
					currentList.add(newWord.toString());
				}
			}
		}
	}


	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		for (int i = 0; i < s.length(); i++) {
			StringBuffer newWord = new StringBuffer(s);
			newWord.deleteCharAt(i);
			if (!currentList.contains(newWord.toString()) && (!wordsOnly || dict.isWord(newWord.toString()))
					&& !s.equals(newWord.toString())) {
				currentList.add(newWord.toString());
			}
		}
	}

	@Override
	public List<String> suggestions(String word, int numSuggestions) {
		Queue<String> queue = new LinkedBlockingQueue<String>();
		HashSet<String> visited = new HashSet<String>();
		List<String> suggestions = new ArrayList<String>();

		queue.add(word);
		visited.add(word);

		while(!queue.isEmpty() && suggestions.size() < numSuggestions) {
			String currWord = queue.poll();
			List<String> neighbors = distanceOne(currWord, false);
			for (String n : neighbors) {
				if (!visited.contains(n)) {
					visited.add(n);
					queue.add(n);
					if(suggestions.size() < numSuggestions && dict.isWord(n)) {
						suggestions.add(n);
					}
				}
			}
		}
		return suggestions;
	}

//   public static void main(String[] args) {
//	   String word = "i";
//	   Pass NearbyWords any Dictionary implementation you prefer
//	   Dictionary d = new DictionaryHashSet();
//	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
//	   NearbyWords w = new NearbyWords(d);
//	   List<String> l = w.distanceOne(word, true);
//	   System.out.println("One away word Strings for for \""+word+"\" are:");
//	   System.out.println(l+"\n");
//
//	   word = "tailo";
//	   List<String> suggest = w.suggestions(word, 10);
//	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
//	   System.out.println(suggest);
//   }
}
