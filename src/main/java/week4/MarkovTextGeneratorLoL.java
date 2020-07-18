package week4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team
 * Completed by Aleksandra Globa
 * Date: July 15, 2020
 */

public class MarkovTextGeneratorLoL implements MarkovTextGenerator {
	private List<ListNode> wordList;
	private final Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		rnGenerator = generator;
	}
	
	private void processText(String text) {
		if (text.isEmpty()) {
			ListNode newNode = new ListNode("");
			newNode.addNextWord("");
			wordList.add(newNode);
		}
		List<String> allWords = getWords(text);
		int size = allWords.size();

		for (int i = 0; i < size; i++) {
			int index = findNodeIndex(allWords.get(i));
			String currWord = allWords.get(i);
			String nextWord = (i == size - 1) ? allWords.get(0) : allWords.get(i + 1);

			if (index >= 0) {
				// add this word to existing list (only next)
				ListNode currNode = wordList.get(index);
				currNode.addNextWord(nextWord);
			} else {
				// add new node (Word + list with next word)
				ListNode newNode = new ListNode(currWord);
				newNode.addNextWord(nextWord);
				wordList.add(newNode);
			}
		}
	}

	private int findNodeIndex(String word) {
		int index = -1;
		int count = 0;
		for (ListNode words : wordList) {
			if (words.getWord().equals(word)) {
				index = count;
			}
			count++;
		}
		return index;
	}

	private List<String> getWords(String text) {
		List<String> allWords = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[a-zA-Z]+(,|.|!)");
		Matcher m = tokSplitter.matcher(text);

		while (m.find()) {
			allWords.add(m.group());
		}
		return allWords;
	}

	@Override
	public void train(String sourceText) {
		processText(sourceText);
	}

	@Override
	public String generateText(int numWords) {
		if (wordList.size() == 0) {
			return "No words available";
		}

		StringBuilder newText = new StringBuilder();
		String word;
		for (int i = 0, wordIndex = 0; i < numWords; i++, wordIndex = findNodeIndex(word)) {
			word = wordList.get(wordIndex).getRandomNextWord(rnGenerator);
			newText.append(word).append(" ");
		}
		return newText.toString();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (ListNode n : wordList) {
			str.append(n.toString());
		}
		return str.toString();
	}
	
	// Retrain the generator from scratch on the source text
	@Override
	public void retrain(String sourceText) {
		wordList.clear();
		wordList = new LinkedList<ListNode>();
		processText(sourceText);
	}

	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello kek kek kek.  Hello there. Hello kek.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}
}

class ListNode {
	private final String word;
	private final List<String> nextWords;
	
	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator) {
		int size = nextWords.size();
		generator = new Random();
		int rnd = generator.nextInt(size);

	    return this.nextWords.get(rnd);
	}

	public String toString() {
		StringBuilder str = new StringBuilder(word + ": ");
		for (String s : nextWords) {
			str.append(s).append("->");
		}
		str.append("\n");
		return str.toString();
	}
}


