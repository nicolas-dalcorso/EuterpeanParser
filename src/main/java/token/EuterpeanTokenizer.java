package token;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class EuterpeanTokenizer extends Tokenizer{
	private List<String> TOKEN_TYPES;
	private HashMap<String, String> TOKEN_REGEX;
	private HashMap<String, String> TOKEN_TYPES_MAP;
	private Token currentToken;
	private String currentType;
	private char currentValue;
	private String input;
	private int index;
	
	
	
	public EuterpeanTokenizer() {
		super();
		setTokenTypes();
		setTokenRegex();
		setTokenTypesMap();
	}

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

	public void setTokenRegex() {
		TOKEN_REGEX = new HashMap<String, String>();
		TOKEN_REGEX.put("NOTE", "[A-G]");
		TOKEN_REGEX.put("REPEAT NOTE?", "[a-g]");
		TOKEN_REGEX.put("INCREASE VOLUME", "\\.\\,");
		TOKEN_REGEX.put("CHANGE INSTRUMENT", "\\!");
		TOKEN_REGEX.put("WHITESPACE", "\\s+");
		TOKEN_REGEX.put("REST", "[h-zH-Z]");
		TOKEN_REGEX.put("EOF", "$");
	}

	public void setTokenTypesMap() {
        TOKEN_TYPES_MAP = new HashMap<String, String>();
        TOKEN_TYPES_MAP.put("A", "LA");
        TOKEN_TYPES_MAP.put("B", "SI");
        TOKEN_TYPES_MAP.put("C", "DO");
        TOKEN_TYPES_MAP.put("D", "RE");
        TOKEN_TYPES_MAP.put("E", "MI");
        TOKEN_TYPES_MAP.put("F", "FA");
        TOKEN_TYPES_MAP.put("G", "SOL");
        TOKEN_TYPES_MAP.put("z", "REST");
        TOKEN_TYPES_MAP.put(".", "INCREASE VOLUME");
        TOKEN_TYPES_MAP.put(",", "DECREASE VOLUME");
        TOKEN_TYPES_MAP.put("EOF", "EOF");
	};

	/**
	 * Matches a char from the input to a token type
	 * 
	 * @param char c
	 * @return String type
	 */
	public String matchType(char c) {
		try {
		for (String type : TOKEN_TYPES) {
				String regex = TOKEN_REGEX.get(type);
				if (String.valueOf(c).matches(regex)) {
					return type;
				}
			}
			
			return "INVALID";
		
		} catch (Exception e) {
			return "INVALID";
		}
	};
	
	public void next() {
		if (isEnd()) {
			currentToken = new EuterpeanToken("EOF", '\0');
			setCurrentType();
			setCurrentValue();
			return;
		}
		char c = input.charAt(index);
		if (String.valueOf(c).matches("\\s+")) {
			currentToken = new EuterpeanToken("WHITESPACE", c);
		}	else {
				String type = matchType(c);
	            if (type == "INVALID") {
	                currentToken = new EuterpeanToken("INVALID", c);
	            } 	else {
	                	currentToken = new EuterpeanToken(type, c);
	            	};
	            	
			};
		
		setCurrentType();
		setCurrentValue();
		index++;
		return;
	};
	
	public void setCurrentType() {
		currentType = currentToken.getType();
	};
	
	public void setCurrentValue() {
		currentValue = currentToken.getValue();
	};
	
	public void setCurrentToken(Token t) {
		currentToken = t;
	};
	
	public Token getCurrentToken() {
		return currentToken;
	};
	
	public String getCurrentType() {
		return currentType;
	};
	
	public char getCurrentValue() {
		return currentValue;
	};
	
	public boolean isEnd() {
		return index >= input.length();
	};
	
	public void back() {
		if (index > 0) {
			index--;
		} else {
			index = 0;
		};
	};
	
	public void setInput(String i) {
		input = i;
		index = 0;
	};
	
	public String getInput() {
		return input;
	};
	
	public int getIndex() {
		return index;
	};
	
	public void setIndex(int i) {
		index = i;
	};
	
	//	Program
	public List<Token> tokenize(String input) {
		setInput(input);
		List<Token> tokens = new ArrayList<Token>();
		while (!isEnd()) {
			next();
			if (currentToken != null) {
				tokens.add(currentToken);
			}
		}
		return tokens;
	};
	


	//	Main Method
	public static void main(String[] args) {		
		EuterpeanTokenizer t = new EuterpeanTokenizer();
		String input = "Classificada como a maior iniciativa deste tipo já realizada no Rio Grande do Sul, ação reuniu cerca de 2 mil pessoas neste domingo para auxiliar moradores dos bairros Humaitá e Vila Farrapos. A região foi a mais atingida pela enchente de maio na Capital";
		List<Token> tokens = t.tokenize(input);
		for (Token token : tokens) {
			System.out.println(token);
		}
	};
};