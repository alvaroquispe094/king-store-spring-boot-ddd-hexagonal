package com.groupal.king.store.adapter.database;

import com.groupal.king.store.adapter.database.model.ProductModel;
import com.groupal.king.store.adapter.database.repository.ProductDataRepository;
import com.groupal.king.store.application.exception.NotFoundException;
import com.groupal.king.store.application.port.out.ProductRepository;
import com.groupal.king.store.catalog.domain.Product;
import com.groupal.king.store.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDbAdapter implements ProductRepository {

    private final ProductDataRepository repository;

    @Override
    public List<Product> findAll() {
        log.info(">> Get all products from DB");

        var results = repository.findAll()
                .stream()
                .map(ProductModel::toDomain)
                .collect(Collectors.toList());

        log.info("<< Get all products with response: {}", results);
        return results;
    }

    @Override
    public Product createProduct(Product product) {
        log.info(">> Create product with: {}", product);

        ProductModel productModel = ProductModel.fromDomain(product);
        var response = repository.save(productModel);

        log.info("Saving in db a product with response {}", response);
        return response.toDomain();
    }

    @Override
    public Product findById(Long id) {
        log.info(">> find product by id = {}", id);

        var result = this.repository.findById(id)
                .map(ProductModel::toDomain)
                .orElseThrow( () -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION));

        log.info("<< Find product with response: {}", result);
        return result;
    }

    @Override
    public Product updateProduct(Product product) {
        log.info(">> Update product with: {}", product);

        ProductModel productModel = ProductModel.fromDomain(product);
        var response = repository.save(productModel);

        log.info("Update in db a product with response {}", response);
        return response.toDomain();
    }
}
