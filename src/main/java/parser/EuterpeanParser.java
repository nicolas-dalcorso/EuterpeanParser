package parser;

import java.util.ArrayList;
import token.*;

/**
 * A {@code EuterpeanParser} is the class responsible for getting the input from the System, parsing it and returning a list of {@code EuterpeanToken} objects.
 * The tokens are obtained from the {@code EuterpeanTokenizer} class and then processed by the parser.
 * Finally, the parser returns a list of tokens that can be used by the {@code NoteBuilder} class to build the {@code MusicString} object.
 * 
 * @see EuterpeanTokenizer
 * @see EuterpeanToken
 * @see NoteBuilder
 * @author nrdc
 * @version 1.0
 * @since 1.0
 */
public class EuterpeanParser {
	private EuterpeanTokenizer 			tokenizer;
	private String 						input;
	private ArrayList<EuterpeanToken> 	tokens;

	public EuterpeanParser() {
		tokenizer 	= new EuterpeanTokenizer();
        tokens 		= new ArrayList<EuterpeanToken>();
    };
    
    public EuterpeanParser(EuterpeanTokenizer t) {
		tokenizer = t;
	};
	
	/**
	 * Parses the input and returns a list of tokens.
	 * 
	 * @param input
	 * @return
	 */
	public ArrayList<EuterpeanToken> parse(String input) {
		this.setInput(input);
		ArrayList<EuterpeanToken> euterpeanTokens 	= new ArrayList<EuterpeanToken>();
		ArrayList<Token>					tokens 	= tokenizer.run(input);
		
		for (Token t : tokens) {
			euterpeanTokens.add((EuterpeanToken) t);
		}
		
		return euterpeanTokens;
	};
	
	/**
	 * Sets the input of the parser.
	 * @param String input
	 */
	public void setInput(String input) {
		this.input = input;
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
