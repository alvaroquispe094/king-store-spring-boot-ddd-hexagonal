package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.UpdateProductCommand;
import com.groupal.king.store.application.port.out.ProductRepository;
import com.groupal.king.store.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUpdateUseCase implements UpdateProductCommand {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Product product, Long id) {
        log.info(">> Execute use case update product with request domain: {}", product);

        var response = productRepository.updateProduct(product, id);

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


