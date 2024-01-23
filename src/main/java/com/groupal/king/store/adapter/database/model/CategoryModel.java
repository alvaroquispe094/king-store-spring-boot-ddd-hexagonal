package com.groupal.king.store.adapter.database.model;

import com.groupal.king.store.catalog.domain.Category;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
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
                .name(category.getName())
                .active(category.getActive())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Category toDomain(){
        return Category.builder()
                .id(id)
                .name(name)
                .active(active)
                .build();
    }
}
