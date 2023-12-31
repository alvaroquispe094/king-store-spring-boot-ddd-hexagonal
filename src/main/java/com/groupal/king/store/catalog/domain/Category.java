package com.groupal.king.store.catalog.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Category {
    Long id;
    String code;
    String name;
    Boolean active;
}
