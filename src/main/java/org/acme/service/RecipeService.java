package org.acme.service;

import org.acme.models.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAll();

    Recipe create(Recipe recipe);

    Recipe getById(Long id);

    Recipe update(Recipe obj, Long id);

    void delete(Long id);
}
