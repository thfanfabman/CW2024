module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.entities to javafx.fxml;
    opens com.example.demo.entities.planes to javafx.fxml;
    opens com.example.demo.entities.projectiles to javafx.fxml;
    opens com.example.demo.UI to javafx.fxml;
}