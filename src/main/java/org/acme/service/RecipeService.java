package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.dto.request.RecipeRequest;
import org.acme.models.Recipe;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAll(Integer page, Integer pageSize);

    List<Recipe> getByCategory(Integer page, Integer pageSize, Long categoryId);

    Recipe create(Recipe recipe);

    Recipe getById(Long id);

    Recipe update(RecipeRequest obj, Long id);

    void delete(Long id);

    List<Recipe> getByUser(Integer page, Integer pageSize, Long userId);

    Recipe uploadImage(MultipartFormDataInput input, Long id);
}
