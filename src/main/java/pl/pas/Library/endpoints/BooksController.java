package pl.pas.Library.endpoints;

import pl.pas.Library.dto.BookDto;
import pl.pas.Library.exceptions.BookNotFoundException;
import pl.pas.Library.DtoMapper.BookMapper;
import pl.pas.Library.managers.BookManager;
import pl.pas.Library.model.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@RequestScoped
@Path("/books")
public class BooksController {

    @Inject
    private BookManager bm;
    @Inject
    private BookMapper mapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(@Valid BookDto bookDto) {

        Book book = mapper.ConvertBookDtoToBook(bookDto);
        bm.addBook(book);
        return Response.status(201).entity(book).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("uuid") UUID uuid) {
        Book book = bm.getBook(uuid);
        return Response.ok().entity(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok().entity(bm.getAllBooks()).build();
    }

    @PUT
    @Path("/{uuid}")
    public Response updateBook(@PathParam("uuid") UUID uuid, @Valid BookDto bookDto) throws BookNotFoundException {
        Book book = mapper.ConvertBookDtoToBook(bookDto);
        bm.updateBook(uuid, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response removeBook(@PathParam("uuid") UUID uuid) {
        bm.removeBook(uuid);
        return Response.ok().build();
    }


}