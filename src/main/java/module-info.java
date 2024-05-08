module GrafyJ {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports ster to javafx.graphics;
    exports gui to javafx.fxml, javafx.graphics;

    opens gui to javafx.fxml;
}