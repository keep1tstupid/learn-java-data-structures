package week5;

import java.util.List;

/**
 * @author UC San Diego Intermediate Programming MOOC Team
 * No changes here
 */

public interface AutoComplete {
	public List<String> predictCompletions(String prefix, int numCompletions);
}
