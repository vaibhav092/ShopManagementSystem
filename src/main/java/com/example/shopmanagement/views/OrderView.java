package com.example.shopmanagement.views;

import com.example.shopmanagement.models.Order;
import com.example.shopmanagement.services.OrderService;
import com.example.shopmanagement.utils.BackButton;
import javafx.collections.FXCollections;
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

        TableColumn<Order, String> nameCol = new TableColumn<>("Customer");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, String> productCol = new TableColumn<>("Products");
        productCol.setCellValueFactory(new PropertyValueFactory<>("productSummary"));



        TableColumn<Order, Double> totalCol = new TableColumn<>("Total Price");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<Order, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));


        table.getColumns().addAll(idCol, customerCol, dateCol, productCol,statusCol, totalCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Order> orders = OrderService.fetchAll();
        table.setItems(FXCollections.observableArrayList(orders));

        VBox layout = new VBox(10, BackButton.getBackButton(), new Label("📦 All Orders:"), table);
        layout.setStyle("-fx-padding: 20;");


        Scene scene = new Scene(layout, 600, 550);
        scene.getStylesheets().add(OrderEntryView.class.getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }
}
