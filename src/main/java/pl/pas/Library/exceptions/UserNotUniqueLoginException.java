package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class UserNotUniqueLoginException extends RuntimeException {
    public UserNotUniqueLoginException(String message) {
        super(message);
    }
}
