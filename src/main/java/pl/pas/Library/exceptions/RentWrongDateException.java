package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class RentWrongDateException extends RuntimeException {
    public RentWrongDateException(String message) {
        super(message);
    }
}
