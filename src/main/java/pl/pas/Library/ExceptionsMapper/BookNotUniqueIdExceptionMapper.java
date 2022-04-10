package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.BookNotUniqueIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookNotUniqueIdExceptionMapper implements ExceptionMapper<BookNotUniqueIdException> {
    @Override
    public Response toResponse(BookNotUniqueIdException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
