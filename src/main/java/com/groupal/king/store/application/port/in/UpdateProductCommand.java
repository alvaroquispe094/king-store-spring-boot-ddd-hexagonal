package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

public interface UpdateProductCommand {

    Product execute(Command command, Long id);

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

        public Product toDomain(Long id) {
            return Product.builder()
                    .id(id)
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

    /*@Value
    @Builder
    class CategoryCommand {
        Long id;
        //@NotBlank(message = "Name mustn't be blank")
        String name;
    }*/
}
