package com.ing.store_management.model.dto;

public class ProductDto {
    private String name;
    private double price;
    private int stockQuantity;
    private String code;
    private String categoryName;

    public ProductDto() {
    }

    public ProductDto(String code, double price, String name, int stockQuantity, String categoryName) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.code = code;
        this.categoryName = categoryName;
    }

    public ProductDto(String code, String name,  double price,String categoryName) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
