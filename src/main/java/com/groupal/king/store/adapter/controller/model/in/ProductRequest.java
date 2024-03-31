package com.groupal.king.store.adapter.controller.model.in;

import com.groupal.king.store.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductRequest {
    @NotBlank(message = "Name mustn't be blank")
    @Schema(description = "Nombre del producto", example = "iPhone 15 Pro Max")
    String name;

    @NotBlank(message = "Description mustn't be blank")
    @Schema(description = "Descripción del producto", example = "iPhone 15 Pro Max, 6.3 inch. Octa core 2.4ghz.")
    String description;

    @NotNull(message = "Price mustn't be null")
    @Min(0)
    @Schema(description = "Precio del producto", example = "1199.99")
    Double price;

    @Schema(description = "Url de la imagen", example = "https://www.google.com.ar/test.image.jpg")
    String image;

    @NotNull(message = "Image mustn't be null")
    @Min(1)
    @Schema(description = "Stock del producto. Mayor o igual a 1", example = "100")
    Integer stock;

    @NotNull(message = "Image mustn't be null")
    @Schema(description = "Id de la categoria del producto", example = "3")
    Long categoryId;

    @NotNull(message = "Active mustn't be null")
    @Schema(description = "Estado del producto", example = "true")
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
