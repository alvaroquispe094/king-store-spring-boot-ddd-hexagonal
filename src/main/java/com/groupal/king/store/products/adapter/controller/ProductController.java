package com.groupal.king.store.products.adapter.controller;

import com.groupal.king.store.products.adapter.controller.model.ProductRest;
import com.groupal.king.store.products.application.port.in.GetProductsQuery;
import com.groupal.king.store.products.application.port.in.ProductCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductCommand productCommand;
    private final GetProductsQuery getProductsQuery;

    @GetMapping
    public List<ProductRest> getAllProducts() {
        log.info(">>Execute controller");

        var response = ProductRest.listFromDomain(getProductsQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }
    /*
        @GetMapping("/{id}")
        public ProductRest getProductById(@PathVariable Integer id) {
            return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
        }
    */
    @PostMapping("")
    public ProductRest createBook(@RequestBody ProductCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = productCommand.execute(command);
        var response = ProductRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }
/*
    @PutMapping("/{id}")
    public ProductRest updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        return ResponseEntity.ok().body(this.bookService.updateBook(bookDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        this.bookService.deleteBook(id);

    }
    */

}
