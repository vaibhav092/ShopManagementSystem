package com.example.shopmanagement;

import com.example.shopmanagement.utils.BackButton;
import com.example.shopmanagement.views.DashboardView;
import com.example.shopmanagement.views.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.shopmanagement.utils.DBConnection;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        System.out.println("⏳ Checking database connection...");
        DBConnection.getConnection(); // just for logging

        // Initialize back button with dummy scene for now
        BackButton.init(primaryStage, new Scene(new VBox()));

        // Start from Login screen
        LoginView.show(primaryStage);

        primaryStage.setTitle("Shop Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
