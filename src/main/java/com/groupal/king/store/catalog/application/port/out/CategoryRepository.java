package com.groupal.king.store.catalog.application.port.out;

import com.groupal.king.store.catalog.domain.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category createCategory(Category category);

}
