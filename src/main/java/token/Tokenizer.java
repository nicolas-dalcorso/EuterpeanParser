package token;

import java.util.ArrayList;

public abstract class Tokenizer {
	protected String currentType;
	protected char currentValue;
	protected String currentToken;
	protected int current_position;
	protected String input;
	
	/**
	 * Creates a new {@code Tokenizer}.
	 */
	public Tokenizer() {
		current_position = 0;
		currentToken = null;
		currentType = null;
		currentValue = '\0';
		setTokenTypes();
		setTokenRegex();
	};
	

	/**
	 * Sets the input of the {@code Tokenizer}.
	 * 
	 * @param input
	 */
	public void setInput(String input) {
		this.input = input;
		current_position = 0;
	};

	/**
	 * Sets the current state of the {@code Tokenizer}.
	 * 
	 * @param Token t
	 */
	public void setState(Token t) {
		currentToken = t.getValue() + "";
		currentType = t.getType();
	};

	/**
	 * Matches the given token to a token type.
	 * 
	 * @param token
	 * @return
	 */
	public abstract String match(String token);

	/**
	 * Decides if the current token shall be a repetition of a Note or a Rest
	 * 
	 * @param lastToken    the last token
	 * @param currentToken the current token
	 * @return the token type
	 */
	public abstract String match(String lastToken, String currentToken);

	/**
	 * Sets the token types of the {@code Tokenizer}.
	 */
	public abstract void setTokenTypes();

	/**
	 * Sets the token regex of the {@code Tokenizer}.
	 */
	public abstract void setTokenRegex();


	/**
     * Get the current state of the {@code Tokenizer}.
     * 
     * @return the current state of the {@code Tokenizer}
     */
    public abstract Token getState();
    
    /**
     * Tokenize the input and returns a ArrayList<Token>.
     * 
     * @return the ArrayList<Token> of the input
     * @throws Exception
     */
    public abstract ArrayList<Token> tokenize() throws Exception;
}
