package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class UserNotUniqueIdException extends RuntimeException {
    public UserNotUniqueIdException(String message) {
        super(message);
    }
}
