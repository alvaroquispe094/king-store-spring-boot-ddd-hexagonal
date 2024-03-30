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
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
        @NotBlank(message = "Name mustn't be blank")
        String name;

        @NotBlank(message = "Description mustn't be blank")
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
        String description;

        @NotNull(message = "Price mustn't be null")
        @Min(0)
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
        Double price;

        String image;

        @NotNull(message = "Image mustn't be null")
        @Min(1)
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
        Integer stock;

        @NotNull(message = "Image mustn't be null")
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
        Long categoryId;

        @NotNull(message = "Active mustn't be null")
        //@Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
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
