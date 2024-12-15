package com.ing.store_management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@AllArgsConstructor
public class StockManagementDto {
    private Long id;
    private String changeQuantity;
    private String reason;
    private Timestamp date;
}
