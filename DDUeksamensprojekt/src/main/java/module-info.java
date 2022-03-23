module com.mycompany.ddueksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.ddueksamensprojekt to javafx.fxml;
    exports com.mycompany.ddueksamensprojekt;
}
