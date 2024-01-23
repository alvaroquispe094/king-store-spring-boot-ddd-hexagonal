package com.groupal.king.store.application.port.in;

import com.groupal.king.store.catalog.domain.Product;

import java.util.List;

public interface GetProductsQuery {

    List<Product> execute();

}
