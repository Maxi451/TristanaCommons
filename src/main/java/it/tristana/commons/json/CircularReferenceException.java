package it.tristana.commons.json;

@SuppressWarnings("serial")
public class CircularReferenceException extends RuntimeException {

	public CircularReferenceException() {
		super();
	}
	
	public CircularReferenceException(String message) {
		super(message);
	}
}
