package lev.filippov.service.rest;

import lev.filippov.models.Category;
import lev.filippov.models.dto.CategoryDto;
import lev.filippov.models.dto.ProductDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/category")
public interface CategoryRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CategoryDto> getAll();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    CategoryDto getCategoryById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(CategoryDto dto);

    @PUT
    @Consumes("application/json")
    void update(CategoryDto dto);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);

}
