package org.acme.mapper;

import org.acme.dto.request.IngredientRequest;
import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.IngredientResponse;
import org.acme.dto.response.RecipeResponse;
import org.acme.models.Category;
import org.acme.models.Ingredient;
import org.acme.models.Recipe;
import org.acme.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface IngredientMapper {

    default Ingredient toResource(IngredientRequest ingredientReq) {
        Ingredient ingredient = new Ingredient();
//        recipe.title = recipeReq.getTitle();
//        recipe.numberOfPortion = recipeReq.getNumberOfPortion();
//        recipe.preparationMethod = recipeReq.getPreparationMethod();
//        recipe.preparationTime = recipeReq.getPreparationTime();
//
//        recipe.author = User.findById(recipeReq.getAuthor());
//        recipe.category = Category.findById(recipeReq.getCategory());

        return ingredient;
    }

    IngredientResponse toResource(Ingredient ingredient);

    List<IngredientResponse> toResourceList(List<Ingredient> ingredients);

}
