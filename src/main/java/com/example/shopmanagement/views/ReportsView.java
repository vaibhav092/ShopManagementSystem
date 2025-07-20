package com.example.shopmanagement.views;

import com.example.shopmanagement.services.ReportService;
import com.example.shopmanagement.utils.BackButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class ReportsView {

    public static void show(Stage stage) {
        Label heading = new Label("📄 Business Report Summary");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // General Summary
        int totalOrders = ReportService.getTotalOrders();
        double totalRevenue = ReportService.getTotalRevenue();
        Map<String, Integer> statusCounts = ReportService.getStatusCounts();
        List<Map<String, Object>> productSummary = ReportService.getProductSalesSummary();

        VBox mainBox = new VBox(15);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(
                BackButton.getBackButton(),
                heading,
                new Label("📌 Total Orders: " + totalOrders),
                new Label("💰 Total Revenue: ₹" + String.format("%.2f", totalRevenue))
        );

        // Status Section
        VBox statusBox = new VBox(5);
        statusBox.getChildren().add(new Label("\n📦 Order Status Breakdown:"));
        for (String status : statusCounts.keySet()) {
            statusBox.getChildren().add(new Label("   • " + status + ": " + statusCounts.get(status)));
        }

        // Product Sales Table (Textual)
        VBox productBox = new VBox(5);
        productBox.getChildren().add(new Label("\n🧾 Product-wise Sales Summary:"));
        for (Map<String, Object> row : productSummary) {
            String product = (String) row.get("product");
            int qty = (int) row.get("quantity");
            double rev = (double) row.get("revenue");
            productBox.getChildren().add(new Label(
                    String.format("   • %-20s ➤ Qty: %-5d | Revenue: ₹%.2f", product, qty, rev)
            ));
        }

        mainBox.getChildren().addAll(statusBox, productBox);
        mainBox.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(mainBox, 700, 700);
        scene.getStylesheets().add(ReportsView.class.getResource("/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
