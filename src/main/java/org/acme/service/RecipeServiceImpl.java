package org.acme.service;

import org.acme.dto.request.RecipeRequest;
import org.acme.models.Category;
import org.acme.models.Recipe;
import org.acme.models.User;

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
        if (recipe.isEmpty()) throw new NotFoundException("Recipe not found");

        return recipe.get();
    }

    @Override
    public Recipe update(RecipeRequest obj, Long id) {
        Optional<Recipe> recipeOpt = Recipe.findByIdOptional(id);
        if (recipeOpt.isEmpty()) throw new NotFoundException("Recipe not found");

        Recipe recipe = recipeOpt.get();
        recipe.title = obj.getTitle();
        recipe.preparationMethod = obj.getPreparationMethod();
        recipe.description = obj.getDescription();
        recipe.numberOfPortion = obj.getNumberOfPortion();
        recipe.image = obj.getImage();
        recipe.author = User.findById(obj.getAuthor());
        recipe.category = Category.findById(obj.getCategory());
        return recipe;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> recipe = Recipe.findByIdOptional(id);
        if (recipe.isEmpty()) throw new NotFoundException("category not found");

        recipe.get().delete();
    }
}
