package com.groupal.king.store.catalog.adapter.controller;

import com.groupal.king.store.catalog.adapter.controller.model.CategoryRest;
import com.groupal.king.store.catalog.application.port.in.CategoryCommand;
import com.groupal.king.store.catalog.application.port.in.GetCategoriesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog/categories")
public class CategoryController {

    private final CategoryCommand categoryCommand;
    private final GetCategoriesQuery getCategoriesQuery;

    @GetMapping
    public List<CategoryRest> getAllCategories() {
        log.info(">>Execute controller");

        var response = CategoryRest.listFromDomain(getCategoriesQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }

    @PostMapping("")
    public CategoryRest createCategory(@RequestBody CategoryCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = categoryCommand.execute(command);
        var response = CategoryRest.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }
}
