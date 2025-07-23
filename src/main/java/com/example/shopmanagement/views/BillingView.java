package com.example.shopmanagement.views;

import com.example.shopmanagement.models.BillingItem;
import com.example.shopmanagement.services.BillingService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class BillingView {

    public static void show(Stage stage) {
        Label heading = new Label("🧾 Order Invoice");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Input field to enter Order ID
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID");

        Button loadButton = new Button("Load Bill");

        HBox inputBox = new HBox(10, new Label("Order ID:"), orderIdField, loadButton);
        inputBox.setStyle("-fx-padding: 10;");

        // Order metadata labels
        Label orderIdLabel = new Label();
        Label customerLabel = new Label();
        Label dateLabel = new Label();
        Label statusLabel = new Label();

        VBox orderInfoBox = new VBox(5, orderIdLabel, customerLabel, dateLabel, statusLabel);
        orderInfoBox.setStyle("-fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-color: #ccc;");

        // Table setup
        TableView<BillingItem> table = new TableView<>();

        TableColumn<BillingItem, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<BillingItem, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<BillingItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<BillingItem, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        table.getColumns().addAll(nameCol, quantityCol, priceCol, subtotalCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label totalLabel = new Label("💰 Total Amount: ₹0.00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Load Bill button action
        loadButton.setOnAction(e -> {
            String input = orderIdField.getText();
            if (input.isEmpty() || !input.matches("\\d+")) {
                showAlert("Invalid ID", "Please enter a valid numeric Order ID.");
                return;
            }

            int orderId = Integer.parseInt(input);
            List<BillingItem> items = BillingService.getBillDetails(orderId);

            if (items.isEmpty()) {
                showAlert("Not Found", "No billing details found for Order ID: " + orderId);
                return;
            }

            table.setItems(FXCollections.observableArrayList(items));

            double total = items.stream().mapToDouble(BillingItem::getSubtotal).sum();
            totalLabel.setText("💰 Total Amount: ₹" + total);

            BillingItem sample = items.get(0);
            orderIdLabel.setText("🆔 Order ID: " + sample.getOrderId());
            customerLabel.setText("👤 Customer: " + sample.getCustomerName());
            dateLabel.setText("📅 Date: " + sample.getOrderDate());
            statusLabel.setText("📦 Status: " + sample.getStatus());
        });

        VBox layout = new VBox(15,
                BackButton.getBackButton(),
                heading,
                inputBox,
                orderInfoBox,
                table,
                totalLabel
        );
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 700, 700); // Changed from 800, 750
        scene.getStylesheets().add(BillingView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
