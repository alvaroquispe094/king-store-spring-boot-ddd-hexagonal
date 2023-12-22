package com.groupal.king.store.catalog.application.port.in;

import com.groupal.king.store.catalog.domain.Category;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public interface CategoryCommand {

    Category execute(Command command);

    @Value
    @Builder
    class Command {
        Long id;
        //@NotBlank(message = "Code mustn't be blank")
        String code;
        //@NotBlank(message = "Name mustn't be blank")
        String name;


        public Category toDomain() {
            return Category.builder()
                    .id(id)
                    .code(UUID.randomUUID().toString())
                    .name(name)
                    .active(Boolean.TRUE)
                    .build();
        }
    }
}
