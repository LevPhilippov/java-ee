package lev.filippov.service.rest;

import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/v1/products")
public interface ProductRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductDto> getAll();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/category/{id}")
    List<ProductDto> getAll(@PathParam("id") Long catId);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    Optional<Product> getProductById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void save(ProductDto dto);

    @PUT
    @Consumes("application/json")
    void insert(ProductDto dto);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}
