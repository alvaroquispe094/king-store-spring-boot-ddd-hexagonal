package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.GetProductByIdQuery;
import com.groupal.king.store.application.port.out.ProductRepository;
import com.groupal.king.store.catalog.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetProductByIdUseCase implements GetProductByIdQuery {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Long id) {
        log.info(">> Execute get product use case");

        Product result = productRepository.findById(id);

        log.info("<< Get product data successfully processed with response {}", result);
        return result;
    }
}
