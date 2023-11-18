package com.groupal.king.store.catalog.application.usecase;

import com.groupal.king.store.catalog.application.port.in.GetCategoriesQuery;
import com.groupal.king.store.catalog.application.port.out.CategoryRepository;
import com.groupal.king.store.catalog.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCategoriesUseCase implements GetCategoriesQuery {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> execute() {
        log.info(">> Execute get categories use case");

        List<Category> results = categoryRepository.findAll();

        log.info("<< Get categories data successfully processed with response {}", results);
        return results;
    }
}
