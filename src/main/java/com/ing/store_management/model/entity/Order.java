package com.ing.store_management.model.entity;

import java.sql.Timestamp;

public class Order {
    private int id;
    private String orderNo;
    private Timestamp orderDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String billingAddress;
    private String shippingAddress;
    private Double totalPrice;
    private Status status;

}
