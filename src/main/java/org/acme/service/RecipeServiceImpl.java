package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.acme.dto.request.RecipeRequest;
import org.acme.models.Category;
import org.acme.models.Recipe;
import org.acme.models.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecipeServiceImpl implements RecipeService{

    @Inject
    private ImageService imageService;

    @Override
    public List<Recipe> getAll(Integer page, Integer pageSize) {

        if (page == null && pageSize == null) {
            return Recipe.listAll();
        }

        Page pagination = Page.of(page, pageSize);
        //Sort ordem = Sort.by("nome");

        PanacheQuery<Recipe> recipes = Recipe.find("order by title");
        return recipes.page(pagination).list();
    }


    @Override
    public List<Recipe> getByCategory(Integer page, Integer pageSize, Long categoryId) {
        if (page == null && pageSize == null) {
            return Recipe.findByCategory(categoryId).stream().toList();
        }

        Page pagination = Page.of(page, pageSize);
        //Sort ordem = Sort.by("nome");

        return Recipe.findByCategory(categoryId).page(pagination).list();
    }

    @Override
    public List<Recipe> getByUser(Integer page, Integer pageSize, Long userId) {
        if (page == null && pageSize == null) {
            return Recipe.findByUser(userId).stream().toList();
        }

        Page pagination = Page.of(page, pageSize);
        //Sort ordem = Sort.by("nome");

        return Recipe.findByUser(userId).page(pagination).list();
    }

    @Override
    public Recipe uploadImage(MultipartFormDataInput input, Long id) {
        Optional<Recipe> recipeOtp = Recipe.findByIdOptional(id);
        if (recipeOtp.isEmpty()) throw new NotFoundException("Recipe not found");

        Recipe recipe = recipeOtp.get();
        recipe.image = imageService.uploadFile(input, "/upload/recipes")
                .stream().findFirst().get();

        return recipe;
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
        recipe.numberOfPortion = obj.getNumberOfPortion();
        recipe.preparationTime = obj.getPreparationTime();
        recipe.updatedAt = LocalDateTime.now();

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
