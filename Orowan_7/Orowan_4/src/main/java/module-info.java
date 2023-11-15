module application.orowan_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens application.orowan_3 to javafx.fxml;
    exports application.orowan_3;
}