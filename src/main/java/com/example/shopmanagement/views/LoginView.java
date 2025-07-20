package com.example.shopmanagement.views;

import com.example.shopmanagement.utils.BackButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.shopmanagement.services.AuthService;

import java.util.Objects;

public class LoginView {

    public static void show(Stage stage) {
        Label title = new Label("🔐 Shop Management Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean success = AuthService.authenticate(username, password);
            if (success) {
                messageLabel.setText("✅ Login successful!");
                BackButton.init(stage);
                DashboardView.show(stage);
            } else {
                messageLabel.setText("❌ Invalid username or password");
            }
        });

        VBox layout = new VBox(10, title, usernameField, passwordField, loginButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setMinWidth(300);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(Objects.requireNonNull(LoginView.class.getResource("/style.css")).toExternalForm());

        stage.setScene(scene);
    }
}
