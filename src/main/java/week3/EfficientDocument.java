package week3;

import java.util.List;

/**
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 * Completed by Aleksandra Globa
 * Date: July 10, 2020
 */

public class EfficientDocument extends Document {

	private int numWords;
	private int numSentences;
	private int numSyllables;
	private static List<String> words;
	private static List<String> sentences;

	public EfficientDocument(String text) {
		super(text);
		processText();
	}

	private void processText() {
		sentences = getTokens("[^!?.]+");
		words = getTokens("[a-zA-Z]+");
		numSentences = sentences.size();
		numWords = words.size();

		for (String word : words) {
			numSyllables += countSyllables(word);
		}
	}

	@Override
	public int getNumSentences() {
		return numSentences;
	}

	@Override
	public int getNumWords() {
	    return numWords;
	}

	@Override
	public int getNumSyllables() {
        return numSyllables;
	}

	// Testing
	public static void main(String[] args)
	{
	    testCase(new EfficientDocument("This is a test.  How many???  "
                + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new EfficientDocument(""), 0, 0, 0);
        testCase(new EfficientDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new EfficientDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new EfficientDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new EfficientDocument("Segue"), 2, 1, 1);
		testCase(new EfficientDocument("Sentence"), 2, 1, 1);
		testCase(new EfficientDocument("Sentences?!"), 3, 1, 1);
		testCase(new EfficientDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
	}
}
