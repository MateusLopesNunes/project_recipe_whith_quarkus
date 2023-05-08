package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;
import org.acme.dto.request.CategoryRequest;
import org.acme.dto.response.CategoryResponse;
import org.acme.models.Category;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-08T17:36:42-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@ApplicationScoped
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toResource(CategoryRequest category) {
        if ( category == null ) {
            return null;
        }

        Category category1 = new Category();

        category1.setName( category.getName() );

        return category1;
    }

    @Override
    public CategoryResponse toResource(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        if ( category.getId() != null ) {
            categoryResponse.setId( String.valueOf( category.getId() ) );
        }
        categoryResponse.setName( category.getName() );
        categoryResponse.setImage( category.getImage() );

        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> toResourceList(List<Category> category) {
        if ( category == null ) {
            return null;
        }

        List<CategoryResponse> list = new ArrayList<CategoryResponse>( category.size() );
        for ( Category category1 : category ) {
            list.add( toResource( category1 ) );
        }

        return list;
    }
}
