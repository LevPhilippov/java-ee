package lev.filippov.service.rest;

import lev.filippov.models.CartItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/cart")
public interface CartRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CartItem> getAll();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    List<CartItem> addToCart(@PathParam("id") Long id);
}
