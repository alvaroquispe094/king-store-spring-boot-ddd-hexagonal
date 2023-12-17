package com.groupal.king.store.catalog.adapter.database.model;

import com.groupal.king.store.catalog.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", schema = "catalog")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="category_id")
    private CategoryModel caterory;
    @Column
    private Boolean active;

    public static ProductModel fromDomain(Product product){
        return ProductModel.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .caterory(CategoryModel.fromDomain(product.getCategory()))
                .active(product.getActive())
                .build();
    }

    public Product toDomain(){
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .image(image)
                .stock(stock)
                .category(caterory.toDomain())
                .active(active)
                .build();
    }
}
