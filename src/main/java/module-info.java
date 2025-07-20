module com.example.shopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.shopmanagement to javafx.fxml;
    exports com.example.shopmanagement;
}