module com.example.ryannelsonproject4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ryannelsonproject4 to javafx.fxml;
    exports com.example.ryannelsonproject4;
}