package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.out.CategoryRepository;
import com.groupal.king.store.application.port.in.UpdateCategoryCommand;
import com.groupal.king.store.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryUpdateUseCase implements UpdateCategoryCommand {

    private final CategoryRepository categoryRepository;

    @Override
    public Category execute(Command command, Long id) {
        log.info(">> Execute use case update category with request domain: {}", command.toDomain());

        var response = categoryRepository.updateCategory(command.toDomain(id));

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


