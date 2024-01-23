package com.groupal.king.store.adapter.database.repository;

import com.groupal.king.store.adapter.database.model.CategoryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryDataRepository extends CrudRepository<CategoryModel, Long> {
    @Override
    List<CategoryModel> findAll();
}
