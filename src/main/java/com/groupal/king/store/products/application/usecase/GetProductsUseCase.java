package com.groupal.king.store.products.application.usecase;

import com.groupal.king.store.products.application.port.in.GetProductsQuery;
import com.groupal.king.store.products.application.port.out.ProductRepository;
import com.groupal.king.store.products.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetProductsUseCase implements GetProductsQuery {

    private final ProductRepository productRepository;

    @Override
    public List<Product> execute() {
        log.info(">> Execute get banks use case");

        List<Product> results = productRepository.findAll();

        log.info("<< Get banks data successfully processed with response {}", results);
        return results;
    }
}
