package dev.emmie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // create root and scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 600);

        // create questmap pane
        Pane questMap = new Pane();

        // add test rectangle to questmap
        Quest quest = new Quest("test", "test", false);
        QuestView questView = new QuestView(quest);
        questMap.getChildren().add(questView);

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
