package com.groupal.king.store.catalog.adapter.controller;

import com.groupal.king.store.catalog.adapter.controller.model.ProductRest;
import com.groupal.king.store.catalog.application.port.in.GetProductByIdQuery;
import com.groupal.king.store.catalog.application.port.in.GetProductsQuery;
import com.groupal.king.store.catalog.application.port.in.ProductCommand;
import com.groupal.king.store.catalog.application.port.in.UpdateProductCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/products")
public class ProductController {

    private final ProductCommand productCommand;
    private final UpdateProductCommand updateProductCommand;
    private final GetProductsQuery getProductsQuery;
    private final GetProductByIdQuery getProductByIdQuery;

    @GetMapping
    public List<ProductRest> getAllProducts() {
        log.info(">>Execute controller");

        var response = ProductRest.listFromDomain(getProductsQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    public ProductRest getProductById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var product = getProductByIdQuery.execute(id);
        var response = ProductRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    public ProductRest createProduct(@RequestBody ProductCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = productCommand.execute(command);
        var response = ProductRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public ProductRest updateProduct(@PathVariable Long id, @RequestBody UpdateProductCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = updateProductCommand.execute(command, id);
        var response = ProductRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }
    /*
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        this.bookService.deleteBook(id);

    }
    */

}
