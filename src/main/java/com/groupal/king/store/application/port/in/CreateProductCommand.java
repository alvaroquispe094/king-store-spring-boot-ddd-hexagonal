package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Product;

public interface CreateProductCommand {
    Product execute(Product product);
}
