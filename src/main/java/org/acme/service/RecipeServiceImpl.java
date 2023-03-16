package org.acme.service;

import org.acme.models.Category;
import org.acme.models.Recipe;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecipeServiceImpl implements RecipeService{

    @Override
    public List<Recipe> getAll() {
        return Recipe.listAll();
    }

    @Override
    public Recipe create(Recipe recipe) {
        recipe.persist();
        return recipe;
    }

    @Override
    public Recipe getById(Long id) {
        Optional<Recipe> recipe = Recipe.findByIdOptional(id);
        if (recipe.isEmpty()) throw new NotFoundException("Category not empty");

        return recipe.get();
    }

    @Override
    public Recipe update(Recipe obj, Long id) {
        Optional<Recipe> recipe = Recipe.findByIdOptional(id);
        if (recipe.isEmpty()) throw new NotFoundException("Category not empty");

        //category.get().name = obj.name;
        return recipe.get();
    }

    @Override
    public void delete(Long id) {
        Optional<Category> recipe = Recipe.findByIdOptional(id);
        if (recipe.isEmpty()) throw new NotFoundException("Category not empty");

        recipe.get().delete();
    }
}
