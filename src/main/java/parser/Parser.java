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

	//	Parser methods
	public void parse() {
		tokens = new ArrayList<Token>();
		while (!tokenizer.isEnd()) {
			tokenizer.next();
			tokens.add(tokenizer.getCurrentToken());
		}
	};
	
	//	Getter methods
	public Tokenizer getTokenizer() {
		return tokenizer;
	};
	
	public List<Token> getTokens() {
		if (tokens == null) {
			parse();
		}
		return tokens;
	};
	
	//	Setter methods
	public void setTokenizer(Tokenizer t) {
		tokenizer = t;
	};
	
	public void setInput(String i) {
		input = i;
		tokenizer.input = i;
	};
};
