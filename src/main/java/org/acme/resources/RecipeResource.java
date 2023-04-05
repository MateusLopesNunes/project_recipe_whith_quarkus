package org.acme.resources;

import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.RecipeResponse;
import org.acme.mapper.RecipeMapper;
import org.acme.models.Recipe;
import org.acme.service.RecipeService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

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
    public Response getAll(@QueryParam("page") Integer page,
                           @QueryParam("pageSize") Integer pageSize) {

        List<Recipe> recipe = recipeService.getAll(page, pageSize);
        List<RecipeResponse> categoriesDto = recipeMapper.toResourceList(recipe);
        return Response.ok(categoriesDto).build();
    }

    @GET
    @Path("/category/{categoryId}")
    public Response getRecipeByCategory(@QueryParam("page") Integer page,
                           @QueryParam("pageSize") Integer pageSize, @PathParam Long categoryId) {

        List<Recipe> recipe = recipeService.getByCategory(page, pageSize, categoryId);
        List<RecipeResponse> categoriesDto = recipeMapper.toResourceList(recipe);
        return Response.ok(categoriesDto).build();
    }

    @GET
    @Path("/user/{userId}")
    public Response getRecipeByUser(@QueryParam("page") Integer page,
                                        @QueryParam("pageSize") Integer pageSize, @PathParam Long userId) {

        List<Recipe> recipe = recipeService.getByUser(page, pageSize, userId);
        List<RecipeResponse> categoriesDto = recipeMapper.toResourceList(recipe);
        return Response.ok(categoriesDto).build();
    }

    @POST
    @Transactional
    //@RolesAllowed("user")
    public Response create(@Valid RecipeRequest obj) {
        Recipe recipeDto = recipeMapper.toResource(obj);
        Recipe recipe = recipeService.create(recipeDto);
        return Response.created(URI.create("/category/" + recipe.id)).build();
    }

    @PATCH
    @Path("/uploadImage/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response uploadImage(@MultipartForm MultipartFormDataInput input, @PathParam Long id) {
        Recipe recipe = recipeService.uploadImage(input, id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    public Response getById(@PathParam Long id) {
        Recipe recipe = recipeService.getById(id);
        return Response.ok(recipeMapper.toResource(recipe)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response update(RecipeRequest obj, @PathParam Long id) {
        Recipe recipe = recipeService.update(obj, id);
        return Response.ok(recipeMapper.toResource(recipe)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(@PathParam Long id) {
        recipeService.delete(id);
        return Response.noContent().build();
    }
}