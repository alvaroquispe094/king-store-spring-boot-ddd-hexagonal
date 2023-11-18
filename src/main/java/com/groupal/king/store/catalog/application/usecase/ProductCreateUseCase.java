package com.groupal.king.store.catalog.application.usecase;

import com.groupal.king.store.catalog.application.port.in.ProductCommand;
import com.groupal.king.store.catalog.application.port.out.ProductRepository;
import com.groupal.king.store.catalog.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductCreateUseCase implements ProductCommand {

    private ProductRepository productRepository;

    @Override
    public Product execute(Command command) {
        log.info(">> Execute use case create product with request domain: {}", command.toDomain());

        var response = productRepository.createProduct(command.toDomain());

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


