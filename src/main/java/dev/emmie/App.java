package dev.emmie;

import javafx.scene.shape.Rectangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    double offsetX;
    double offsetY;

    @Override
    public void start(Stage stage) {
        // create root and scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 600);

        // create questmap pane
        Pane questMap = new Pane();

        // create quest rectangle
        Rectangle testRect = new Rectangle(0, 0, 30, 30);
        testRect.setFill(Color.TAN);
        testRect.setStroke(Color.BLACK);

        // handlers for dragging
        testRect.setOnMousePressed(event -> {
            offsetX = event.getSceneX() - testRect.getLayoutX();
            offsetY = event.getSceneY() - testRect.getLayoutY();
        });

        testRect.setOnMouseDragged(event -> {
            testRect.setLayoutX(event.getSceneX() - offsetX);
            testRect.setLayoutY(event.getSceneY() - offsetY);
        });

        // add test rectangle to questmap
        questMap.getChildren().add(testRect);

        // put questmap in root pane
        root.setCenter(questMap);

        stage.setTitle("IRL Quest Book");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
