package org.acme.resources;

import org.acme.dto.request.CategoryRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.mapper.CategoryMapper;
import org.acme.models.Category;
import org.acme.service.CategoryService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    private CategoryService categoryService;
    
    @Inject
    private CategoryMapper categoryMapper;

    @GET
    public Response getAll() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponse> categoriesDto = categoryMapper.toResourceList(categories);
        return Response.ok(categoriesDto).build();
    }

    @POST
    @Transactional
    @RolesAllowed("user")
    public Response create(@Valid CategoryRequest obj) {
        Category categoryDto = categoryMapper.toResource(obj);
        Category category = categoryService.create(categoryDto);
        return Response.created(URI.create("/category/" + category.id)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    public Response getById(Long id) {
        Category category = categoryService.getById(id);
        return Response.ok(categoryMapper.toResource(category)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response update(CategoryRequest obj, Long id) {
        Category category = categoryService.update(categoryMapper.toResource(obj), id);
        return Response.ok(categoryMapper.toResource(category)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(Long id) {
        categoryService.delete(id);
        return Response.noContent().build();
    }
}