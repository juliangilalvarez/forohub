package forohub.forohub.modelo.exceptions;



public class TokenServiceException extends RuntimeException {
    public TokenServiceException(String message) {
        super(message);
    }
    public TokenServiceException(String message, Throwable cause) { super(message, cause); }
}