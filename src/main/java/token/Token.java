package token;

/**
 * {@code Token} is an abstract class that represents a token.
 * A {@code Token} object has a String type and a char value.
 * 
 * @see ASCIIToken
 * @see Tokenizer
 * @see Parser
 */
public abstract class Token {
	private char value;
	private String type;

	/**
	 * Constructor for {@code Token} class
	 * 
	 * @param String type
	 * @param char value
	 */
	public Token(String type, char value) {
		this.type = type;
		this.value = value;
	};
	
	/**
     * Constructor for {@code Token} class
     * 
     * @param char value
     */
	public Token(char value) {
		this.value = value;
        this.type = null;
	};
	
	/**
	 * Constructor for {@code Token} class
	 */
	public Token() {
		this.value = '\0';
        this.type = null;
	};
	
	/**
	 * Getter method for value
	 * 
	 * @return char
	 */
	public char getValue() {
		return value;
    };
    
    /**
     * Getter method for type
     * 
     * @return String
     */
    public String getType() {
        return type;
    };
    
	/**
	 * Setter method for value
	 * 
	 * @param char value
	 */
	public void setValue(char value) {
		this.value = value;
	};
	
	/**
	 * Setter method for type
	 * 
	 * @param String type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	public abstract String toString();
	
	public abstract boolean equals(Token t);
	
	public abstract boolean isValid();
}
