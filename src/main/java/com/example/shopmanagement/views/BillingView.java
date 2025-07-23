package com.example.shopmanagement.views;

import com.example.shopmanagement.models.BillingItem;
import com.example.shopmanagement.services.BillingService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

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

        // Add Print Button
        Button printButton = new Button("🖨️ Print Bill");
        printButton.setStyle("-fx-font-size: 14px;");

        // Footer for invoice
        Label footer = new Label("Thank you for shopping with us! 🛍️");
        footer.setStyle("-fx-font-size: 12px; -fx-padding: 20 0 10 0;");
        footer.setAlignment(Pos.CENTER);

        // Separate content for printing (without back button)
        VBox printableContent = new VBox(15);
        printableContent.setStyle("-fx-padding: 20; -fx-background-color: white;");
        printableContent.getChildren().addAll(
            heading,
            orderInfoBox,
            table,
            totalLabel,
            footer
        );
        printableContent.setMinWidth(650); // Good width for A4 paper

        // Main layout including back button and print button
        VBox layout = new VBox(15,
                BackButton.getBackButton(),
                heading,
                inputBox,
                orderInfoBox,
                table,
                totalLabel,
                printButton
        );
        layout.setStyle("-fx-padding: 20;");

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

        // Print Button action
        printButton.setOnAction(e -> {
            if (table.getItems() == null || table.getItems().isEmpty()) {
                showAlert("No Data", "Please load an order first before printing.");
                return;
            }
            printBill(stage, printableContent);
        });

        Scene scene = new Scene(layout, 700, 700); // Changed from 800, 750
        scene.getStylesheets().add(BillingView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }

    /**
     * Handles the printing of the bill
     */
    private static void printBill(Stage stage, Node contentToPrint) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            showAlert("Printer Error", "No printer found or printer unavailable.");
            return;
        }

        boolean proceed = job.showPrintDialog(stage);
        if (proceed) {
            // Set print job properties
            job.getJobSettings().setJobName("Invoice - Order");

            // Print the content
            boolean printed = job.printPage(contentToPrint);
            
            if (printed) {
                job.endJob();
                showAlert("Success", "Bill printed successfully! 🖨️");
            } else {
                job.cancelJob();
                showAlert("Error", "Printing failed. Please try again.");
            }
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
