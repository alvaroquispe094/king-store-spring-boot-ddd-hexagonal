package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.CategoryCommand;
import com.groupal.king.store.application.port.out.CategoryRepository;
import com.groupal.king.store.catalog.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryCreateUseCase implements CategoryCommand {

    private final CategoryRepository categoryRepository;

    @Override
    public Category execute(Command command) {
        log.info(">> Execute use case create product with request domain: {}", command.toDomain());

        var response = categoryRepository.createCategory(command.toDomain());

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


