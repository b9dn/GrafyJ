package ster;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import gui.*;
import init.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.3), CornerRadii.EMPTY, Insets.EMPTY);
        load.setBackground(new Background(backgroundFill));

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setTitle("GrafyJ");
        stage.show();

        Drawer.drawColorScale(load, 50, 950, 710, 740, 75);
    }
}
