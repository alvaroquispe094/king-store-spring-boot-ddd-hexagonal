package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.out.CategoryResponse;
import com.groupal.king.store.application.port.in.CategoryCommand;
import com.groupal.king.store.application.port.in.GetCategoriesQuery;
import com.groupal.king.store.application.port.in.GetCategoryByIdQuery;
import com.groupal.king.store.application.port.in.UpdateCategoryCommand;
import jakarta.validation.Valid;
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
    public List<CategoryResponse> getAllCategories() {
        log.info(">>Execute controller");

        var response = CategoryResponse.listFromDomain(getCategoriesQuery.execute());

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var category = getCategoryByIdQuery.execute(id);
        var response = CategoryResponse.fromDomain(category);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var product = categoryCommand.execute(command);
        var response = CategoryResponse.fromDomain(product);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var category = updateCategoryCommand.execute(command, id);
        var response = CategoryResponse.fromDomain(category);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }
}
