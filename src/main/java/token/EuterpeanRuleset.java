package token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@code EuterpeanRuleset} is a set of rules that define the behavior of a {@token EuterpeanTokenizer}.
 * It produces {@code EuterpeanToken} objects.
 * 
 * Whitespace matching with the regex API in Java can be tricky. The class constants {@code WHITESPACE_CHARACTERS} and {@code WHITESPACE_REGEX_CLASS}
 * are used to define whitespace characters and whitespace regexes.
 * @see EuterpeanTokenizer
 * @see EuterpeanToken
 * @see Token
 * @see Ruleset 
 */
public class EuterpeanRuleset implements Ruleset {
	private List<String> 			TOKEN_TYPES;
	private HashMap<String, String> TOKEN_REGEX;
	
	//	-------------------------------------------------------------------------------------------------------------
	private static final String WHITESPACE_CHARACTERS = ""       /* dummy empty string for homogeneity */
										            + "\\u0009" // CHARACTER TABULATION
										            + "\\u000A" // LINE FEED (LF)
										            + "\\u000B" // LINE TABULATION
										            + "\\u000C" // FORM FEED (FF)
										            + "\\u000D" // CARRIAGE RETURN (CR)
										            + "\\u0020" // SPACE
										            + "\\u0085" // NEXT LINE (NEL) 
										            + "\\u00A0" // NO-BREAK SPACE
										            + "\\u1680" // OGHAM SPACE MARK
										            + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
										            + "\\u2000" // EN QUAD 
										            + "\\u2001" // EM QUAD 
										            + "\\u2002" // EN SPACE
										            + "\\u2003" // EM SPACE
										            + "\\u2004" // THREE-PER-EM SPACE
										            + "\\u2005" // FOUR-PER-EM SPACE
										            + "\\u2006" // SIX-PER-EM SPACE
										            + "\\u2007" // FIGURE SPACE
										            + "\\u2008" // PUNCTUATION SPACE
										            + "\\u2009" // THIN SPACE
										            + "\\u200A" // HAIR SPACE
										            + "\\u2028" // LINE SEPARATOR
										            + "\\u2029" // PARAGRAPH SEPARATOR
										            + "\\u202F" // NARROW NO-BREAK SPACE
										            + "\\u205F" // MEDIUM MATHEMATICAL SPACE
										            + "\\u3000" // IDEOGRAPHIC SPACE
										            ;        
	
	private static final String WHITESPACE_REGEX_CLASS = "[" + WHITESPACE_CHARACTERS + "]";
	
	@SuppressWarnings("unused")
	private static final String NOT_WHITESPACE_REGEX_CLASS = "[^" + WHITESPACE_CHARACTERS + "]";
	//	-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Construct a new {@code EuterpeanRuleset}.
	 */
	public EuterpeanRuleset() {
		TOKEN_TYPES = new ArrayList<String>();
        TOKEN_REGEX = new HashMap<String, String>();
        initialize();
	    }
	
	//	Initialization and rule setting methods
	/**
	 * Initialize the token types and token regex of the ruleset.
	 */
	private void initialize() {
		setTokenTypes();
        setTokenRegex();
    };
    
