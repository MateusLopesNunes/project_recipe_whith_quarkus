package org.acme.resources;

import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.common.annotation.Blocking;
import org.acme.dto.request.AuthRequest;
import org.acme.dto.request.ImagePath;
import org.acme.dto.request.UserRequest;
import org.acme.dto.request.UserUpdateRequest;
import org.acme.mapper.UserMapper;
import org.acme.models.User;
import org.acme.service.ImageService;
import org.acme.service.UserService;
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
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    private ImageService imageService;

    @Inject
    private UserMapper userMapper;

    @Inject
    private ReactiveMailer reactiveMailer;

    @GET
    //@RolesAllowed("user")
    public Response getAll() {
        List<User> users = userService.getAll();
        return Response.ok(userMapper.toResourceList(users)).build();
    }

    @GET
    @Path("search/{name}")
    @RolesAllowed("user")
    public Response getByName(String name) {
        User user = userService.getByName(name);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @Operation(summary = "Cadastro de usuário")
    @APIResponse(responseCode = "201", //
            description = "Cafastra um usuário", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = User.class, //
                            type = SchemaType.ARRAY)))
    @POST
    @Transactional
    public Response create(@Valid UserRequest obj) {
        User userDto = userMapper.toResource(obj);
        User user = userService.create(userDto);
        return Response.created(URI.create("/category/" + user.id)).build();
    }

    @Operation(summary = "Buscar usuário")
    @APIResponse(responseCode = "200", //
            description = "Buscar usuário por id", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = User.class, //
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/{id}")
    //@RolesAllowed("user")
    public Response getById(@PathParam Long id) {
        User user = userService.getById(id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @Operation(summary = "Atualização de usuário")
    @APIResponse(responseCode = "200", //
            description = "Atualização de cadastro de usuário", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = User.class, //
                            type = SchemaType.ARRAY)))
    @PUT
    @Path("/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response update(UserUpdateRequest obj, @PathParam Long id) {
        User user = userService.update(userMapper.toResource(obj), id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @Operation(summary = "Deleta um usuário")
    @APIResponse(responseCode = "200", //
            description = "Deleta um usuário exitente")
    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(@PathParam Long id) {
        userService.delete(id);
        return Response.noContent().build();
    }

    @Operation(summary = "Login")
    @APIResponse(responseCode = "200", //
            description = "Loga o usuário no sistema", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = AuthRequest.class, //
                            type = SchemaType.ARRAY)))
    @POST
    @Path("/auth")
    @Transactional
    public Response login(AuthRequest auth) {
        String login = userService.login(auth);
        return Response.ok(login).build();
    }

    @Operation(summary = "Esqueci minha senha")
    @APIResponse(responseCode = "200", //
            description = "Atualiza a senha do usuário", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/resetPassword/{email}")
    @Blocking
    @Transactional
    public CompletionStage<Response> resetPassword(@PathParam String email) {
        return userService.resetPassword(email);
    }

    @Operation(summary = "Adciona uma imagem de perfil")
    @APIResponse(responseCode = "200", //
            description = "Adciona uma imagem de perfil", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = ImagePath.class, //
                            type = SchemaType.ARRAY)))
    @PATCH
    @Path("/addPerfilImage/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User addPerfilImage(@MultipartForm MultipartFormDataInput input, @PathParam Long id) {
        return userService.addPerfilImage(input, id);
    }

    @Operation(summary = "Busca uma imagem")
    @APIResponse(responseCode = "200", //
            description = "Busca a imagem de perfil", //
            content = @Content(//
                    mediaType = MediaType.APPLICATION_JSON, //
                    schema = @Schema(//
                            implementation = ImagePath.class, //
                            type = SchemaType.ARRAY)))
    @POST
    @Path("image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"image/png", "image/jpeg", "image/jpg"})
    public Response findImage(ImagePath imagePath) {
        // Cria um objeto File que aponta para a imagem
        InputStream image = imageService.findImage(imagePath);
        return Response.ok(image).build();
    }
}
