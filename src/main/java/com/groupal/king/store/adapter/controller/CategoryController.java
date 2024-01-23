package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.CategoryRest;
import com.groupal.king.store.application.port.in.CategoryCommand;
import com.groupal.king.store.application.port.in.GetCategoriesQuery;
import com.groupal.king.store.application.port.in.GetCategoryByIdQuery;
import com.groupal.king.store.application.port.in.UpdateCategoryCommand;
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
    private final GetCategoryByIdQuery getCategoryByIdQuery;
    private final UpdateCategoryCommand updateCategoryCommand;

    @GetMapping
    public List<CategoryRest> getAllCategories() {
        log.info(">>Execute controller");

        var response = CategoryRest.listFromDomain(getCategoriesQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    public CategoryRest getCategoryById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var category = getCategoryByIdQuery.execute(id);
        var response = CategoryRest.fromDomain(category);

        log.info("<< Request successfully executed with response {}", response);
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

    @PutMapping("/{id}")
    public CategoryRest updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var category = updateCategoryCommand.execute(command, id);
        var response = CategoryRest.fromDomain(category);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }
}
