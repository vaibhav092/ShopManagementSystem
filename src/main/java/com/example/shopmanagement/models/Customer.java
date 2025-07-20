package com.example.shopmanagement.models;

public class Customer {
    private int id;
    private String name;
    private String contact;
    private String address;

    public Customer(int id, String name, String contact, String address) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return name; // Or whatever field holds the customer's name
    }

}

