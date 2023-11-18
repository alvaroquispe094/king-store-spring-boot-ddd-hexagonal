package com.groupal.king.store.catalog.application.port.in;


import com.groupal.king.store.catalog.domain.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public interface ProductCommand {

    Product execute(Command command);

    @Value
    @Builder
    class Command {
        Long id;
        @NotBlank(message = "Name mustn't be blank")
        String name;
        @NotBlank(message = "Description mustn't be blank")
        String description;
        @NotBlank(message = "Price mustn't be blank")
        Double price;
        @NotBlank(message = "Image mustn't be blank")
        String image;
        @NotBlank(message = "Image mustn't be blank")
        Integer stock;
        @NotBlank(message = "Image mustn't be blank")
        Integer categoryId;


        public Product toDomain() {
            return Product.builder()
                    .id(id)
                    .code(UUID.randomUUID().toString())
                    .name(name)
                    .description(description)
                    .price(price)
                    .image(image)
                    .stock(stock)
                    .categoryId(categoryId)
                    .build();
        }
    }
}
