package com.example.shopmanagement.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class DashboardView {

    // Used when directly showing from Main or after login
    public static void show(Stage stage) {
        Scene scene = new Scene(getView(stage), 700, 700); // Changed from 500, 450
        scene.getStylesheets().add(Objects.requireNonNull(DashboardView.class.getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
    }

    // Reusable component view (for BackButton etc.)
    public static Parent getView(Stage stage) {
        Label title = new Label("📊 Shop Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Button registerCustomerBtn = new Button("➕ Register Customer");
        Button addProductBtn = new Button("📦 Add Product");
        Button newOrderBtn = new Button("🛒 New Order");
        Button viewOrdersBtn = new Button("📋 View Orders");
        Button billingBtn = new Button("💳 Billing");
        Button reportsBtn = new Button("📈 Reports");

        // ✅ Navigation actions
        registerCustomerBtn.setOnAction(e -> CustomerRegistrationView.show(stage));
        addProductBtn.setOnAction(e -> ProductView.show(stage));
        newOrderBtn.setOnAction(e -> OrderEntryView.show(stage));
        viewOrdersBtn.setOnAction(e -> OrderView.show(stage));
        billingBtn.setOnAction(e->BillingView.show(stage));
        reportsBtn.setOnAction(e -> ReportsView.show(stage));

        VBox layout = new VBox(15,
                title,
                registerCustomerBtn,
                addProductBtn,
                newOrderBtn,
                viewOrdersBtn,
                billingBtn,
                reportsBtn
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMinWidth(700);  // Changed from 800
        layout.setMinHeight(700); // Changed from 800

        return layout;
    }
}
