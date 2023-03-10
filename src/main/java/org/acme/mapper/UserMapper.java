package org.acme.mapper;

import org.acme.dto.request.UserRequest;
import org.acme.dto.request.UserUpdateRequest;
import org.acme.dto.response.UserResponse;
import org.acme.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    User toResource(UserRequest user);

    User toResource(UserUpdateRequest user);

    UserResponse toResource(User user);

    List<UserResponse> toResourceList(List<User> user);

}
