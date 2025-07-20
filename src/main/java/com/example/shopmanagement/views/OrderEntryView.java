package com.example.shopmanagement.views;

import com.example.shopmanagement.models.Customer;
import com.example.shopmanagement.models.OrderItem;
import com.example.shopmanagement.models.Product;
import com.example.shopmanagement.services.CustomerService;
import com.example.shopmanagement.services.OrderService;
import com.example.shopmanagement.services.ProductService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class OrderEntryView {




    public static void show(Stage stage) {
        Label title = new Label("📝 New Order Entry");

        ComboBox<Customer> customerCombo = new ComboBox<>();
        customerCombo.setPromptText("Select Customer");
        customerCombo.setItems(FXCollections.observableArrayList(CustomerService.getAllCustomers()));

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Pending", "In Progress", "Delivered", "Cancelled");
        statusBox.setPromptText("Select Order Status");





        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        ComboBox<Product> productCombo = new ComboBox<>();
        productCombo.setPromptText("Select Product");
        productCombo.setItems(FXCollections.observableArrayList(ProductService.getAllProducts()));




        productCombo.setOnAction(e -> {
            Product selected = productCombo.getValue();
            if (selected != null) {
                priceField.setText(String.valueOf(selected.getPrice()));
            }
        });

        Button addItemButton = new Button("➕ Add Item");
        Button saveOrderButton = new Button("💾 Save Order");

        // Table to show order items
        TableView<OrderItem> orderTable = new TableView<>();
        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

        TableColumn<OrderItem, String> prodCol = new TableColumn<>("Product");
        prodCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProductName()));

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        orderTable.getColumns().addAll(prodCol, qtyCol, priceCol);
        orderTable.setItems(orderItems);
        orderTable.setPrefHeight(200);

        saveOrderButton.setOnAction(e -> {
            Customer selectedCustomer = customerCombo.getValue();
            String status = statusBox.getValue();

            if (selectedCustomer == null || orderItems.isEmpty() || status == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer, add items, and choose status.");
                alert.show();
                return;
            }

            int orderId = OrderService.saveOrder(
                    selectedCustomer.getId(),
                    status,
                    new ArrayList<>(orderItems)
            );

            if (orderId != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order saved with ID: " + orderId);
                alert.showAndWait();
                OrderEntryView.show(stage); // refresh
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save order.");
                alert.show();
            }
        });


        addItemButton.setOnAction(e -> {
            Product selectedProduct = productCombo.getValue();
            String quantityStr = quantityField.getText();

            if (selectedProduct == null || quantityStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select product and enter quantity.");
                alert.show();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = selectedProduct.getPrice();

                OrderItem item = new OrderItem(
                        selectedProduct.getId(),      // ✅ should be actual product ID from DB
                        selectedProduct.getName(),
                        quantity,
                        price
                );


                orderItems.add(item);

                // Reset fields
                productCombo.setValue(null);
                quantityField.clear();
                priceField.clear();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid quantity.");
                alert.show();
            }
        });


        VBox form = new VBox(10, BackButton.getBackButton(),title, customerCombo, statusBox,productCombo, quantityField, priceField, addItemButton, orderTable, saveOrderButton);
        form.setPadding(new Insets(20));

        Scene scene = new Scene(form, 600, 550);
        scene.getStylesheets().add(OrderEntryView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }
}
