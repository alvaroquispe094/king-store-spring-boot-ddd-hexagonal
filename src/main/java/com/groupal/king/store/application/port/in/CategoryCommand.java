package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

public interface CategoryCommand {

    Category execute(Command command);

    @Value
    @Builder
    class Command {
        Long id;

        @NotBlank(message = "Name mustn't be blank")
        String name;

        @NotNull(message = "Active mustn't be null")
        Boolean active;


        public Category toDomain() {
            return Category.builder()
                    .id(id)
                    .name(name)
                    .active(active)
                    .build();
        }
    }
}
