package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.BookAlreadyRentedException;
import pl.pas.Library.exceptions.UserNotActiveException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotActiveExceptionMapper implements ExceptionMapper<UserNotActiveException> {
    @Override
    public Response toResponse(UserNotActiveException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
