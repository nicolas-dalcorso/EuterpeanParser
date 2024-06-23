package parser;

import java.util.ArrayList;
import token.*;

public class EuterpeanParser {
	private EuterpeanTokenizer tokenizer;
	private String input;
	private ArrayList<EuterpeanToken> tokens;

	public EuterpeanParser() {
		tokenizer = new EuterpeanTokenizer();
        tokens = new ArrayList<EuterpeanToken>();
    };
    
	public void parse(String input) {
		this.input = input;
		ArrayList<EuterpeanToken> tokenList = new ArrayList<EuterpeanToken>();
		
		//	First iteration with the tokenizer
		tokenizer.setInput(input);
		while (!tokenizer.isEnd()) {
			tokenizer.next();
			if (tokenizer.getCurrentToken() != null) {
				tokenList.add((EuterpeanToken) tokenizer.getCurrentToken());
			}
		};
		
		//	Second iteration with the parser
		for (int i = 0; i < tokenList.size(); i++) {
			EuterpeanToken current_token = tokenList.get(i);
			
			//	Check if the token is a repeat note
			if (current_token.getType().equals("REPEAT NOTE?")) {
				if(i > 0) {
                    EuterpeanToken previous_token = tokenList.get(i - 1);
                    if (previous_token.getType().equals("NOTE")) {
                        tokenList.set(i, new EuterpeanToken("NOTE", previous_token.getValue()));
                    } else {
                        tokenList.set(i, new EuterpeanToken("REST", current_token.getValue()));
                    }
				}	else {
                    tokenList.set(i, new EuterpeanToken("REST", current_token.getValue()));
				};
			};
			
		
		};
		tokens = tokenList;
	};
	
	
	//	Main method
	public static void main(String[] args) {
		EuterpeanParser p = new EuterpeanParser();
		p.parse("a Bb cCdD eFgG a2b2c2d2e2f2g2");
		
		for (EuterpeanToken t : p.tokens) {
			System.out.println(t);
		}	
	};

}
