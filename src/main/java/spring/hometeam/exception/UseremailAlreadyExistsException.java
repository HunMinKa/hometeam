package spring.hometeam.exception;

public class UseremailAlreadyExistsException extends RuntimeException {
    public UseremailAlreadyExistsException(String message) {
        super(message);
    }
}