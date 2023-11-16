package com.groupal.king.store.products.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Product {
    Long id;
    String code;
    String name;
    String description;
    Double price;
    String image;
    Integer stock;
    Integer categoryId;
}
