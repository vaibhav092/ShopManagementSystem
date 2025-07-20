package com.example.shopmanagement.services;

import com.example.shopmanagement.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ReportService {

    public static int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getTotalRevenue() {
        String sql = "SELECT SUM(price * quantity) FROM order_items";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static Map<String, Integer> getStatusCounts() {
        Map<String, Integer> statusMap = new HashMap<>();
        String sql = "SELECT status, COUNT(*) FROM orders GROUP BY status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                statusMap.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusMap;
    }

    public static List<Map<String, Object>> getProductSalesSummary() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = """
            SELECT p.name, SUM(oi.quantity) AS total_qty, SUM(oi.quantity * oi.price) AS total_revenue
            FROM order_items oi
            JOIN products p ON oi.product_id = p.id
            GROUP BY p.name
            ORDER BY total_revenue DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("product", rs.getString("name"));
                row.put("quantity", rs.getInt("total_qty"));
                row.put("revenue", rs.getDouble("total_revenue"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
