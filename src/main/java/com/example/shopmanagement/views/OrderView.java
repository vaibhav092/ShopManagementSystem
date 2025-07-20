package com.example.shopmanagement.views;

import com.example.shopmanagement.models.Order;
import com.example.shopmanagement.services.OrderService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrderView {

    public static void show(Stage stage) {
        TableView<Order> table = new TableView<>();

        TableColumn<Order, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Order, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, String> productCol = new TableColumn<>("Products");
        productCol.setCellValueFactory(new PropertyValueFactory<>("productSummary"));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Pending", "Shipped", "Delivered", "Cancelled");
                comboBox.setOnAction(e -> {
                    Order order = getTableView().getItems().get(getIndex());
                    String newStatus = comboBox.getValue();
                    order.setStatus(newStatus);
                    OrderService.updateStatus(order.getId(), newStatus);
                });
            }

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(status);
                    setGraphic(comboBox);
                }
            }
        });
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, Double> totalCol = new TableColumn<>("Total Price");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Delete Button Column
        TableColumn<Order, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("❌");

            {
                deleteBtn.setOnAction(e -> {
                    Order order = getTableView().getItems().get(getIndex());
                    OrderService.deleteById(order.getId());
                    table.getItems().remove(order);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        TableColumn<Order, Void> viewCol = new TableColumn<>("Details");
        viewCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("👁");

            {
                viewBtn.setOnAction(e -> {
                    Order order = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Order Details");
                    alert.setHeaderText("Order #" + order.getId());

                    StringBuilder content = new StringBuilder();
                    content.append("👤 Customer: ").append(order.getCustomerName()).append("\n");
                    content.append("📅 Date: ").append(order.getOrderDate()).append("\n");
                    content.append("📦 Status: ").append(order.getStatus()).append("\n");
                    content.append("🛒 Products: ").append(order.getProductSummary()).append("\n");
                    content.append("💰 Total: ₹").append(order.getTotalPrice()).append("\n");

                    alert.setContentText(content.toString());
                    alert.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewBtn);
            }
        });


        table.getColumns().addAll(idCol, customerCol, dateCol, productCol, statusCol, totalCol, actionCol,viewCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Order> orders = OrderService.fetchAll();
        ObservableList<Order> data = FXCollections.observableArrayList(orders);
        table.setItems(data);

        VBox layout = new VBox(10, BackButton.getBackButton(), new Label("📦 All Orders:"), table);
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 800, 550);
        scene.getStylesheets().add(OrderEntryView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }
}
