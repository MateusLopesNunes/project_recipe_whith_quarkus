package org.acme.resources;

import org.acme.dto.request.CategoryRequest;
import org.acme.dto.request.ImagePath;
import org.acme.dto.response.CategoryResponse;
import org.acme.mapper.CategoryMapper;
import org.acme.models.Category;
import org.acme.models.User;
import org.acme.service.CategoryService;
import org.acme.service.ImageService;
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
import java.io.*;
import java.net.URI;
import java.util.List;

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    private CategoryService categoryService;
    
    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private ImageService imageService;

    @Operation(summary = "Busca todas as categorias")
    @APIResponse(responseCode = "200", //
            description = "Retorna todas as categorias cadastrados", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = CategoryResponse.class, //
                            type = SchemaType.ARRAY)))
    @GET
    public Response getAll() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponse> categoriesDto = categoryMapper.toResourceList(categories);
        return Response.ok(categoriesDto).build();
    }

    @Operation(summary = "Cadastra uma categoria")
    @APIResponse(responseCode = "201", //
            description = "Retorna todas as categorias cadastradas", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Category.class, //
                            type = SchemaType.ARRAY
                    )))
    @POST
    @Transactional
    //@RolesAllowed("user")
    public Response create(@Valid CategoryRequest obj) {
        Category categoryDto = categoryMapper.toResource(obj);
        Category category = categoryService.create(categoryDto);
        return Response.created(URI.create("/category/" + category.id)).build();
    }

    @Operation(summary = "Busca uma category")
    @APIResponse(responseCode = "200", //
            description = "Retorna uma categoria por id", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = CategoryResponse.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/{id}")
    //@RolesAllowed("user")
    public Response getById(@PathParam Long id) {
        Category category = categoryService.getById(id);
        return Response.ok(categoryMapper.toResource(category)).build();
    }

    @Operation(summary = "Atualiza uma categoria")
    @APIResponse(responseCode = "200", //
            description = "Atualiza a categoria", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Category.class, //
                            type = SchemaType.ARRAY)))
    @PUT
    @Path("/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response update(CategoryRequest obj, @PathParam Long id) {
        Category category = categoryService.update(categoryMapper.toResource(obj), id);
        return Response.ok(categoryMapper.toResource(category)).build();
    }

    @Operation(summary = "Faz upload da imagem da categoria")
    @APIResponse(responseCode = "200", //
            description = "Faz upload da imagem da categoria", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = Category.class, //
                            type = SchemaType.ARRAY)))
    @PATCH
    @Path("/addImage/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addImage(@MultipartForm MultipartFormDataInput input, @PathParam Long id) {
        Category category = categoryService.addImage(input, id);
        return Response.ok(categoryMapper.toResource(category)).build();
    }

    @Operation(summary = "Deleta uma categoria")
    @APIResponse(responseCode = "200", //
            description = "Deleta uma categoria pelo id"//
    )
    @DELETE
    @Path("/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response delete(@PathParam Long id) {
        categoryService.delete(id);
        return Response.noContent().build();
    }

    @Operation(summary = "Busca uma imagem")
    @APIResponse(responseCode = "200", //
            description = "Busca uma imagem no servidor", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = ImagePath.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/image/{path}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage(@PathParam String path) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream("/images/category/" + path);
        if (imageStream != null) {
            return Response.ok(imageStream).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}