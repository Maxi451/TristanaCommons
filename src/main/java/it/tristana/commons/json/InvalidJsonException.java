package it.tristana.commons.json;

@SuppressWarnings("serial")
public class InvalidJsonException extends RuntimeException {

	public InvalidJsonException() {
		super();
	}

	public InvalidJsonException(String message) {
		super(message);
	}
}
