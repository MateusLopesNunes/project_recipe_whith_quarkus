package org.acme.service;

import org.acme.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category create(Category category);

    Category getById(Long id);

    Category update(Category obj, Long id);

    void delete(Long id);
}
