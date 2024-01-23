package com.groupal.king.store.application.port.out;



import com.groupal.king.store.domain.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category findById(Long id);
    Category createCategory(Category category);

    Category updateCategory(Category category);
}
