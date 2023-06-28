package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.acme.dto.request.IngredientRequest;
import org.acme.dto.response.IngredientResponse;
import org.acme.models.Ingredient;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-27T21:47:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@ApplicationScoped
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public List<Ingredient> toResource(List<IngredientRequest> ingredientReq) {
        if ( ingredientReq == null ) {
            return null;
        }

        List<Ingredient> list = new ArrayList<Ingredient>( ingredientReq.size() );
        for ( IngredientRequest ingredientRequest : ingredientReq ) {
            list.add( ingredientRequestToIngredient( ingredientRequest ) );
        }

        return list;
    }

    @Override
    public IngredientResponse toResource(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientResponse ingredientResponse = new IngredientResponse();

        ingredientResponse.setName( ingredient.getName() );

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

    protected Ingredient ingredientRequestToIngredient(IngredientRequest ingredientRequest) {
        if ( ingredientRequest == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setName( ingredientRequest.getName() );

        return ingredient;
    }
}
