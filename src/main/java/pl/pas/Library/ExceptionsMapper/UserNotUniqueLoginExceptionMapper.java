package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.UserNotUniqueLoginException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotUniqueLoginExceptionMapper implements ExceptionMapper<UserNotUniqueLoginException> {
    @Override
    public Response toResponse(UserNotUniqueLoginException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
