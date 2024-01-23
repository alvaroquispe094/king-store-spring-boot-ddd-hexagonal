package com.groupal.king.store.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Product {
    Long id;
    String name;
    String description;
    Double price;
    String image;
    Integer stock;
    Long categoryId;
    String categoryName;
    Boolean active;
}
