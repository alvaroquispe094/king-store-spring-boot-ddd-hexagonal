package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

public interface CreateProductCommand {

    Product execute(Command command);

    @Value
    @Builder
    class Command {
        @NotBlank(message = "Name mustn't be blank")
        String name;

        @NotBlank(message = "Description mustn't be blank")
        String description;

        @NotNull(message = "Price mustn't be null")
        @Min(0)
        Double price;

        String image;

        @NotNull(message = "Image mustn't be null")
        @Min(1)
        Integer stock;

        @NotNull(message = "Image mustn't be null")
        Long categoryId;

        @NotNull(message = "Active mustn't be null")
        Boolean active;


        public Product toDomain() {
            return Product.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .image(image)
                    .stock(stock)
                    .categoryId(categoryId)
                    .active(active)
                    .build();
        }
    }
}
