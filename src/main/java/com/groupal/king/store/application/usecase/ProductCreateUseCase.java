package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.ProductCommand;
import com.groupal.king.store.application.port.out.ProductRepository;
import com.groupal.king.store.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreateUseCase implements ProductCommand {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Command command) {
        log.info(">> Execute use case create product with request domain: {}", command.toDomain());

        var response = productRepository.createProduct(command.toDomain());

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


