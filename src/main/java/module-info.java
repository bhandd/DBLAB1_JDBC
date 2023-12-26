module dblab1.dblab1_jdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens dblab1.dblab1_jdbc to javafx.fxml;
    exports dblab1.dblab1_jdbc;
}