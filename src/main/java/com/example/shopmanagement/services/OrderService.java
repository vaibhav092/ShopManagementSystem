package com.example.shopmanagement.services;

import com.example.shopmanagement.utils.DBConnection;
import com.example.shopmanagement.views.OrderEntryView.OrderItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderService {

    public static int saveOrder(int customerId, String status, List<OrderItem> items) {
        int orderId = -1;

        String insertOrderSQL = "INSERT INTO orders (customer_id, order_date, status) VALUES (?, ?, ?)";
        String insertItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // 1. Insert order
            try (PreparedStatement ps = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, customerId);
                ps.setDate(2, Date.valueOf(LocalDate.now()));
                ps.setString(3, status);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            // 2. Insert items
            try (PreparedStatement ps = conn.prepareStatement(insertItemSQL)) {
                for (OrderItem item : items) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProductId()); // ✅ Correct usage inside loop
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getPrice());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit(); // End transaction

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }
}
