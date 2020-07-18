package week3;

import java.util.List;

/**
 * A naive implementation of the Document abstract class.
 * @author UC San Diego Intermediate Programming MOOC team
 */

public class BasicDocument extends Document {

	public BasicDocument(String text) {
		super(text);
	}

	@Override
	public int getNumWords() {
		List<String> tokens = getTokens("[a-zA-Z]+");
		return tokens.size();
	}

	@Override
	public int getNumSentences() {
		List<String> tokens = getTokens("[^?.!]+");
		return tokens.size();
	}

	@Override
	public int getNumSyllables() {
		List<String> tokens = getTokens("[a-zA-Z]+");
		int totalSyllables = 0;
		for (String word : tokens) {
			totalSyllables += countSyllables(word);
		}
		return totalSyllables;
	}


	public static void main(String[] args) {
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("This is a test.  How many???  Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  (And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
				32, 15, 1);
		testCase(new BasicDocument("Segue."), 2, 1, 1);

		String s = "%one%%two%%%three%%%%";
		String[] arr = s.split("one,two,three");
		for ( String s1 : arr) {
			System.out.println("Str: " + s1 + ".");
		}
	}
}
