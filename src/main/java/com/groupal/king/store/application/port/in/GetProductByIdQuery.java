package com.groupal.king.store.application.port.in;

import com.groupal.king.store.catalog.domain.Product;

public interface GetProductByIdQuery {

    Product execute(Long id);

}
