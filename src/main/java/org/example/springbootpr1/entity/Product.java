package org.example.springbootpr1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @NotNull
    private long id;
    private String name;
    private double price;
    private String description;
    @OneToOne
    private ProductCategory category;
}
