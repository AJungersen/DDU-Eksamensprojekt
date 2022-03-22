module com.mycompany.ddueksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.ddueksamensprojekt to javafx.fxml;
    exports com.mycompany.ddueksamensprojekt;
}
