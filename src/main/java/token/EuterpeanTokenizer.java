package token;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A {@code EuterpeanTokenizer} is a tokenizer that tokenizes a string input into {@code EuterpeanToken} objects.
 * It uses a {@code EuterpeanRuleset} to define the behavior of the tokenizer. 
 * 
 * @author nrdc
 * @since 1.0
 * @version 1.0
 * 
 * @see EuterpeanToken
 * @see EuterpeanRuleset
 * @see Tokenizer
 */
public class EuterpeanTokenizer extends Tokenizer{
	protected List<String> 				TOKEN_TYPES;
	protected HashMap<String, String> 	TOKEN_REGEX;
	public 	EuterpeanRuleset 			ruleset;
	private String 						currentToken;
	private String 						currentType;
	private int 						current_position;
	private String 						input;
	
	/**
     * Creates a new {@code EuterpeanTokenizer}.
     */
	public EuterpeanTokenizer() {
		ruleset = new EuterpeanRuleset();
		
		current_position = 0;
        currentToken = null;
        currentType = null;
        setTokenTypes();
        setTokenRegex();
    };

    /**
     * Sets the input of the {@code EuterpeanTokenizer}.
     * 
     * @param input
     */
	public void setInput(String input) {
		this.input = input;
		current_position = 0;
	};
	
	/**
     * Sets the current state of the {@code EuterpeanTokenizer}.
     * 
     * @param Token t
     */
	public void setState(EuterpeanToken t) {
		currentToken = t.getValue() + "";
        currentType = t.getType();
    }

	/**
     * Matches the given token to a token type.
     * 
     * @param token
     * @return
     */
	@Override
	public String match(String token) {
		return ruleset.match(token);
	};

	/**
     * Decides whether the current token shall be a repetition of a Note or a Rest
     * 
     * @param lastToken    the last token
     * @param currentToken the current token
     * @return the token type
     */
	@Override
	public String match(String lastToken, String currentToken) {
		return ruleset.match(lastToken, currentToken);
	};

	
	// 	------------------------------------------------------------------------------
	//	Technical debt from bad implementation of the interface
	@Override
	public void setTokenTypes() {
		return;
	};

	@Override
	public void setTokenRegex() {
		return;
	};

	@Override
	public Token getState() {
		return new EuterpeanToken(currentToken, currentType);
	};

	// 	------------------------------------------------------------------------------
	
	//	------------------------------------------------------------------------------
	//	Tokenization methods
	/**
	 * Gets the next char from the input string.
	 * 
	 * @return the next token
	 */
	public String next() {
		if (current_position >= input.length()) {
            return "EOF";
		}
		
		String token = input.substring(current_position, current_position + 1);
		current_position++;
		return token;
	};
	
	/**
     * Tokenizes the input string into a list of {@code EuterpeanToken} objects.
     * 
     * @return the list of {@code EuterpeanToken} objects
     */
	@Override
	public ArrayList<Token> tokenize() throws Exception {
        ArrayList<Token> tokens = new ArrayList<Token>();
        String lastToken = null;
        String currentToken = null;
        
        while (true) {
        	lastToken = currentToken;
        	try {
        		currentToken = next();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        	
        	//	Stopping condition
        	if (currentToken.equals("EOF")) {
        		tokens.add(new EuterpeanToken(currentToken, "EOF"));
                break;
        	};
        	
        	//	Gets the token type
        	String tokenType = match(lastToken, currentToken);
        	
        	System.out.println("Token: " + currentToken + " Type: " + tokenType);
        	
        	EuterpeanToken token = new EuterpeanToken(tokenType, currentToken);
        	tokens.add(token);
        }
        
        return tokens;
	};
	
	/**
	 * Program method. Wrapper for the {@code tokenize()} and the {@code setInput()} methods.
	 * 
	 * @param String input
	 * @return the list of {@code EuterpeanToken} objects
	 */
	public ArrayList<Token> run(String input) {
		setInput(input);
		try {
			return tokenize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};
	
	
	//	------------------------------------------------------------------------------
	//	main method
	/**
     * Main method for testing the {@code EuterpeanTokenizer}.
     * 
     * @param args
     */
	public static void main(String[] args) {
		EuterpeanTokenizer tokenizer = new EuterpeanTokenizer();
		String input = "A B C D E F G";
		ArrayList<Token> tokens = tokenizer.run(input);
		for (Token token : tokens) {
			System.out.println((EuterpeanToken) token);
		}
    };
	
    
};
	
	
	
	
	
	
	