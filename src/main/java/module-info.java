module com.example.coursefinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.coursefinder to javafx.fxml;
    exports com.example.coursefinder;
}