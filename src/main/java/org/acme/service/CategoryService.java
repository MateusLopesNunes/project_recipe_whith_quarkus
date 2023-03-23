package org.acme.service;

import org.acme.models.Category;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category create(Category category);

    Category getById(Long id);

    Category update(Category obj, Long id);

    public Category addImage(MultipartFormDataInput input, Long id);

    void delete(Long id);
}
