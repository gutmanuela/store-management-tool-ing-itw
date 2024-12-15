package com.ing.store_management.model.dto;

import com.ing.store_management.model.entity.ChangeType;

import java.sql.Timestamp;

public class StockManagementDto {
    private int changeQuantity;
    private String reason;
    private Timestamp date;
    private String productCode;
    private ChangeType changeType;

    public StockManagementDto(Timestamp date, String reason, int changeQuantity, String productCode, ChangeType changeType) {
        this.changeQuantity = changeQuantity;
        this.reason = reason;
        this.date = date;
        this.productCode = productCode;
        this.changeType = changeType;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public int getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(int changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
