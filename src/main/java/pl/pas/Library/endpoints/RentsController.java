package pl.pas.Library.endpoints;

import pl.pas.Library.dto.RentPostDto;
import pl.pas.Library.managers.RentManager;
import pl.pas.Library.DtoMapper.RentMapper;
import pl.pas.Library.model.Rent;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Path("/rents")
public class RentsController {
    @Inject
    private RentManager rm;

    @Inject
    private RentMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRents() {
        List<Rent> rents = rm.getAllRents();
        return Response.ok().entity(rents).build();
    }

    // .../api/rents
    // .../api/rents/[uuid]?status=[]&obj=[]

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRent(@PathParam("uuid") UUID uuid) {
        Rent rent = rm.getRent(uuid);
        return Response.ok().entity(rent).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRent(@Valid RentPostDto rentPostDto) {
        Rent rent = mapper.convertRentDtoToRent(rentPostDto);
        rm.addRent(rent);
        return Response.status(201).entity(rent).build();
    }

    @PUT
    @Path("/{uuid}")
    public Response endRent(@PathParam("uuid") UUID uuid) {
        rm.endRent(uuid);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response removeRent(@PathParam("uuid") UUID uuid) {
        rm.removeRent(uuid);
        return Response.ok().build();
    }

}
