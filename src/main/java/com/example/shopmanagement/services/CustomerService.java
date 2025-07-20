package com.example.shopmanagement.services;
import com.example.shopmanagement.utils.DBConnection;

import com.example.shopmanagement.models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public static void saveCustomer(String name, String phone, String address) {
        String sql = "INSERT INTO customers (name, contact, address) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Failed to save customer: " + e.getMessage());
        }
    }


    public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading customers: " + e.getMessage());
        }

        return list;
    }

    public static void deleteCustomerById(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to delete customer: " + e.getMessage());
        }
    }

}
