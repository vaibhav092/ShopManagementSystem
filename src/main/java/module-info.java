module com.example.shopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.shopmanagement to javafx.fxml;
    opens com.example.shopmanagement.models to javafx.base;

    exports com.example.shopmanagement;
    exports com.example.shopmanagement.models;
    exports com.example.shopmanagement.views;
    exports com.example.shopmanagement.services;
}
