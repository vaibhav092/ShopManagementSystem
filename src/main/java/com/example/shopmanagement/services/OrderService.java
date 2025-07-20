package com.example.shopmanagement.services;

import com.example.shopmanagement.models.Order;
import com.example.shopmanagement.utils.DBConnection;
import com.example.shopmanagement.models.OrderItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public static List<Order> fetchAll() {
        List<Order> orders = new ArrayList<>();

        String query = """
        SELECT o.id, c.name AS customer_name, o.order_date, o.status,
               GROUP_CONCAT(CONCAT(p.name, ' x', oi.quantity) SEPARATOR ', ') AS product_summary,
               SUM(oi.price * oi.quantity) AS total_price
        FROM orders o
        JOIN customers c ON o.customer_id = c.id
        JOIN order_items oi ON o.id = oi.order_id
        JOIN products p ON oi.product_id = p.id
        GROUP BY o.id
        ORDER BY o.id DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("order_date"),
                        rs.getDouble("total_price")
                );
                order.setStatus(rs.getString("status"));
                order.setProductSummary(rs.getString("product_summary"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching orders: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }
}
