package org.acme.resources;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.dto.request.AuthRequest;
import org.acme.dto.request.CategoryRequest;
import org.acme.dto.request.UserRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.dto.response.UserResponse;
import org.acme.mapper.UserMapper;
import org.acme.models.Category;
import org.acme.models.User;
import org.acme.service.UserService;
import org.jboss.logging.annotations.Pos;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    private UserMapper userMapper;

    @GET
//    @RolesAllowed("user")
    public Response getAll() {
        List<User> users = userService.getAll();
        return Response.ok(userMapper.toResourceList(users)).build();
    }

    @GET
    @Path("search/{name}")
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
    public Response getById(Long id) {
        User user = userService.getById(id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(UserRequest obj, Long id) {
        User user = userService.update(userMapper.toResource(obj), id);
        return Response.ok(userMapper.toResource(user)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(Long id) {
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
}
