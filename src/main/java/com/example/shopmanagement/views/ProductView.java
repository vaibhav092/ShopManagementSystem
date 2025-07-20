package com.example.shopmanagement.views;

import com.example.shopmanagement.models.Product;
import com.example.shopmanagement.services.ProductService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ProductView {

    private static ObservableList<Product> productData;

    public static void show(Stage stage) {
        Label title = new Label("➕ Add New Product");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField priceField = new TextField();
        priceField.setPromptText("Product Price");

        Label message = new Label();
        Button saveButton = new Button("Save Product");

        // Table to show all products
        TableView<Product> productTable = new TableView<>();
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.getColumns().addAll(idCol, nameCol, priceCol);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setPrefHeight(200);

        // Load data
        refreshProductTable(productTable);

        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();

            if (name.isEmpty() || priceText.isEmpty()) {
                message.setText("❌ Name or price is empty!");
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                boolean success = ProductService.addProduct(name, price);
                if (success) {
                    message.setText("✅ Product added!");
                    nameField.clear();
                    priceField.clear();
                    refreshProductTable(productTable); // Refresh table after add
                } else {
                    message.setText("❌ Failed to add product.");
                }
            } catch (NumberFormatException ex) {
                message.setText("❌ Invalid price!");
            }
        });

        VBox layout = new VBox(10,
                BackButton.getBackButton(),
                title,
                nameField,
                priceField,
                saveButton,
                message,
                new Label("📋 All Products:"),
                productTable
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 500, 600);
        scene.getStylesheets().add(ProductView.class.getResource("/style.css").toExternalForm());
        stage.setScene(scene);
    }

    private static void refreshProductTable(TableView<Product> table) {
        List<Product> products = ProductService.fetchAll();
        productData = FXCollections.observableArrayList(products);
        table.setItems(productData);
    }

}
