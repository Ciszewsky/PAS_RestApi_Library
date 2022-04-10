package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.UserNotUniqueIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotUniqueIdExceptionMapper implements ExceptionMapper<UserNotUniqueIdException> {
    @Override
    public Response toResponse(UserNotUniqueIdException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
