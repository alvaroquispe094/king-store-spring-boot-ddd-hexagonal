package com.groupal.king.store.adapter.controller.model;


import com.groupal.king.store.domain.Category;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class CategoryRest {
    Long id;
    String name;
    Boolean active;

    public static CategoryRest fromDomain(Category category){
        return CategoryRest.builder()
                .id(category.getId())
                .name(category.getName())
                .active(category.getActive())
                .build();
    }

    public static List<CategoryRest> listFromDomain(List<Category> categories){
        return categories.stream()
                        .map(CategoryRest::fromDomain)
                        .collect(Collectors.toList());
    }

}
