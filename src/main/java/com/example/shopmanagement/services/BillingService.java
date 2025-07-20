package com.example.shopmanagement.services;

import com.example.shopmanagement.models.BillingItem;
import com.example.shopmanagement.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillingService {

    public static List<BillingItem> getBillDetails(int orderId) {
        List<BillingItem> items = new ArrayList<>();

        String query = """
            SELECT o.id AS order_id, c.name AS customer_name, o.order_date, o.status,
                   p.name AS product_name, oi.quantity, oi.price
            FROM orders o
            JOIN customers c ON o.customer_id = c.id
            JOIN order_items oi ON o.id = oi.order_id
            JOIN products p ON oi.product_id = p.id
            WHERE o.id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BillingItem item = new BillingItem(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("order_date"),
                        rs.getString("status"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                items.add(item);
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to fetch billing details: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }
}
