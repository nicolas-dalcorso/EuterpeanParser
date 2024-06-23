package token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Tokenizer {
	protected static List<String> TOKEN_TYPES;
	protected static Map<String, String> TOKEN_REGEX;
	protected static Map<String, String> TOKEN_TYPES_MAP;
	public String input;
	public int index;
	protected Token currentToken;
	public String currentType;
	public char currentValue;
	
	

	public Tokenizer() {
		index = 0;
		currentToken = null;
		currentType = null;
		currentValue = '\0';
	};
	
	//	Initialization methods
	public abstract void setTokenTypes();
	
	public abstract void setTokenRegex();
	
	public abstract void setTokenTypesMap();
	
	//	State methods
	public void setState(Token t) {
		setCurrentToken(t);
		setCurrentType();
		setCurrentValue();
	};
	
	public boolean isEnd() {
		return index >= input.length();
	};
	
	public abstract void next();
	
	public void back() {
		if (index > 0) {
			index--;
		} else {
			index = 0;
		};
	};
	
	
	//	Getter methods
	public Token getCurrentToken() {
		return currentToken;
	};
	
	public String getCurrentType() {
		return currentType;
	};
	
	public char getCurrentValue() {
		return currentValue;
	};
	
	//	Setter methods
	public abstract void setCurrentToken(Token t);
	
	public void setCurrentType() {
		currentType = TOKEN_TYPES_MAP.get(currentToken.getType());
	};
	
	public void setCurrentValue() {
		currentValue = currentToken.getValue();
	};


	//	Program method
	public List<Token> tokenize(String input) {
		this.input = input;
		List<Token> tokens = new ArrayList<Token>();
		while (!isEnd()) {
			next();
			if (currentToken != null) {
				tokens.add(currentToken);
			}
		}
		return tokens;
	};

}
