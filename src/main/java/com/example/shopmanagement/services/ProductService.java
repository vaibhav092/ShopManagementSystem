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

    public static boolean addProduct(String name, double price) {
        String sql = "INSERT INTO products(name, price) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to add product: " + e.getMessage());
        }
        return false;
    }

    public static List<Product> fetchAll() {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                list.add(p);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Fetch Error");
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
