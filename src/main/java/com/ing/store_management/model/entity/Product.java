package com.ing.store_management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotNull
    private int stockQuantity;
    @NotBlank
    private String code;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<StockManagement> stockManagements;
}
