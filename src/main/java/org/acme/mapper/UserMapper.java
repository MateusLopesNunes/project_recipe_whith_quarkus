package org.acme.mapper;

import org.acme.dto.request.UserRequest;
import org.acme.dto.request.UserUpdateRequest;
import org.acme.dto.response.UserResponse;
import org.acme.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(target = "perfilImage", ignore = true)
    User toResource(UserRequest user);

    @Mapping(target = "perfilImage", ignore = true)
    User toResource(UserUpdateRequest user);

    UserResponse toResource(User user);

    List<UserResponse> toResourceList(List<User> user);

}
