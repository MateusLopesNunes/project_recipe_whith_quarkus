package org.acme.service;

import org.acme.dto.request.RecipeRequest;
import org.acme.models.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAll();

    Recipe create(Recipe recipe);

    Recipe getById(Long id);

    Recipe update(RecipeRequest obj, Long id);

    void delete(Long id);
}
