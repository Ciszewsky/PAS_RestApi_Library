package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class UserUsedInCurrentRentException extends RuntimeException {
    public UserUsedInCurrentRentException(String message) {
        super(message);
    }
}
