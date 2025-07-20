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

    public static void show(Stage stage) {
        Label title = new Label("📊 Shop Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Button registerCustomerBtn = new Button("➕ Register Customer");
        Button newOrderBtn = new Button("🛒 New Order");
        Button viewOrdersBtn = new Button("📋 View Orders");
        Button billingBtn = new Button("💳 Billing");
        Button deliveryBtn = new Button("🚚 Delivery Status");
        Button reportsBtn = new Button("📈 Reports");

        registerCustomerBtn.setOnAction(e -> CustomerRegistrationView.show(stage));
        newOrderBtn.setOnAction(e -> OrderEntryView.show(stage));

        VBox layout = new VBox(15,
                title,
                registerCustomerBtn,
                newOrderBtn,
                viewOrdersBtn,
                billingBtn,
                deliveryBtn,
                reportsBtn
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMinWidth(400);

        Scene scene = new Scene(layout, 500, 450);
        scene.getStylesheets().add(Objects.requireNonNull(DashboardView.class.getResource("/style.css")).toExternalForm());

        stage.setScene(scene);
    }
    public static Parent getView() {
        Label title = new Label("📊 Shop Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Button registerCustomerBtn = new Button("➕ Register Customer");
        Button newOrderBtn = new Button("🛒 New Order");
        Button viewOrdersBtn = new Button("📋 View Orders");
        Button billingBtn = new Button("💳 Billing");
        Button deliveryBtn = new Button("🚚 Delivery Status");
        Button reportsBtn = new Button("📈 Reports");

        registerCustomerBtn.setOnAction(e -> CustomerRegistrationView.show((Stage) title.getScene().getWindow()));
        newOrderBtn.setOnAction(e -> OrderEntryView.show((Stage) title.getScene().getWindow()));

        VBox layout = new VBox(15,
                title,
                registerCustomerBtn,
                newOrderBtn,
                viewOrdersBtn,
                billingBtn,
                deliveryBtn,
                reportsBtn
        );
        layout.setAlignment(Pos.CENTER);
        layout.setMinWidth(500);
        layout.setMinHeight(500);

        return layout;
    }

}
