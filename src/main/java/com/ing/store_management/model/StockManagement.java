package com.ing.store_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@Entity
public class StockManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String changeQuantity;
    @NotBlank
    private String reason;
    @NotNull
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
}
