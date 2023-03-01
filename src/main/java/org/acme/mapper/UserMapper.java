package org.acme.mapper;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.dto.request.CategoryRequest;
import org.acme.dto.request.UserRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.dto.response.UserResponse;
import org.acme.models.Category;
import org.acme.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    User toResource(UserRequest user);

    UserResponse toResource(User user);

    List<UserResponse> toResourceList(List<User> user);

}
