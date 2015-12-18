package xeasony.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("RESOURCE_NOT_FOUND");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
