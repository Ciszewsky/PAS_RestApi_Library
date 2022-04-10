package pl.pas.Library.ExceptionsMapper;


import pl.pas.Library.exceptions.RentNotUniqueIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RentNotUniqueIdExceptionMapper implements ExceptionMapper<RentNotUniqueIdException> {
    @Override
    public Response toResponse(RentNotUniqueIdException ex){
        return Response.status(500).entity(ex.getMessage()).build();
    }
}
