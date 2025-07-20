package com.example.shopmanagement.models;

public class BillingItem {
    private int orderId;
    private String customerName;
    private String orderDate;
    private String status;
    private String productName;
    private int quantity;
    private double price;
    private double subtotal;

    public BillingItem(int orderId, String customerName, String orderDate, String status,
                       String productName, int quantity, double price) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.status = status;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = quantity * price;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Optionally, add setters if you need to modify fields later
}
