package org.acme.mapper;

import org.acme.dto.request.CategoryRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.models.Category;
import org.mapstruct.Mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Mapper(componentModel = "cdi")
public interface CategoryMapper {

    Category toResource(CategoryRequest category);

    CategoryResponse toResource(Category category);

    List<CategoryResponse> toResourceList(List<Category> category);

}
