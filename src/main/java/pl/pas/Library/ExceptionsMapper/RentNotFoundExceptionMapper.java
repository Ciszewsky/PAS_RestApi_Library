package pl.pas.Library.ExceptionsMapper;

import pl.pas.Library.exceptions.RentNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RentNotFoundExceptionMapper implements ExceptionMapper<RentNotFoundException> {
    @Override
    public Response toResponse(RentNotFoundException ex){
        return Response.status(404).entity(ex.getMessage()).build();
    }
}
