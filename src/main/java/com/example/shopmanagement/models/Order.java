package com.example.shopmanagement.models;

public class Order {
    private int id;
    private String customerName;
    private String orderDate;  // Changed from 'date' to be consistent with SQL
    private String status;
    private double totalPrice;
    private String productSummary;

    // Updated constructor to include all fields
    public Order(int id, String customerName, String orderDate, double totalPrice) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getProductSummary() {
        return productSummary;
    }

    public void setProductSummary(String productSummary) {
        this.productSummary = productSummary;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}