package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Category;
import jakarta.validation.constraints.NotBlank;
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
        @NotBlank(message = "Active mustn't be blank")
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
