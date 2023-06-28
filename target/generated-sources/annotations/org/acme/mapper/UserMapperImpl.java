package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.acme.dto.request.UserRequest;
import org.acme.dto.request.UserUpdateRequest;
import org.acme.dto.response.UserResponse;
import org.acme.models.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-27T21:47:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@ApplicationScoped
public class UserMapperImpl implements UserMapper {

    @Override
    public User toResource(UserRequest user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setUserName( user.getUserName() );
        user1.setEmail( user.getEmail() );
        user1.setPassword( user.getPassword() );
        user1.setBirthDate( user.getBirthDate() );
        user1.setTel( user.getTel() );

        return user1;
    }

    @Override
    public User toResource(UserUpdateRequest user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setUserName( user.getUserName() );
        user1.setEmail( user.getEmail() );
        user1.setPassword( user.getPassword() );
        user1.setBirthDate( user.getBirthDate() );
        user1.setTel( user.getTel() );
        user1.id = user.getId();

        return user1;
    }

    @Override
    public UserResponse toResource(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.id );
        userResponse.setUserName( user.getUserName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setBirthDate( user.getBirthDate() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setUpdatedAt( user.getUpdatedAt() );
        userResponse.setTel( user.getTel() );
        userResponse.setPerfilImage( user.getPerfilImage() );

        return userResponse;
    }

    @Override
    public List<UserResponse> toResourceList(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( user.size() );
        for ( User user1 : user ) {
            list.add( toResource( user1 ) );
        }

        return list;
    }
}
