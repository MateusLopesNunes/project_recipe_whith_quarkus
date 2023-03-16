package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.dto.request.AuthRequest;
import org.acme.models.Category;
import org.acme.models.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface UserService {

    List<User> getAll();

    User create(User category);

    User getById(Long id);

    public User getByName(String name);

    User update(User obj, Long id);

    void delete(Long id);

    String login(AuthRequest auth);

    public CompletionStage resetPassword(String email);
}
