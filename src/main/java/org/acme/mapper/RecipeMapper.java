package org.acme.mapper;

import org.acme.dto.request.CategoryRequest;
import org.acme.dto.request.RecipeRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.dto.response.RecipeResponse;
import org.acme.models.Category;
import org.acme.models.Recipe;
import org.mapstruct.Mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Mapper(componentModel = "cdi")
public interface RecipeMapper {

    Recipe toResource(RecipeRequest recipe);

    RecipeResponse toResource(Recipe recipe);

    List<RecipeResponse> toResourceList(List<Recipe> recipe);

}
