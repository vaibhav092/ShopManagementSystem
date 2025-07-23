package com.example.shopmanagement.utils;

import com.example.shopmanagement.views.DashboardView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.util.Objects;

public class BackButton {

    private static Stage primaryStage;

    // Just save the stage; no need to store a static Scene
// Correct version now only accepts Stage
    public static void init(Stage stage) {
        primaryStage = stage;
    }


    // Use this in any view
    public static HBox getBackButton() {
        Button backBtn = new Button("🔙 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #1f41e5; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 8px;");

        backBtn.setOnAction(e -> {
            Scene scene = new Scene(DashboardView.getView(primaryStage), 700, 700);
            scene.getStylesheets().add(Objects.requireNonNull(DashboardView.class.getResource("/style.css")).toExternalForm());
            primaryStage.setScene(scene);
        });

        HBox box = new HBox(backBtn);
        box.setAlignment(Pos.TOP_LEFT);
        box.setSpacing(10);
        box.setStyle("-fx-padding: 10;");
        return box;
    }
}
