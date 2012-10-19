
public class BadConfigFormatException extends Exception {

	public BadConfigFormatException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Bad Config Format. Please try again";
	}
	
}
