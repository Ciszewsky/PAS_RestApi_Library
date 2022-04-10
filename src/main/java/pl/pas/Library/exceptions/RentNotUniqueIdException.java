package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class RentNotUniqueIdException extends RuntimeException {
    public RentNotUniqueIdException(String message) {
        super(message);
    }
}
