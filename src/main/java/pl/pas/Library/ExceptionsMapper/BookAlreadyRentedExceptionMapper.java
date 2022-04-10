package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.BookAlreadyRentedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookAlreadyRentedExceptionMapper implements ExceptionMapper<BookAlreadyRentedException> {
    @Override
    public Response toResponse(BookAlreadyRentedException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
