package pl.pas.Library.exceptions;

public class RentRemoveAfterEndedException extends RuntimeException {
    public RentRemoveAfterEndedException(String message) {
        super(message);
    }
}
