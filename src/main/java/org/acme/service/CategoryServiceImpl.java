package org.acme.service;

import org.acme.models.Category;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService{

    @Inject
    private ImageService imageService;

    @Override
    public List<Category> getAll() {
        return Category.listAll();
    }

    @Override
    public Category create(Category category) {
        category.persist();
        return category;
    }

    @Override
    public Category getById(Long id) {
        Optional<Category> category = Category.findByIdOptional(id);
        if (category.isEmpty()) throw new NotFoundException("Category not empty");

        return category.get();
    }

    @Override
    public Category update(Category obj, Long id) {
        Optional<Category> category = Category.findByIdOptional(id);
        if (category.isEmpty()) throw new NotFoundException("Category not empty");

        category.get().name = obj.name;
        return category.get();
    }

    @Override
    public Category addImage(MultipartFormDataInput input, Long id) {
        Optional<Category> categoryOpt = Category.findByIdOptional(id);
        if (categoryOpt.isEmpty()) throw new NotFoundException("Category not empty");

        Category category = categoryOpt.get();
        category.image = imageService.uploadFile(input, "/images/category")
                                     .stream().findFirst().get();

        return category;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = Category.findByIdOptional(id);
        if (category.isEmpty()) throw new NotFoundException("Category not empty");

        category.get().delete();
    }
}
