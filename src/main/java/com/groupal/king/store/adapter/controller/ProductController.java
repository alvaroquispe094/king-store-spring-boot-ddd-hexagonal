package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.ProductRest;
import com.groupal.king.store.application.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Products", description = "Products API Controller")
@RequestMapping("/api/v1/catalog/products")
public class ProductController {

    private final CreateProductCommand productCommand;
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
    @Operation(summary = "Create", description = "Request to create a new product")
    @Parameter(name = "Authorization", description = "Token de autorización", in = ParameterIn.HEADER, required = true)
    /*@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema= @Schema())
    )*/
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ProductRest.class)) })
    public ProductRest createProduct(@Valid @RequestBody CreateProductCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = productCommand.execute(command);
        var response = ProductRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public ProductRest updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductCommand.Command command) {
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
