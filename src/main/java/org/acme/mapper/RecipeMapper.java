package org.acme.mapper;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.RecipeResponse;
import org.acme.models.Category;
import org.acme.models.Recipe;
import org.acme.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RecipeMapper {

    default Recipe toResource(RecipeRequest recipeReq) {
        Recipe recipe = new Recipe();
        recipe.title = recipeReq.getTitle();
        recipe.image = recipeReq.getImage();
        recipe.description = recipeReq.getDescription();
        recipe.numberOfPortion = recipeReq.getNumberOfPortion();
        recipe.preparationMethod = recipeReq.getPreparationMethod();

        recipe.author = User.findById(recipeReq.getAuthor());
        recipe.category = Category.findById(recipeReq.getCategory());

        return recipe;
    };

    RecipeResponse toResource(Recipe recipe);

    List<RecipeResponse> toResourceList(List<Recipe> recipe);

}
