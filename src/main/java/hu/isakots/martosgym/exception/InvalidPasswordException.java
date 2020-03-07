package hu.isakots.martosgym.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
       super(message);
    }
}