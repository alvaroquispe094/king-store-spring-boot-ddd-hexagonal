package com.groupal.king.store.application.port.out;

import com.groupal.king.store.catalog.domain.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product createProduct(Product product);

    Product findById(Long id);

    Product updateProduct(Product product);

}
