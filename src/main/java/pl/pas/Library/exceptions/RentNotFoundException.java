package pl.pas.Library.exceptions;

import javax.ws.rs.NotFoundException;

public class RentNotFoundException extends NotFoundException {
    public RentNotFoundException(String message) {
        super(message);
    }
}
