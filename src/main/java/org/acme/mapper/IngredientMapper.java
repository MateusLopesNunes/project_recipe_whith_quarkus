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
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper( IngredientMapper.class );

    List<Ingredient> toResource(List<IngredientRequest> ingredientReq);

    IngredientResponse toResource(Ingredient ingredient);

    List<IngredientResponse> toResourceList(List<Ingredient> ingredients);

}
