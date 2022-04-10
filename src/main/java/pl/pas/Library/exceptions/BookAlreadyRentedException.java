package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class BookAlreadyRentedException extends RuntimeException {
    public BookAlreadyRentedException(String message) {
        super(message);
    }
}
