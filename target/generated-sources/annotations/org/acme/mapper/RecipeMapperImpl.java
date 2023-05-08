package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.acme.dto.response.RecipeResponse;
import org.acme.models.Recipe;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-08T17:36:42-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@ApplicationScoped
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeResponse toResource(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeResponse recipeResponse = new RecipeResponse();

        recipeResponse.setId( recipe.getId() );
        recipeResponse.setTitle( recipe.getTitle() );
        recipeResponse.setNumberOfPortion( recipe.getNumberOfPortion() );
        recipeResponse.setPreparationMethod( recipe.getPreparationMethod() );
        recipeResponse.setPreparationTime( recipe.getPreparationTime() );
        recipeResponse.setCreatedAt( recipe.getCreatedAt() );
        recipeResponse.setUpdatedAt( recipe.getUpdatedAt() );
        recipeResponse.setImage( recipe.getImage() );
        recipeResponse.setAuthor( recipe.getAuthor() );
        recipeResponse.setCategory( recipe.getCategory() );

        return recipeResponse;
    }

    @Override
    public List<RecipeResponse> toResourceList(List<Recipe> recipe) {
        if ( recipe == null ) {
            return null;
        }

        List<RecipeResponse> list = new ArrayList<RecipeResponse>( recipe.size() );
        for ( Recipe recipe1 : recipe ) {
            list.add( toResource( recipe1 ) );
        }

        return list;
    }
}
