package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
