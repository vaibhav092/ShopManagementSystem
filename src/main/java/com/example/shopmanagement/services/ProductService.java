package com.example.shopmanagement.services;

import com.example.shopmanagement.models.Product;
import com.example.shopmanagement.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public static List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),// ✅ make sure this is correct column name
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch products: " + e.getMessage());
        }

        return list;
    }

    public static int getProductIdByName(String name) {
        String sql = "SELECT id FROM products WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch product ID: " + e.getMessage());
        }

        return -1;
    }
}
