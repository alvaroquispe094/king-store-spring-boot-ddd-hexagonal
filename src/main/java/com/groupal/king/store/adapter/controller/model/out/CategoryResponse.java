package com.groupal.king.store.adapter.controller.model.out;


import com.groupal.king.store.domain.Category;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class CategoryResponse {
    Long id;
    String name;
    Boolean active;

    public static CategoryResponse fromDomain(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .active(category.getActive())
                .build();
    }

    public static List<CategoryResponse> listFromDomain(List<Category> categories){
        return categories.stream()
                        .map(CategoryResponse::fromDomain)
                        .collect(Collectors.toList());
    }

}
