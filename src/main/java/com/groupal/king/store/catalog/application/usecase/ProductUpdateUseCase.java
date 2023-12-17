package com.groupal.king.store.catalog.application.usecase;

import com.groupal.king.store.catalog.application.port.in.UpdateProductCommand;
import com.groupal.king.store.catalog.application.port.out.ProductRepository;
import com.groupal.king.store.catalog.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUpdateUseCase implements UpdateProductCommand {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Command command, Long id) {
        log.info(">> Execute use case update product with request domain: {}", command.toDomain());

        var response = productRepository.updateProduct(command.toDomain(id));

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


