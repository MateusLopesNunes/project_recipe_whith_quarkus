package org.acme.resources;

import org.acme.dto.request.ImagePath;
import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.RecipeResponse;
import org.acme.mapper.RecipeMapper;
import org.acme.models.Recipe;
import org.acme.service.ImageService;
import org.acme.service.RecipeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@Path("/recipe")
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    private RecipeService recipeService;
    
    @Inject
    private RecipeMapper recipeMapper;

    @Inject
    private ImageService imageService;

    @Operation(summary = "Busca todas as receitas")
    @APIResponse(responseCode = "200", //
            description = "Busca todas as receitas", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @GET
    public Response getAll(@QueryParam("page") Integer page,
                           @QueryParam("pageSize") Integer pageSize) {

        List<Recipe> recipe = recipeService.getAll(page, pageSize);
        return Response.ok(recipe).build();
    }

    @Operation(summary = "Busca receitas por categoria")
    @APIResponse(responseCode = "200", //
            description = "Busca receitas por categoria", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/category/{categoryId}")
    public Response getRecipeByCategory(@QueryParam("page") Integer page,
                           @QueryParam("pageSize") Integer pageSize, @PathParam Long categoryId) {

        List<Recipe> recipe = recipeService.getByCategory(page, pageSize, categoryId);
        return Response.ok(recipe).build();
    }

    @Operation(summary = "Busca receitas por usuário")
    @APIResponse(responseCode = "200", //
            description = "Busca receitas por usuário", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/user/{userId}")
    public Response getRecipeByUser(@QueryParam("page") Integer page,
                                        @QueryParam("pageSize") Integer pageSize, @PathParam Long userId) {

        List<Recipe> recipe = recipeService.getByUser(page, pageSize, userId);
        return Response.ok(recipe).build();
    }

    @Operation(summary = "cadastro de receita")
    @APIResponse(responseCode = "200", //
            description = "cadastro de receita", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @POST
    @Transactional
    //@RolesAllowed("user")
    public Response create(@Valid RecipeRequest obj) {
        Recipe recipeDto = recipeMapper.toResource(obj);
        Recipe recipe = recipeService.create(recipeDto);
        return Response.created(URI.create("/category/" + recipe.id)).build();
    }

    @Operation(summary = "Adciona a imagem da receita")
    @APIResponse(responseCode = "200", //
            description = "Adciona uma imagem de perfil", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON))
    @PATCH
    @Path("/uploadImage/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response uploadImage(@MultipartForm MultipartFormDataInput input, @PathParam Long id) {
        Recipe recipe = recipeService.uploadImage(input, id);
        return Response.ok().build();
    }

    @Operation(summary = "Busca a receita pelo id")
    @APIResponse(responseCode = "200", //
            description = "Busca a receita pelo id", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/{id}")
    //@RolesAllowed("user")
    public Response getById(@PathParam Long id) {
        Recipe recipe = recipeService.getById(id);
        return Response.ok(recipe).build();
    }

    @Operation(summary = "Atualiza uma receita")
    @APIResponse(responseCode = "200", //
            description = "Atualiza uma receita", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Recipe.class, //
                            type = SchemaType.ARRAY)))
    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response update(RecipeRequest obj, @PathParam Long id) {
        Recipe recipe = recipeService.update(obj, id);
        return Response.ok(recipeMapper.toResource(recipe)).build();
    }

    @Operation(summary = "Adciona uma imagem de perfil")
    @APIResponse(responseCode = "200", //
            description = "Adciona uma imagem de perfil", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON))
    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(@PathParam Long id) {
        recipeService.delete(id);
        return Response.noContent().build();
    }
}