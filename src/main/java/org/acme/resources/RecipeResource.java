package org.acme.resources;

import org.acme.dto.request.CategoryRequest;
import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.dto.response.RecipeResponse;
import org.acme.mapper.CategoryMapper;
import org.acme.mapper.RecipeMapper;
import org.acme.models.Category;
import org.acme.models.Recipe;
import org.acme.service.CategoryService;
import org.acme.service.RecipeService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/recipe")
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    private RecipeService recipeService;
    
    @Inject
    private RecipeMapper recipeMapper;

    @GET
    public Response getAll() {
        List<Recipe> recipe = recipeService.getAll();
        List<RecipeResponse> categoriesDto = recipeMapper.toResourceList(recipe);
        return Response.ok(categoriesDto).build();
    }

    @POST
    @Transactional
    @RolesAllowed("user")
    public Response create(@Valid RecipeRequest obj) {
        Recipe recipeDto = recipeMapper.toResource(obj);
        Recipe recipe = recipeService.create(recipeDto);
        return Response.created(URI.create("/category/" + recipe.id)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    public Response getById(Long id) {
        Recipe recipe = recipeService.getById(id);
        return Response.ok(recipeMapper.toResource(recipe)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response update(RecipeRequest obj, Long id) {
        Recipe recipe = recipeService.update(recipeMapper.toResource(obj), id);
        return Response.ok(recipeMapper.toResource(recipe)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(Long id) {
        recipeService.delete(id);
        return Response.noContent().build();
    }
}