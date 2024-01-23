package com.groupal.king.store.adapter.controller.model;

import com.groupal.king.store.domain.Product;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class ProductRest {
    Long id;
    String name;
    String description;
    Double price;
    String image;
    Integer stock;
    String category;
    Boolean active;

    public static ProductRest fromDomain(Product product){
        return ProductRest.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .category(product.getCategoryName())
                .active(product.getActive())
                .build();
    }

    public static List<ProductRest> listFromDomain(List<Product> products){
        return products.stream()
                        .map(ProductRest::fromDomain)
                        .collect(Collectors.toList());
    }

}
