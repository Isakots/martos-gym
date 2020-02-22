package hu.isakots.martosgym.exception;

public class ResourceNotFoundException extends Exception {
    private final String message;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
