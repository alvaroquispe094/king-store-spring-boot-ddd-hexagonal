package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.CreateProductCommand;
import com.groupal.king.store.application.port.out.ProductRepository;
import com.groupal.king.store.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreateUseCase implements CreateProductCommand {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Product product) {
        log.info(">> Execute use case create product with request domain: {}", product);

        var response = productRepository.createProduct(product);

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


