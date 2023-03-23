package org.acme.resources;

import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.common.annotation.Blocking;
import org.acme.dto.request.AuthRequest;
import org.acme.dto.request.UserRequest;
import org.acme.dto.request.UserUpdateRequest;
import org.acme.mapper.UserMapper;
import org.acme.models.User;
import org.acme.service.ImageService;
import org.acme.service.UserService;
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

    @POST
    @Transactional
    public Response create(@Valid UserRequest obj) {
        User userDto = userMapper.toResource(obj);
        User user = userService.create(userDto);
        return Response.created(URI.create("/category/" + user.id)).build();
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed("user")
    public Response getById(@PathParam Long id) {
        User user = userService.getById(id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    //@RolesAllowed("user")
    public Response update(UserUpdateRequest obj, @PathParam Long id) {
        User user = userService.update(userMapper.toResource(obj), id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    public Response delete(@PathParam Long id) {
        userService.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/auth")
    @Transactional
    public Response login(AuthRequest auth) {
        String login = userService.login(auth);
        return Response.ok(login).build();
    }

    @GET
    @Path("/resetPassword/{email}")
    @Blocking
    @Transactional
    public CompletionStage<Response> resetPassword(@PathParam String email) {
        return userService.resetPassword(email);
    }

    @PATCH()
    @Path("/addPerfilImage/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User addPerfilImage(@MultipartForm MultipartFormDataInput input, @PathParam Long id) {
        return userService.addPerfilImage(input, id);
    }

    //--------------------------------------------------------------------------

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<String> uploadFile(@MultipartForm MultipartFormDataInput input) {
        return imageService.uploadFile(input, "/upload/images");
    }
}
