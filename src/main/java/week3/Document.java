package week3;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;

	protected Document(String text)
	{
		this.text = text;
	}
	
	// Returns the tokens that match the regex pattern from the document text string
	protected List<String> getTokens(String pattern) {
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		return tokens;
	}

	protected static int countSyllables(String word) {
		int numSyllables = 0;
		boolean newSyllable = true;
		String vowels = "aeiouy";
		char[] cArray = word.toLowerCase().toCharArray();
		for (int i = 0; i < cArray.length; i++) {
			if (i == cArray.length - 1 && cArray[i] == 'e'
					&& newSyllable && numSyllables > 0) {
				numSyllables--;
			}
			if (newSyllable && vowels.indexOf(cArray[i]) >= 0) {
				newSyllable = false;
				numSyllables++;
			} else if (vowels.indexOf(cArray[i]) < 0) {
				newSyllable = true;
			}
		}
		return numSyllables;
	}

	public static boolean testCase(Document doc, int syllables, int words, int sentences) {
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		} else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	

	public abstract int getNumWords();

	public abstract int getNumSentences();

	public abstract int getNumSyllables();

	public String getText() {
		return this.text;
	}

	public double getFleschScore() {
		double words = getNumWords();
		double sentences = getNumSentences();
		double syllables = getNumSyllables();
		double fleschScore = 206.835 - 1.015 * (words / sentences) - 84.6 * ( syllables / words);

		return Math.round(fleschScore * 100.0) / 100.0;
	}
}
