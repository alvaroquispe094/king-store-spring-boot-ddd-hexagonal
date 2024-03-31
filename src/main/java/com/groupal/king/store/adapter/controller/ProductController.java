package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.out.ProductResponse;
import com.groupal.king.store.adapter.controller.model.in.ProductRequest;
import com.groupal.king.store.application.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    @Operation(summary = "Find all", description = "Request to find all products")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    public List<ProductResponse> getAllProducts() {
        log.info(">>Execute controller");

        var response = ProductResponse.listFromDomain(getProductsQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    @Operation(summary = "find by id", description = "Request to find a product by id")
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ProductResponse.class)) })
    public ProductResponse getProductById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var product = getProductByIdQuery.execute(id);
        var response = ProductResponse.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    @Operation(summary = "Create", description = "Request to create a new product")
    @Parameter(name = "Authorization", description = "Token de autorización", in = ParameterIn.HEADER, required = true)
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ProductResponse.class)) })
    public void createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info(">> Execute controller with body: {}", productRequest);

        var product = productCommand.execute(productRequest.toDomain());
        var response = ProductResponse.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Request to update a existing product")
    @Parameter(name = "Authorization", description = "Token de autorización", in = ParameterIn.HEADER, required = true)
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ProductResponse.class)) })
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        log.info(">> Execute controller with body: {}", productRequest);

        var product = updateProductCommand.execute(productRequest.toDomain(), id);
        var response = ProductResponse.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

}
