package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
