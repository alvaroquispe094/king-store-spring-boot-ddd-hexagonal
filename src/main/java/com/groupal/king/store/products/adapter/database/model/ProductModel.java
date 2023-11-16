package com.groupal.king.store.products.adapter.database.model;

import com.groupal.king.store.products.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductModel {

    @Id
    @Column(name = "id")
    private Long id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double price;
    @Column
    private String image;
    @Column
    private Integer stock;
    @Column
    private Integer categoryId;

    public static ProductModel fromDomain(Product product){
        return ProductModel.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .categoryId(product.getCategoryId())
                .build();
    }

    public Product toDomain(){
        return Product.builder()
                .id(id)
                .code(code)
                .name(name)
                .description(description)
                .price(price)
                .image(image)
                .stock(stock)
                .categoryId(categoryId)
                .build();
    }
}
