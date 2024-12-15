package com.ing.store_management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private int stockQuantity;
    private String code;
}
