package com.groupal.king.store.application.port.in;

import com.groupal.king.store.catalog.domain.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

public interface ProductCommand {

    Product execute(Command command);

    @Value
    @Builder
    class Command {
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
        Long categoryId;
        @NotBlank(message = "Active mustn't be blank")
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
