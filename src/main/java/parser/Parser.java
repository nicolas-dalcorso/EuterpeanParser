package parser;

import java.util.List;
import java.util.ArrayList;
import token.Token;
import token.Tokenizer;

/**
 * The {@code Parser} class is an abstract class that is used to parse a given input.
 * A {@code Parser} object is initialized with a {@code Tokenizer} object, responsible for tokenizing the input.
 * 
 * @see Tokenizer
 * @see Token
 * @author nrdc
 * @version 1.0
 * @since 1.0
 */
public abstract class Parser {
	protected Tokenizer 	tokenizer;
	protected String		input;
	protected List<Token>	tokens;

	public Parser() {
		tokenizer = null;
    };
    
	public Parser(Tokenizer t) {
		tokenizer = t;
	};
	
	/**
	 * Parses the input and returns a list of tokens.
	 * 
	 * @param input
	 * @return
	 */
	public abstract List<Token> parse(String input);
	
	/**
	 * Sets the input of the parser.
	 * 
	 * @param input
	 */
	public void setInput(String input) {
		this.input = input;
    };
};
