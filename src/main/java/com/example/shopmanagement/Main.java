package com.example.shopmanagement;

import com.example.shopmanagement.views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import com.example.shopmanagement.utils.DBConnection;


public class Main extends Application {
    @Override

    public void start(Stage primaryStage) {
        System.out.println("⏳ Checking database connection...");
        DBConnection.getConnection(); // just for logging

        primaryStage.setTitle("Shop Management System");
        LoginView.show(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); // This will call start()
    }
}
