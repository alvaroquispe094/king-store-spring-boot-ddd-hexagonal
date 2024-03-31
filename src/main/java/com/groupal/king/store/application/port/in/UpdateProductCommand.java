package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Product;

public interface UpdateProductCommand {
    Product execute(Product product, Long id);
}