    /**
     * Sets the token types of the ruleset. Each token type is a string associated with a unique action or marker.
     */
    public void setTokenTypes() {
    	//	A Note can be obtained through
    	//		any uppercase character from 'A' (65) to 'G' (71)
    	//		any lowercase character from 'a' (97) to 'g' (103) if the last token was a Note
    	//		any other consonant ('B', 'C', 'D', 'F', 'G'), uppercase or lowercase, if the last token was a Note
    	//		any other character, uppercase or lowercase, if the last token was a Note.
    	//	To each of the situations above, if the last token wasn't a Note, the current token shall be a Rest
    	TOKEN_TYPES.add("NOTE");
        TOKEN_TYPES.add("REST");
        
        //	A Double Volume action is the WHITESPACE character (32).
        //	It is used to denote a change in volume.
        TOKEN_TYPES.add("DOUBLE_VOLUME");
        
        //	A Instrument Change can be obtained through
        //    	the character '!' (33) :: changes the instrument to the Agogo (MIDI#114)
        //	  	the chracaters 'i', 'I', 'o', 'O', 'u', 'U' (105, 73, 117, 85) :: changes the instrument to the Harpsichord (MIDI#7)
        //		the newline character '\n' (10) :: changes the instrument to the Tubular Bells (MIDI#15)
        //      the character ';' (111) :: changes the instrument to the Pan Flute (MIDI#76)
        //      the character ',' (44) :: changes the instrument to the Church Organ (MIDI#20)
        //		any DIGIT (48-57) :: changes the instrument to (MIDI#(CURRENT_INSTRUMENT + DIGIT))
        TOKEN_TYPES.add("INSTRUMENT_CHANGE");
        
        //	A Octave Change can be obtained through the characters '?' (63) and '.' (46).
        //	These characters are used to denote a one octave increase.
        TOKEN_TYPES.add("OCTAVE_CHANGE");
    };
    
	/**
	 * Sets the token regex of the ruleset. Each token regex is a string associated
	 * with a unique action or marker.
	 */
    public void setTokenRegex() {
    	//	Double Volume regex
    	TOKEN_REGEX.put("DOUBLE_VOLUME", " ");

    	//	Note regex
    	TOKEN_REGEX.put("NOTE", "[A-Ga-g]");
    	    	
    	
    	//	Instrument Change regex
    	TOKEN_REGEX.put("INSTRUMENT_CHANGE", "[!iIoOuU\n;0-9]");
    	
    	//	Octave Change regex
    	TOKEN_REGEX.put("OCTAVE_CHANGE", "[?.]");
    }

	
    
    
    //    Getters
    @Override
	public List<String> getTokenTypes() {
		return TOKEN_TYPES;
	};
	

	@Override
	public HashMap<String, String> getTokenRegex() {
		return TOKEN_REGEX;
	};
	
	
	//	Overridden methods
	/**
	 * Matches the given token to a token type.
	 * @param token
	 * @return
	 */
	public String match(String token) {
		//	Whitespace matching
		if (token.matches(WHITESPACE_REGEX_CLASS)) {
			return "DOUBLE_VOLUME";
		}	else {
			for (Map.Entry<String, String> entry : TOKEN_REGEX.entrySet()) {				
				if (token.matches(entry.getValue())) {
					return entry.getKey();
				}
			}
			
			return "INVALID_TOKEN";
		}
	};
	
	/**
	 * 	Decides if the current token shall be a repetition of a Note or a Rest
	 * 
	 * @param lastToken the last token
	 * @param currentToken the current token
	 * @return the token type
	 */
	public String match(String lastToken, String currentToken) {
		if (lastToken == null &&  currentToken != null) {
			return match(currentToken);
		} else if (lastToken.equals("NOTE") || currentToken.matches("[a-g]")) {
					return "NOTE";
				};		
		return match(currentToken);
	};
	
	/**
	 * Checks if the given token is a valid
	 * @param token
	 * @return
	 */
	public boolean isToken(String token) {
		for (Map.Entry<String, String> entry : TOKEN_REGEX.entrySet()) {
			if (token.matches(entry.getValue())) {
				return true;
			}
		}
		return false;
	};
    
    
	/**
     * Returns the token type of the given token.
     * @param token
     * @return
     */
    public String getTokenType(String token) {
    	for (Map.Entry<String, String> entry : TOKEN_REGEX.entrySet()) {
            if (token.matches(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    };
    
    // 	Main
	public static void main(String[] args) {
		EuterpeanRuleset ruleset = new EuterpeanRuleset();
		System.out.println(ruleset.getTokenTypes());
		System.out.println(ruleset.getTokenRegex());
	
		String token = "A";
		System.out.println(ruleset.match(token));
		
		token = " ";
		System.out.println(ruleset.match(token));
		
		token = "?";
		System.out.println(ruleset.match(token));
	};
};	
