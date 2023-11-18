package com.groupal.king.store.catalog.adapter.database.model;

import com.groupal.king.store.catalog.domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", schema = "catalog")
public class CategoryModel {

    @Id
    @Column(name = "id")
    private Long id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private Boolean active;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;


    public static CategoryModel fromDomain(Category category){
        return CategoryModel.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .active(category.getActive())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Category toDomain(){
        return Category.builder()
                .id(id)
                .code(code)
                .name(name)
                .active(active)
                .build();
    }
}
