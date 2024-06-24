package token;
import java.util.HashMap;
import java.util.List;
/**
 * A {@code Ruleset} is a set of rules that define the behavior of a {@token Tokenizer}.
 * 
 * @author nrdc
 * @since 1.0
 * @version 1.0
 * 
 */
public interface Ruleset {

	/**
	 * Set the token types of the ruleset.
	 */
	public void setTokenTypes();

	/**
	 * Set the token regex of the ruleset.
	 */
	public void setTokenRegex();


	/**
	 * Get the token types of the ruleset.
	 * 
	 * @return the token types of the ruleset
	 */
	public List<String> getTokenTypes();

	/**
	 * Get the token regex of the ruleset.
	 * 
	 * @return the token regex of the ruleset
	 */
	public HashMap<String, String> getTokenRegex();
};
