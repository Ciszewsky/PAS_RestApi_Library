package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.BookNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException ex){
        return Response.status(404).entity(ex.getMessage()).build();
    }
}
