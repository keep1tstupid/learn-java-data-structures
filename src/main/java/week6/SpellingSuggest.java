package week6;

import java.util.List;

/**
 * @author UC San Diego Intermediate Programming MOOC Team
 * No changes here
 */

public interface SpellingSuggest {
	public List<String> suggestions(String word, int numSuggestions);
}
