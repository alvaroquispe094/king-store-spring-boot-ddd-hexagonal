package com.groupal.king.store.catalog.application.usecase;

import com.groupal.king.store.catalog.application.port.in.CategoryCommand;
import com.groupal.king.store.catalog.application.port.out.CategoryRepository;
import com.groupal.king.store.catalog.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryCreateUseCase implements CategoryCommand {

    private CategoryRepository categoryRepository;

    @Override
    public Category execute(Command command) {
        log.info(">> Execute use case create product with request domain: {}", command.toDomain());

        var response = categoryRepository.createCategory(command.toDomain());

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


