package com.groupal.king.store.catalog.adapter.database.repository;

import com.groupal.king.store.catalog.adapter.database.model.ProductModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDataRepository extends CrudRepository<ProductModel, Long> {
    @Override
    List<ProductModel> findAll();
}
