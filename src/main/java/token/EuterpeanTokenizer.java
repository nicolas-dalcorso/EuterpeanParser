package token;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class EuterpeanTokenizer extends Tokenizer{
	private ArrayList<EuterpeanToken> tokens;
	private EuterpeanRuleset ruleset;
	private String input;
	private int current_position;
	private boolean is_end;
	private HashMap<String, String> token_regex;
	
	
	/**
	 * Creates a new {@code EuterpeanTokenizer}.
	 */
	public EuterpeanTokenizer() {
		ruleset = new EuterpeanRuleset();
		tokens = new ArrayList<EuterpeanToken>();
		current_position = 0;
		is_end = false;
	};

	public void setInput(String input) {
		this.input = input;
		current_position = 0;
		is_end = false;
	};
	
	@Override
	public void next() {
		if (current_position >= input.length()) {
			is_end = true;
			return;
		}

		String remaining_input = input.substring(current_position);
		String token_type = ruleset.getTokenType(remaining_input);
		String token_value = ruleset.getTokenValue(remaining_input);
		EuterpeanToken token = new EuterpeanToken(token_type, token_value);
		tokens.add(token);
		current_position += token_value.length();
	};
	
	public String getTokenType(String value) {
		return ruleset.getTokenType(value);
	};
	
	@Override
	public Token getCurrentToken() {
		if (tokens.size() > 0) {
			return tokens.get(tokens.size() - 1);
		} else {
			return null;
		}
	};
	
	@Override
	public boolean isEnd() {
		return is_end;
	};
	
	@Override
	public ArrayList<EuterpeanToken> tokenize(String input) {
		setInput(input);
		while (!is_end) {
			next();
		}
		return tokens;
	};
	
	public ArrayList<EuterpeanToken> getTokens() {
		return tokens;
	};
	
	public void reset() {
		tokens.clear();
		current_position = 0;
		is_end = false;
	};
	
	public void clear() {
		tokens.clear();
		input = "";
		current_position = 0;
		is_end = false;
	};
	
	
	


	//	Main Method
	public static void main(String[] args) {		
		EuterpeanTokenizer t = new EuterpeanTokenizer();
		String input = "Classificada como a maior iniciativa deste tipo, ou será?";
		ArrayList<EuterpeanToken> tokens = t.tokenize(input);
		for (Token token : tokens) {
			System.out.println(token);
		}
	}

	@Override
	public void setTokenTypes() {
		return;
		
	}

	@Override
	public void setTokenRegex() {
		return;
	}

	@Override
	public void setCurrentToken(Token t) {
		return;
	};
};