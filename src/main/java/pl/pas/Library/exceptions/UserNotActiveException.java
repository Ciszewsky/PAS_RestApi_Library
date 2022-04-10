package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class UserNotActiveException extends NotFoundException {
    public UserNotActiveException(String message) {
        super(message);
    }
}
