package week5;

import javax.management.Query;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * Completed by Aleksandra Globa
 * Date: July 16, 2020
 */

public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {
    private TrieNode root;
    private int size;

    public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
		size = 0;
	}

	// Insert a word into the trie.
	public boolean addWord(String word) {
		String newWord = word.toLowerCase();
		TrieNode currNode = root;

		for (char c : newWord.toCharArray()) {
			if (currNode.getValidNextCharacters().contains(c)) {
				currNode = currNode.getChild(c);
				//System.out.println("EXIST: " + c);
			} else {
				currNode = currNode.insert(c);
				//System.out.println("INSERT: " + c);
			}
		}

		if (currNode.endsWord()) {
			return false;
		}

		currNode.setEndsWord(true);
		size++;
		return true;
	}

	public int size() {
	    return size;
	}

	// Returns whether the string is a word in the trie, using the algorithm
	@Override
	public boolean isWord(String s) {
		String str = s.toLowerCase();
		TrieNode currNode = root;
		for (char c : str.toCharArray()) {
			if (currNode.getValidNextCharacters().contains(c)) {
				currNode = currNode.getChild(c);
			} else {
				return false;
			}
		}

		if (currNode.endsWord()) {
			return true;
		}

		return false;
	}

	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		String prefixToCheck = prefix.toLowerCase();
		TrieNode currNode = isPartOfTrie(prefixToCheck);

		LinkedList<String> foundWords = new LinkedList<String>();

		if (currNode == null) {
			return foundWords;
		}

		Queue<TrieNode> allOptions = new LinkedBlockingQueue<TrieNode>();
		allOptions.add(currNode);

		while (foundWords.size() < numCompletions && allOptions.size() > 0) {
			TrieNode node = allOptions.poll();

			if (node.endsWord()) {
				foundWords.add(node.getText());
			}

			for (Character c: node.getValidNextCharacters()) {
				allOptions.add(node.getChild(c));
			}
		}

		return foundWords;
	}

	private TrieNode isPartOfTrie(String prefix) {
		String str = prefix.toLowerCase();
		TrieNode currNode = root;

		for (char c : str.toCharArray()) {
			if (currNode.getValidNextCharacters().contains(c)) {
				currNode = currNode.getChild(c);
			} else {
				return null;
			}
		}
		return currNode;
	}

 	public void printTree() {
 		printNode(root);
 	}

 	// Do a pre-order traversal from this node down
 	public void printNode(TrieNode curr) {
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
}