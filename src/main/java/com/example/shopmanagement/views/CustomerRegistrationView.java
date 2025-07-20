package com.example.shopmanagement.views;

import com.example.shopmanagement.models.Customer;
import com.example.shopmanagement.services.CustomerService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomerRegistrationView {

    public static void show(Stage stage) {
        Label title = new Label("➕ Register Customer");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        Button saveButton = new Button("Save Customer");

        // Table
        TableView<Customer> table = new TableView<>();
        ObservableList<Customer> customerList = FXCollections.observableArrayList(CustomerService.getAllCustomers());

        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContact()));

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Customer, Void> deleteCol = new TableColumn<>("Action");

        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteBtn.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    if (customer != null) {
                        CustomerService.deleteCustomerById(customer.getId());
                        customerList.setAll(CustomerService.getAllCustomers()); // Refresh table
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });

        table.getColumns().addAll(idCol, nameCol, phoneCol, addressCol, deleteCol);
        table.setItems(customerList);
        table.setPrefHeight(200);

        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (!name.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
                CustomerService.saveCustomer(name, phone, address);
                customerList.setAll(CustomerService.getAllCustomers()); // refresh table

                nameField.clear();
                phoneField.clear();
                addressField.clear();
            }
        });

        VBox form = new VBox(10, BackButton.getBackButton(),title, nameField, phoneField, addressField, saveButton);
        VBox.setMargin(form, new Insets(10));
        VBox.setMargin(table, new Insets(10));

        VBox layout = new VBox(form, table);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout, 600, 500);
        scene.getStylesheets().add(CustomerRegistrationView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }
}
