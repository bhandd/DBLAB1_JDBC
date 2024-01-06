module dblab1.dblab1_jdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports dblab1.dblab1_jdbc.model.Dialog;
    opens dblab1.dblab1_jdbc to javafx.fxml;
    exports dblab1.dblab1_jdbc;
    exports dblab1.dblab1_jdbc.model;
    exports dblab1.dblab1_jdbc.model.exceptions;
    exports dblab1.dblab1_jdbc.model.entityClasses;
}