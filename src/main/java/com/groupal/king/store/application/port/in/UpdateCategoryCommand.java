package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

public interface UpdateCategoryCommand {

    Category execute(Command command, Long id);

    @Value
    @Builder
    class Command {
        @NotBlank(message = "Name mustn't be blank")
        String name;
        Boolean active;

        public Category toDomain() {
            return Category.builder()
                    .name(name)
                    .active(active)
                    .build();
        }

        public Category toDomain(Long id) {
            return Category.builder()
                    .id(id)
                    .name(name)
                    .active(active)
                    .build();
        }
    }
}
