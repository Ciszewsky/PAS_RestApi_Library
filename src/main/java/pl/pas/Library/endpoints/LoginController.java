package pl.pas.Library.endpoints;

import pl.pas.Library.dto.CredentialDto;
import pl.pas.Library.managers.UserManager;
import pl.pas.Library.security.TokenAndSignatureGeneratorValidate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


@RequestScoped
@Path("/authenticate")
public class LoginController {

    @Inject
    IdentityStoreHandler identityStoreHandler;

    @Inject
    UserManager um;

    @Inject
    TokenAndSignatureGeneratorValidate tokenAndSignatureGeneratorValidate;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(CredentialDto credentialDto){
        Credential credential = new UsernamePasswordCredential(credentialDto.getLogin(),new Password(credentialDto.getPassword()));
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        if (result.getStatus() == CredentialValidationResult.Status.VALID){
            return Response.accepted().entity(tokenAndSignatureGeneratorValidate.getJWT(result)).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }


    //pomocniczna metoda zwracajaca wartośc do If-Match
    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response help(@PathParam("uuid") UUID uuid) {
        return Response.ok().entity(tokenAndSignatureGeneratorValidate.calculateUserSignature(um.getUser(uuid))).build();
    }

}
