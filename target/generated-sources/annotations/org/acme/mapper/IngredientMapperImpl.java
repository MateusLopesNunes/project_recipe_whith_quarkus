package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.acme.dto.response.IngredientResponse;
import org.acme.models.Ingredient;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-08T17:36:41-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@ApplicationScoped
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientResponse toResource(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientResponse ingredientResponse = new IngredientResponse();

        ingredientResponse.setName( ingredient.getName() );
        ingredientResponse.setRecipe( ingredient.getRecipe() );

        return ingredientResponse;
    }

    @Override
    public List<IngredientResponse> toResourceList(List<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientResponse> list = new ArrayList<IngredientResponse>( ingredients.size() );
        for ( Ingredient ingredient : ingredients ) {
            list.add( toResource( ingredient ) );
        }

        return list;
    }
}
