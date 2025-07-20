package com.example.shopmanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/shop_db";
    private static final String USER = "root";
    private static final String PASSWORD = "vaibhav092";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to database.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("❌ Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }
}
