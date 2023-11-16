package com.groupal.king.store.products.application.port.in;

import com.groupal.king.store.products.domain.Product;

import java.util.List;

public interface GetProductsQuery {

    List<Product> execute();

}
