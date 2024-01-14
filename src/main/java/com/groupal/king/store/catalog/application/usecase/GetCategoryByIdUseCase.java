package com.groupal.king.store.catalog.application.usecase;

import com.groupal.king.store.catalog.application.port.in.GetCategoryByIdQuery;
import com.groupal.king.store.catalog.application.port.out.CategoryRepository;
import com.groupal.king.store.catalog.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCategoryByIdUseCase implements GetCategoryByIdQuery {

    private final CategoryRepository categoryRepository;

    @Override
    public Category execute(Long id) {
        log.info(">> Execute get category use case");

        Category result = categoryRepository.findById(id);

        log.info("<< Get category data successfully processed with response {}", result);
        return result;
    }
}
