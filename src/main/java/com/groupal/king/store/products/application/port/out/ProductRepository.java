package com.groupal.king.store.products.application.port.out;

import com.groupal.king.store.products.domain.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product createProduct(Product product);

}
