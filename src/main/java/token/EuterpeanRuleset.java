package token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@code EuterpeanRuleset} is a set of rules that define the behavior of a {@token EuterpeanTokenizer}.
 * It produces {@code EuterpeanToken} objects.
 * 
 * @see EuterpeanTokenizer
 * @see EuterpeanToken
 * @see Token
 * @see Ruleset 
 */
public class EuterpeanRuleset implements Ruleset {
	private List<String> TOKEN_TYPES;
	private HashMap<String, String> TOKEN_REGEX;
	
	/**
	 * Creates a new {@code EuterpeanRuleset}.
	 */	
	public EuterpeanRuleset() {
		setTokenTypes();
		setTokenRegex();
		
		
	};

	@Override
	public void setTokenTypes() {
		TOKEN_TYPES = new ArrayList<String>();
		TOKEN_TYPES.add("NOTE");
		TOKEN_TYPES.add("REPEAT NOTE?");
		TOKEN_TYPES.add("REST");
		TOKEN_TYPES.add("INCREASE VOLUME");
		TOKEN_TYPES.add("DECREASE VOLUME");
		TOKEN_TYPES.add("CHANGE INSTRUMENT");
		TOKEN_TYPES.add("TEMPO");
		TOKEN_TYPES.add("EOF");
	}

	@Override
	public void setTokenRegex() {
		TOKEN_REGEX = new HashMap<String, String>();
		TOKEN_REGEX.put("NOTE", "[A-G]");
		TOKEN_REGEX.put("REPEAT NOTE?", "[a-g]");
		TOKEN_REGEX.put("INCREASE VOLUME", "\\.\\,");
		TOKEN_REGEX.put("CHANGE INSTRUMENT", "\\!");
		TOKEN_REGEX.put("WHITESPACE", "\\s");
		TOKEN_REGEX.put("REST", "[h-zH-Z]");
		TOKEN_REGEX.put("EOF", "$");
	};
	
	/**
	 * Get the token types of the ruleset.
	 * 
	 * @return the token types of the ruleset
	 */
	@Override
	public List<String> getTokenTypes() {
		return TOKEN_TYPES;
	};
	
	/**
	 * Get the token regex of the ruleset.
	 * 
	 * @return the token regex of the ruleset
	 */
	@Override
	public HashMap<String, String> getTokenRegex() {
		return TOKEN_REGEX;
	};
	
	/**
	 * Decide the type of a token based on its value.
	 * 
	 * @param c the value of the token
	 * @return the type of the token
	 */
	public String matchType(char c) {
		for (String type : TOKEN_TYPES) {
			String regex = TOKEN_REGEX.get(type);
			try {
				if (regex == null) {
					return "WHITESPACE";
				}
				
				if (String.valueOf(c).matches(regex)) {
					return type;
				}
			} catch (Exception e) {
				return "INVALID";
			}
		};
		return null;
	};
	
	/**
	 * Decide the type of a token A, based on its value and the value of its previous token B
	 * 
	 * 
	 * @param A the value of the token
	 * @param B the value of the previous token
	 * @return the type of the token
	 */
	public String matchType(char A, char B) {
		if (String.valueOf(A).matches(TOKEN_REGEX.get("REPEAT NOTE?")) && String.valueOf(B).matches(TOKEN_REGEX.get("NOTE"))) {
            return "NOTE";
        } else {
            return "REST";
        }
    };
	
      
    
	/**
	 * Decide the value of a token based on its type.
	 * 
	 * @param type the type of the token
	 * @return the value of the token
	 */
    public String getTokenType(String s) {
    	if (s.length() == 0) {
    		return "EOF";
		} else {
			return matchType(s.charAt(0));
		}
    };
    
    
	public String getTokenValue(String s) {
    	if (s.length() == 0) {
    		return "";
    		} else {
				return String.valueOf(s.charAt(0));
			}
    };
	
}	
