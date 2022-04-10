package pl.pas.Library.endpoints;

import pl.pas.Library.dto.UserPostDto;
import pl.pas.Library.DtoMapper.UserMapper;
import pl.pas.Library.managers.UserManager;
import pl.pas.Library.model.User;
import pl.pas.Library.security.TokenAndSignatureGeneratorValidate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Path("/users")
public class UsersController {
    @Inject
    UserManager um;

    @Inject
    UserMapper mapper;

    @Inject
    TokenAndSignatureGeneratorValidate tokenAndSignatureGeneratorValidate;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("uuid") UUID uuid) {
        User user = um.getUser(uuid);
        return Response.ok().entity(user).build();
    }

    // .../api/users
    // .../api/users?type=[]&login=[]
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@QueryParam("type") String type, @QueryParam("login") String login) {
        if (type != null && login != null) {
            if (type.equals("part")) {
                List<User> users = um.findAllUsers(login);
                return Response.ok().entity(users).build();
            } else if (type.equals("full")) {
                User user = um.findUser(login);
                return Response.ok().entity(user).build();
            } else {
                return Response.status(400).build();
            }
        }
        if (type == null && login == null) {
            List<User> users = um.getAllUsers();
            return Response.ok().entity(users).build();
        }
        return Response.status(400).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@Valid UserPostDto userPostDto) {
        User user = mapper.ConvertUserPostDtoToUser(userPostDto);
        um.addUser(user);
        return Response.status(201).entity(user).build();
    }

    @PUT
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("uuid") UUID uuid, @Valid UserPostDto userPostDto,@NotNull @NotEmpty @HeaderParam("If-Match") String tagValue) {
        User newUser = mapper.ConvertUserPostDtoToUser(userPostDto);
        User user = um.getUser(uuid);

        if(!tokenAndSignatureGeneratorValidate.verifySignatureIntefrity(tagValue,user)){
            return Response.status(Response.Status.BAD_REQUEST).build(); //tymczasowe, mozna  rzucać wyjatek
        }

        um.updateUser(uuid, newUser);
        return Response.ok().build();
    }

    @PUT
    @Path("/{uuid}/activate")
    public Response activeUser(@PathParam("uuid") UUID uuid) {
        um.changeActivityOfUser(uuid, true);
        return Response.ok().build();
    }

    @PUT
    @Path("/{uuid}/deactivate")
    public Response deactivateUser(@PathParam("uuid") UUID uuid) {
        um.changeActivityOfUser(uuid, false);
        return Response.ok().build();
    }

}
