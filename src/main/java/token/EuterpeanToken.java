package token;

public class EuterpeanToken extends Token{
	public EuterpeanToken(String type, char value) {
		super(type, value);
	};

	public EuterpeanToken(char value) {
		super(value);
	};

	public EuterpeanToken() {
		super();
	}

	@Override
	public String toString() {
		return "<T: " + getType() + ", v: " + getValue() + ">";
	}

	@Override
	public boolean equals(Token t) {
		return (t.getType().equals(getType()) && t.getValue() == getValue());
	}

	@Override
	public boolean isValid() {
		return (getType() != null && getValue() != '\0');
	};
}
