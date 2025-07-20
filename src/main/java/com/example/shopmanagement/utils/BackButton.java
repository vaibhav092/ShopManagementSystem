package com.example.shopmanagement.utils;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class BackButton {

    private static Stage primaryStage;
    private static Scene dashboardScene;

    // Call this once during app start (e.g., from Main)
    public static void init(Stage stage, Scene dashboard) {
        primaryStage = stage;
        dashboardScene = dashboard;
    }

    // Use this in any view
    public static HBox getBackButton() {
        Button backBtn = new Button("🔙 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #1f41e5; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> primaryStage.setScene(dashboardScene));

        HBox box = new HBox(backBtn);
        box.setAlignment(Pos.TOP_LEFT);
        box.setSpacing(10);
        box.setStyle("-fx-padding: 10;");
        return box;
    }
}
