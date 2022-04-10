package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class BookNotUniqueIdException extends RuntimeException {
    public BookNotUniqueIdException(String message) {
        super(message);
    }
}
