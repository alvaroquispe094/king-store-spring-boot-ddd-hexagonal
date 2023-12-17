package com.groupal.king.store.catalog.domain;

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
    Category category;
    Boolean active;
}
